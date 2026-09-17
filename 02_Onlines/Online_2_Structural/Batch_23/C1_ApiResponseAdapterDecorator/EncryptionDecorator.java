import java.util.Base64;

/**
 * Concrete Decorator: Encrypts the response body before sending.
 */
public class EncryptionDecorator extends ApiResponseDecorator {

    public EncryptionDecorator(ApiResponse wrappee) {
        super(wrappee);
    }

    @Override
    protected String transform(String body) {
        // Safe reversible Base64 encoding representing encrypted payload
        String encrypted = Base64.getEncoder().encodeToString(body.getBytes());
        return "[ENCRYPTED: " + encrypted + "]";
    }
}
