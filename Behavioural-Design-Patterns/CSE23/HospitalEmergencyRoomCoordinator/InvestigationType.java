public enum InvestigationType {
    PATHOLOGY_TEST("Pathology test"),
    RADIOLOGY_INVESTIGATION("Radiology investigation");

    private final String displayName;

    InvestigationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
