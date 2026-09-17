import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

/**
 * Concrete Decorator: Compresses the response body before sending.
 */
public class CompressionDecorator extends ApiResponseDecorator {

    public CompressionDecorator(ApiResponse wrappee) {
        super(wrappee);
    }

    @Override
    protected String transform(String body) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
                gzip.write(body.getBytes(StandardCharsets.UTF_8));
            }
            String compressedBase64 = Base64.getEncoder().encodeToString(out.toByteArray());
            return "[GZIP_COMPRESSED: " + compressedBase64 + "]";
        } catch (IOException e) {
            return "[COMPRESSED: " + body + "]";
        }
    }
}
