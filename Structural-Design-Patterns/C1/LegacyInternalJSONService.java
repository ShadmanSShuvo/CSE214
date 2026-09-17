public class LegacyInternalJSONService implements ApiResponse {
    @Override
    public String getBody(String format) {
        if ("JSON".equalsIgnoreCase(format)) {
            return getDataAsJSON();
        }
        throw new IllegalArgumentException("Unsupported format: " + format);
    }
    public String getDataAsJSON(String format) {
        if ("JSON".equalsIgnoreCase(format)) {
            return getDataAsJSON();
        }
        throw new IllegalArgumentException("Unsupported format: " + format);
    }
    public String getDataAsJSON() {
        // Simulate fetching data and converting it to JSON format
        return "{ \"user\": { \"id\": 101, \"name\": \"Ishrat\" } }";
    }
}
