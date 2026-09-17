import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete Subclass: JSONDataProcessor
 * Implements JSON-specific parsing and customizes the postProcessHook for
 * indexing.
 */
public class JSONDataProcessor extends DataProcessor {

    @Override
    protected String readRawData(String sourcePath) {
        System.out.println("[JSON Reader] Ingesting REST API payload: " + sourcePath);
        // Simulated JSON payload
        return "[\n"
                + "  {\"id\":\"J-201\", \"name\":\"Server Alpha\", \"status\":\"ONLINE\", \"region\":\"us-east-1\"},\n"
                + "  {\"id\":\"J-202\", \"name\":\"Server Beta\", \"status\":\"OFFLINE\", \"region\":\"eu-west-1\"}\n"
                + "]";
    }

    @Override
    protected List<ParsedRecord> parseRecords(String rawData) {
        List<ParsedRecord> records = new ArrayList<>();
        // Lightweight simulated parser for demonstration
        String cleaned = rawData.replace("[", "").replace("]", "").trim();
        String[] objects = cleaned.split("},");

        for (String obj : objects) {
            String trimmed = obj.replace("{", "").replace("}", "").trim();
            String[] pairs = trimmed.split(",");
            String id = "";
            String name = "";
            Map<String, String> attrs = new HashMap<>();

            for (String pair : pairs) {
                String[] kv = pair.split(":");
                if (kv.length == 2) {
                    String k = kv[0].replace("\"", "").trim();
                    String v = kv[1].replace("\"", "").trim();
                    if ("id".equals(k))
                        id = v;
                    else if ("name".equals(k))
                        name = v;
                    else
                        attrs.put(k, v);
                }
            }
            records.add(new ParsedRecord(id, name, attrs));
        }
        return records;
    }

    @Override
    protected void postProcessHook(List<ParsedRecord> validRecords) {
        System.out.println(
                "  🔔 [JSON Hook] Notifying Kafka cluster: " + validRecords.size() + " server records synchronized.");
        System.out.println("  🔔 [JSON Hook] Flushed Elasticsearch index cache.");
    }

    @Override
    protected String getFormatName() {
        return "JSON REST Document";
    }
}
