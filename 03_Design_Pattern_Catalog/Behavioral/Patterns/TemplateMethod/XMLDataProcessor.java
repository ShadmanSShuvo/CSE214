import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete Subclass: XMLDataProcessor
 * Implements XML extraction and overrides preProcessHook and
 * applyBusinessRules.
 */
public class XMLDataProcessor extends DataProcessor {

    @Override
    protected void preProcessHook(String sourcePath) {
        System.out.println("  🛡️ [XML Hook] Validating XML namespace and XSD schema definitions for " + sourcePath);
    }

    @Override
    protected String readRawData(String sourcePath) {
        System.out.println("[XML Reader] Streaming XML document: " + sourcePath);
        return "<dataset>\n"
                + "  <record id=\"X-901\"><name>Widget Pro</name><price>29.99</price><category>Hardware</category></record>\n"
                + "  <record id=\"X-902\"><name>Gizmo Ultra</name><price>49.99</price><category>Gadgets</category></record>\n"
                + "</dataset>";
    }

    @Override
    protected List<ParsedRecord> parseRecords(String rawData) {
        List<ParsedRecord> records = new ArrayList<>();
        String[] entries = rawData.split("<record");

        for (int i = 1; i < entries.length; i++) {
            String entry = entries[i];
            String id = extractTagValue(entry, "id=\"", "\"");
            String name = extractTagValue(entry, "<name>", "</name>");
            String price = extractTagValue(entry, "<price>", "</price>");
            String category = extractTagValue(entry, "<category>", "</category>");

            Map<String, String> attrs = new HashMap<>();
            attrs.put("price", price);
            attrs.put("category", category);

            records.add(new ParsedRecord(id, name, attrs));
        }
        return records;
    }

    @Override
    protected void applyBusinessRules(List<ParsedRecord> records) {
        System.out
                .println("[ETL Step 4 - XML Override] Applying 8.5% Import Customs Tariff and Currency Normalization.");
        for (ParsedRecord r : records) {
            String priceStr = r.getAttributes().get("price");
            if (priceStr != null) {
                try {
                    double originalPrice = Double.parseDouble(priceStr);
                    double tariffPrice = originalPrice * 1.085;
                    System.out.printf("   * Adjusted item %s (%s): $%.2f -> $%.2f (with tariff)%n",
                            r.getId(), r.getEntityName(), originalPrice, tariffPrice);
                } catch (NumberFormatException ignored) {
                }
            }
        }
    }

    private String extractTagValue(String text, String startTag, String endTag) {
        int s = text.indexOf(startTag);
        if (s == -1)
            return "";
        s += startTag.length();
        int e = text.indexOf(endTag, s);
        return e != -1 ? text.substring(s, e) : "";
    }

    @Override
    protected String getFormatName() {
        return "XML Enterprise Dataset";
    }
}
