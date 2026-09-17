public class APIService {
    public String getDataAsXML() {
        LegacyInternalXMLService legacyService = new LegacyInternalXMLService();
        return legacyService.getDataAsXML();
    }
    public String getDataAsJSON() {
        LegacyInternalJSONService legacyService = new LegacyInternalJSONService();
        return legacyService.getDataAsJSON();
    }
}
