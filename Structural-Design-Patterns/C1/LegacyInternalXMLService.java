public class LegacyInternalXMLService implements ApiResponse {
    @Override
    public String getBody(String format) {
        if ("XML".equalsIgnoreCase(format)) {
            return getDataAsXML();
        }
        throw new IllegalArgumentException("Unsupported format: " + format);
    }
    public String getDataAsXML(String format) {
        if ("XML".equalsIgnoreCase(format)) {
            return getDataAsXML();
        }
        throw new IllegalArgumentException("Unsupported format: " + format);
    }
    public String getDataAsXML() {
        return "<user><id>101</id><name>Ishrat</name></user>";
    }
}
