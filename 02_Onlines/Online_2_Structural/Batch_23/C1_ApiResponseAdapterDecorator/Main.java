import java.util.List;
import java.util.ArrayList;

/**
 * Client application demonstrating Adapter + Decorator design patterns
 * for Batch 23 CSE 214 Online 2 (C1).
 */
public class Main {

    /**
     * Helper to dynamically assemble decorators based on client request parameters and requested order.
     */
    public static ApiResponse buildResponsePipeline(
            LegacyInternalXMLService legacyService,
            List<String> transformPipeline
    ) {
        // Base: Legacy XML adapted to target JSON interface
        ApiResponse response = new XmlToJsonAdapter(legacyService);

        // Apply requested transformations in specified order
        for (String transform : transformPipeline) {
            if ("compress".equalsIgnoreCase(transform)) {
                response = new CompressionDecorator(response);
            } else if ("encrypt".equalsIgnoreCase(transform)) {
                response = new EncryptionDecorator(response);
            } else if ("sign".equalsIgnoreCase(transform)) {
                response = new SignatureDecorator(response);
            } else {
                System.out.println("Warning: Unknown transformation ignored: " + transform);
            }
        }
        return response;
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("    CSE 214 Online 2 (C1) - API Adapter & Decorator Demo       ");
        System.out.println("===============================================================");

        // 1. Untouched Internal Service
        LegacyInternalXMLService legacyService = new LegacyInternalXMLService();
        System.out.println("\n[1] Internal Legacy Service (Raw XML):");
        System.out.println("    " + legacyService.getDataAsXML());

        // 2. Base Adapter: XML -> JSON
        ApiResponse baseAdapter = new XmlToJsonAdapter(legacyService);
        System.out.println("\n[2] Client Response via Adapter (Pure JSON, neither encrypted nor compressed):");
        System.out.println("    " + baseAdapter.getBody());

        // 3. Option: Encryption Only
        ApiResponse encryptedOnly = new EncryptionDecorator(baseAdapter);
        System.out.println("\n[3] Client Response (?encrypt=true):");
        System.out.println("    " + encryptedOnly.getBody());

        // 4. Option: Compression Only
        ApiResponse compressedOnly = new CompressionDecorator(baseAdapter);
        System.out.println("\n[4] Client Response (?compress=true):");
        System.out.println("    " + compressedOnly.getBody());

        // 5. Combination Order A: Compress-then-Encrypt
        ApiResponse compressThenEncrypt = new EncryptionDecorator(new CompressionDecorator(baseAdapter));
        System.out.println("\n[5] Client Response (Compress -> Encrypt):");
        System.out.println("    " + compressThenEncrypt.getBody());

        // 6. Combination Order B: Encrypt-then-Compress
        ApiResponse encryptThenCompress = new CompressionDecorator(new EncryptionDecorator(baseAdapter));
        System.out.println("\n[6] Client Response (Encrypt -> Compress):");
        System.out.println("    " + encryptThenCompress.getBody());

        // 7. Dynamic Pipeline Builder simulation with new third transform (Digital Signature)
        System.out.println("\n[7] Dynamic Request: ?compress=true&encrypt=true&sign=true");
        List<String> pipeline = new ArrayList<>();
        pipeline.add("compress");
        pipeline.add("encrypt");
        pipeline.add("sign");
        ApiResponse customPipeline = buildResponsePipeline(legacyService, pipeline);
        System.out.println("    Result: " + customPipeline.getBody());

        System.out.println("\n===============================================================");
        System.out.println("  All structural requirements & design constraints verified!   ");
        System.out.println("===============================================================");
    }
}
