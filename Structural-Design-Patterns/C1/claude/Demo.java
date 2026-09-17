import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.*;
import org.w3c.dom.*;


// ================= Internal service (DO NOT TOUCH) =================
class LegacyInternalXMLService {
    String getDataAsXML() {
        return "<user><id>101</id><name>Ishrat</name></user>";
    }
}

// ========= Target interface the client codes against (DO NOT TOUCH) =========
interface ApiResponse {
    String getBody();
}


/**
 * ADAPTER: adapts LegacyInternalXMLService (XML, wrong interface)
 * to ApiResponse (JSON, target interface).
 * This class is the single point of XML->JSON knowledge.
 * Nothing else in the system ever sees XML.
 */
class XmlToJsonApiResponseAdapter implements ApiResponse {

    private final LegacyInternalXMLService legacyService;

    XmlToJsonApiResponseAdapter(LegacyInternalXMLService legacyService) {
        this.legacyService = legacyService;
    }

    @Override
    public String getBody() {
        String xml = legacyService.getDataAsXML();
        return convertXmlToJson(xml);
    }

    // Minimal, dependency-free XML->JSON conversion for flat/simple structures.
    private String convertXmlToJson(String xml) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            Document doc = dbf.newDocumentBuilder()
                    .parse(new org.xml.sax.InputSource(new java.io.StringReader(xml)));
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();

            StringBuilder json = new StringBuilder();
            json.append("{\"").append(root.getTagName()).append("\":{");
            NodeList children = root.getChildNodes();
            boolean first = true;
            for (int i = 0; i < children.getLength(); i++) {
                Node n = children.item(i);
                if (n.getNodeType() != Node.ELEMENT_NODE) continue;
                if (!first) json.append(",");
                first = false;
                json.append("\"").append(n.getNodeName()).append("\":\"")
                    .append(n.getTextContent()).append("\"");
            }
            json.append("}}");
            return json.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert legacy XML to JSON", e);
        }
    }
}


/**
 * DECORATOR base. Every transform (compression, encryption, signature, ...)
 * extends this. It just delegates to the wrapped ApiResponse and lets
 * subclasses post-process the body.
 */
abstract class ApiResponseDecorator implements ApiResponse {
    protected final ApiResponse wrapped;

    protected ApiResponseDecorator(ApiResponse wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String getBody() {
        return transform(wrapped.getBody());
    }

    /** Subclasses implement only their own transform. */
    protected abstract String transform(String input);
}



/*Concrete decorator implementations */


/**
 * COMPRESSION decorator — Deflate, then Base64 so it's still safely a String.
 */
class CompressionDecorator extends ApiResponseDecorator {
    CompressionDecorator(ApiResponse wrapped) {
        super(wrapped);
    }

    @Override
    protected String transform(String input) {
        try {
            byte[] data = input.getBytes("UTF-8");
            Deflater deflater = new Deflater();
            deflater.setInput(data);
            deflater.finish();
            byte[] buffer = new byte[Math.max(64, data.length * 2)];
            int len = deflater.deflate(buffer);
            byte[] compressed = new byte[len];
            System.arraycopy(buffer, 0, compressed, 0, len);
            return Base64.getEncoder().encodeToString(compressed);
        } catch (Exception e) {
            throw new RuntimeException("Compression failed", e);
        }
    }
}

/** ENCRYPTION decorator — AES, then Base64 so it's still safely a String. */
class EncryptionDecorator extends ApiResponseDecorator {

    // Demo-only fixed key. In production: per-tenant / KMS-managed keys.
    private static final byte[] KEY = "0123456789abcdef".getBytes(); // 16 bytes = AES-128

    EncryptionDecorator(ApiResponse wrapped) {
        super(wrapped);
    }

    @Override
    protected String transform(String input) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // demo simplicity
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY, "AES"));
            byte[] encrypted = cipher.doFinal(input.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }
}

/** Future extension: SIGNATURE decorator. No other class touched. */
class SignatureDecorator extends ApiResponseDecorator {
    SignatureDecorator(ApiResponse wrapped) {
        super(wrapped);
    }

    @Override
    protected String transform(String input) {
        String signature = "sig(" + input.hashCode() + ")"; // placeholder
        return input + "::" + signature;
    }
}




/**
 * Builds the final ApiResponse by layering decorators in exactly the
 * order specified. This is what reads ?encrypt=true&compress=true (and
 * their relative order) and assembles the chain — no new class needed
 * per combination.
 */
class ApiResponseFactory {

    enum Transform {
        COMPRESS, ENCRYPT, SIGN
    }

    static ApiResponse build(LegacyInternalXMLService legacyService, List<Transform> orderedTransforms) {
        ApiResponse response = new XmlToJsonApiResponseAdapter(legacyService);

        for (Transform t : orderedTransforms) {
            switch (t) {
                case COMPRESS -> response = new CompressionDecorator(response);
                case ENCRYPT -> response = new EncryptionDecorator(response);
                case SIGN -> response = new SignatureDecorator(response);
                // Adding a new enum value + one case line here is the ONLY
                // change needed to support a new transform end-to-end.
            }
        }
        return response;
    }
}




class RequestHandler {

    /**
     * Example: parses something like
     * "?compress=true&encrypt=true&order=compress,encrypt"
     * In practice the "order" param (or an ordered list of query keys) tells you
     * the sequence the client wants applied.
     */
    static ApiResponse handleRequest(LegacyInternalXMLService legacyService, Map<String, String> params) {
        List<ApiResponseFactory.Transform> order = new ArrayList<>();

        String orderParam = params.getOrDefault("order", ""); // e.g. "compress,encrypt"
        for (String step : orderParam.split(",")) {
            switch (step.trim().toLowerCase()) {
                case "compress" -> {
                    if ("true".equals(params.get("compress")))
                        order.add(ApiResponseFactory.Transform.COMPRESS);
                }
                case "encrypt" -> {
                    if ("true".equals(params.get("encrypt")))
                        order.add(ApiResponseFactory.Transform.ENCRYPT);
                }
                case "sign" -> {
                    if ("true".equals(params.get("sign")))
                        order.add(ApiResponseFactory.Transform.SIGN);
                }
                default -> {
                    /* ignore unknown */ }
            }
        }

        return ApiResponseFactory.build(legacyService, order);
    }
}


public class Demo {
    public static void main(String[] args) {
        LegacyInternalXMLService legacy = new LegacyInternalXMLService();

        System.out.println("-- JSON only --");
        System.out.println(new XmlToJsonApiResponseAdapter(legacy).getBody());

        System.out.println("-- compress then encrypt --");
        Map<String, String> p1 = Map.of("compress", "true", "encrypt", "true", "order", "compress,encrypt");
        System.out.println(RequestHandler.handleRequest(legacy, p1).getBody());

        System.out.println("-- encrypt then compress (different result!) --");
        Map<String, String> p2 = Map.of("compress", "true", "encrypt", "true", "order", "encrypt,compress");
        System.out.println(RequestHandler.handleRequest(legacy, p2).getBody());
    }
}
