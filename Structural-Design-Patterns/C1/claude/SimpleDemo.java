// Academic demo of Adapter + Decorator Pattern
// Simplified for classroom use (implementation details omitted)

import java.util.*;

class LegacyInternalXMLService {
    public String getDataAsXML() {
        return "<user>...</user>";
    }
}

interface ApiResponse {
    String getBody();
}

class XmlToJsonApiResponseAdapter implements ApiResponse {
    private LegacyInternalXMLService legacyService;

    public XmlToJsonApiResponseAdapter(LegacyInternalXMLService legacyService) {
        this.legacyService = legacyService;
    }

    @Override
    public String getBody() {
        String xml = legacyService.getDataAsXML();
        return convertXmlToJson(xml);
    }

    private String convertXmlToJson(String xml) {
        // XML -> JSON conversion logic
        return "{JSON}";
    }
}

abstract class ApiResponseDecorator implements ApiResponse {
    protected ApiResponse wrapped;

    public ApiResponseDecorator(ApiResponse wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String getBody() {
        return transform(wrapped.getBody());
    }

    protected abstract String transform(String input);
}

class CompressionDecorator extends ApiResponseDecorator {
    public CompressionDecorator(ApiResponse wrapped) {
        super(wrapped);
    }

    @Override
    protected String transform(String input) {
        return compress(input);
    }

    private String compress(String input) {
        // Compression algorithm
        return "[Compressed] " + input;
    }
}

class EncryptionDecorator extends ApiResponseDecorator {
    public EncryptionDecorator(ApiResponse wrapped) {
        super(wrapped);
    }

    @Override
    protected String transform(String input) {
        return encrypt(input);
    }

    private String encrypt(String input) {
        // AES encryption
        return "[Encrypted] " + input;
    }
}

class SignatureDecorator extends ApiResponseDecorator {
    public SignatureDecorator(ApiResponse wrapped) {
        super(wrapped);
    }

    @Override
    protected String transform(String input) {
        return sign(input);
    }

    private String sign(String input) {
        // Digital signature generation
        return input + " [Signed]";
    }
}

class ApiResponseFactory {

    enum Transform {
        COMPRESS, ENCRYPT, SIGN
    }

    public static ApiResponse build(
            LegacyInternalXMLService service,
            List<Transform> transforms) {

        ApiResponse response = new XmlToJsonApiResponseAdapter(service);

        for (Transform t : transforms) {
            switch (t) {
                case COMPRESS:
                    response = new CompressionDecorator(response);
                    break;
                case ENCRYPT:
                    response = new EncryptionDecorator(response);
                    break;
                case SIGN:
                    response = new SignatureDecorator(response);
                    break;
            }
        }

        return response;
    }
}

class RequestHandler {

    public static ApiResponse handleRequest(
            LegacyInternalXMLService service,
            Map<String, String> params) {

        List<ApiResponseFactory.Transform> order = new ArrayList<>();

        String orderParam = params.getOrDefault("order", "");

        for (String step : orderParam.split(",")) {
            switch (step.trim().toLowerCase()) {
                case "compress":
                    if ("true".equals(params.get("compress")))
                        order.add(ApiResponseFactory.Transform.COMPRESS);
                    break;

                case "encrypt":
                    if ("true".equals(params.get("encrypt")))
                        order.add(ApiResponseFactory.Transform.ENCRYPT);
                    break;

                case "sign":
                    if ("true".equals(params.get("sign")))
                        order.add(ApiResponseFactory.Transform.SIGN);
                    break;
            }
        }

        return ApiResponseFactory.build(service, order);
    }
}

public class SimpleDemo {

    public static void main(String[] args) {

        LegacyInternalXMLService legacy = new LegacyInternalXMLService();

        System.out.println("=== JSON Only ===");
        ApiResponse response1 =
                new XmlToJsonApiResponseAdapter(legacy);
        System.out.println(response1.getBody());

        System.out.println("\n=== Compress -> Encrypt ===");
        Map<String, String> req1 = Map.of(
                "compress", "true",
                "encrypt", "true",
                "order", "compress,encrypt"
        );
        System.out.println(
                RequestHandler.handleRequest(legacy, req1).getBody()
        );

        System.out.println("\n=== Encrypt -> Compress ===");
        Map<String, String> req2 = Map.of(
                "compress", "true",
                "encrypt", "true",
                "order", "encrypt,compress"
        );
        System.out.println(
                RequestHandler.handleRequest(legacy, req2).getBody()
        );
    }
}
