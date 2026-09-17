import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Concrete Decorator: Adds a digital signature / checksum to the response body.
 * Demonstrates Open/Closed principle: added without modifying any existing classes.
 */
public class SignatureDecorator extends ApiResponseDecorator {

    public SignatureDecorator(ApiResponse wrappee) {
        super(wrappee);
    }

    @Override
    protected String transform(String body) {
        String hash = computeSha256(body);
        return body + " [SIGNATURE_SHA256: " + hash.substring(0, 16) + "...]";
    }

    private String computeSha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return Integer.toHexString(input.hashCode());
        }
    }
}
