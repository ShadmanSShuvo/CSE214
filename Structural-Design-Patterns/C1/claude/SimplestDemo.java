// =======================================================
// Adapter + Decorator Pattern (Academic Demo)
// Same scenario as the assignment, simplified for lab exam
// =======================================================

// ---------- Legacy Class (Adaptee) ----------
class LegacyInternalXMLService {

    public String getXML() {
        return "<user><name>Alice</name></user>";
    }
}

// ---------- Target Interface ----------
interface ApiResponse {
    String getBody();
}

// ---------- Adapter ----------
class XmlToJsonApiResponseAdapter implements ApiResponse {

    private LegacyInternalXMLService legacyService;

    public XmlToJsonApiResponseAdapter(LegacyInternalXMLService legacyService) {
        this.legacyService = legacyService;
    }

    @Override
    public String getBody() {
        String xml = legacyService.getXML();
        return convertXmlToJson(xml);
    }

    private String convertXmlToJson(String xml) {
        // XML -> JSON conversion
        return "{ \"name\" : \"Alice\" }";
    }
}

// ---------- Base Decorator ----------
abstract class ApiResponseDecorator implements ApiResponse {

    protected ApiResponse response;

    public ApiResponseDecorator(ApiResponse response) {
        this.response = response;
    }

    @Override
    public String getBody() {
        return transform(response.getBody());
    }

    protected abstract String transform(String body);
}

// ---------- Compression Decorator ----------
class CompressionDecorator extends ApiResponseDecorator {

    public CompressionDecorator(ApiResponse response) {
        super(response);
    }

    @Override
    protected String transform(String body) {
        // Compression logic
        return "[Compressed] " + body;
    }
}

// ---------- Encryption Decorator ----------
class EncryptionDecorator extends ApiResponseDecorator {

    public EncryptionDecorator(ApiResponse response) {
        super(response);
    }

    @Override
    protected String transform(String body) {
        // Encryption logic
        return "[Encrypted] " + body;
    }
}

// ---------- Signature Decorator ----------
class SignatureDecorator extends ApiResponseDecorator {

    public SignatureDecorator(ApiResponse response) {
        super(response);
    }

    @Override
    protected String transform(String body) {
        // Digital signature logic
        return body + " [Signed]";
    }
}

// ---------- Client ----------
public class SimplestDemo {

    public static void main(String[] args) {

        LegacyInternalXMLService legacy = new LegacyInternalXMLService();

        // Adapter only
        ApiResponse response1 = new XmlToJsonApiResponseAdapter(legacy);

        System.out.println("===== Adapter Only =====");
        System.out.println(response1.getBody());

        // Adapter + Compression
        ApiResponse response2 = new CompressionDecorator(
                new XmlToJsonApiResponseAdapter(legacy));

        System.out.println("\n===== Compression =====");
        System.out.println(response2.getBody());

        // Adapter + Compression + Encryption
        ApiResponse response3 = new EncryptionDecorator(
                new CompressionDecorator(
                        new XmlToJsonApiResponseAdapter(legacy)));

        System.out.println("\n===== Compression + Encryption =====");
        System.out.println(response3.getBody());

        // Adapter + Compression + Encryption + Signature
        ApiResponse response4 = new SignatureDecorator(
                new EncryptionDecorator(
                        new CompressionDecorator(
                                new XmlToJsonApiResponseAdapter(legacy))));

        System.out.println("\n===== Compression + Encryption + Signature =====");
        System.out.println(response4.getBody());
    }
}
