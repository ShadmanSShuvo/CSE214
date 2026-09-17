import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Adapter: Adapts the legacy XML internal service to the target ApiResponse interface,
 * converting internal XML responses into JSON format required by third-party clients.
 */
public class XmlToJsonAdapter implements ApiResponse {
    private final LegacyInternalXMLService xmlService;

    public XmlToJsonAdapter(LegacyInternalXMLService xmlService) {
        this.xmlService = xmlService;
    }

    @Override
    public String getBody() {
        String xml = xmlService.getDataAsXML();
        return convertXmlToJson(xml);
    }

    /**
     * Converts simple XML tags to clean JSON format.
     * E.g.: <user><id>101</id><name>Ishrat</name></user> -> {"id": "101", "name": "Ishrat"}
     */
    private String convertXmlToJson(String xml) {
        String id = extractTagValue(xml, "id");
        String name = extractTagValue(xml, "name");

        if (id != null && name != null) {
            return String.format("{\"id\": \"%s\", \"name\": \"%s\"}", id, name);
        }
        // Generic fallback for any tag
        return "{\"raw_xml\": \"" + xml.replace("\"", "\\\"") + "\"}";
    }

    private String extractTagValue(String xml, String tag) {
        Pattern pattern = Pattern.compile("<" + tag + ">(.*?)</" + tag + ">");
        Matcher matcher = pattern.matcher(xml);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
