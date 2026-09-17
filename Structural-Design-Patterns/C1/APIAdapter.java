public class APIAdapter implements ApiResponse {
    private APIService apiService;
    public APIAdapter(APIService apiService) {
        this.apiService = apiService;
    }
    @Override
    public String getBody(String format) {
        if (apiService instanceof APIService) {
            if ("XML".equalsIgnoreCase(format)) {
                return apiService.getDataAsXML();
            } else if ("JSON".equalsIgnoreCase(format)) {
                return apiService.getDataAsJSON();
            }
        }
        throw new IllegalArgumentException("Unsupported format: " + format);
    }
}
