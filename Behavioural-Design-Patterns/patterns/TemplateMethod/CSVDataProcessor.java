import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete Subclass: CSVDataProcessor
 * Implements CSV-specific parsing and extraction primitives.
 */
public class CSVDataProcessor extends DataProcessor {

    @Override
    protected String readRawData(String sourcePath) {
        System.out.println("[CSV Reader] Opening file stream: " + sourcePath);
        // Simulated CSV payload with header and data lines (one invalid entry for
        // validation demo)
        return "id,name,role,salary\n"
                + "101,John Doe,Developer,85000\n"
                + "102,Jane Smith,Architect,115000\n"
                + ",Empty Person,Unknown,0\n" // Malformed (missing ID)
                + "104,Bob Jones,Manager,95000";
    }

    @Override
    protected List<ParsedRecord> parseRecords(String rawData) {
        List<ParsedRecord> list = new ArrayList<>();
        String[] lines = rawData.split("\n");
        if (lines.length <= 1)
            return list;

        String[] headers = lines[0].split(",");

        for (int i = 1; i < lines.length; i++) {
            String[] tokens = lines[i].split(",", -1);
            Map<String, String> attrs = new HashMap<>();
            String id = tokens.length > 0 ? tokens[0] : "";
            String name = tokens.length > 1 ? tokens[1] : "";

            for (int h = 2; h < headers.length && h < tokens.length; h++) {
                attrs.put(headers[h], tokens[h]);
            }
            list.add(new ParsedRecord(id, name, attrs));
        }
        return list;
    }

    @Override
    protected String getFormatName() {
        return "CSV / Delimited File";
    }
}
