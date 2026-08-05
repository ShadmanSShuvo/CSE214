
/**
 * The disaster categories supported by the system.
 */
enum AlertCategory {
    EARTHQUAKE,
    FLOOD,
    FIRE
}

/**
 * Immutable data object describing a single published alert.
 */
class Alert {
    private final String title;
    private final AlertCategory category;
    private final String affectedLocation;
    private final String severityLevel;
    private final String safetyInstructions;

    public Alert(String title, AlertCategory category, String affectedLocation,
            String severityLevel, String safetyInstructions) {
        this.title = title;
        this.category = category;
        this.affectedLocation = affectedLocation;
        this.severityLevel = severityLevel;
        this.safetyInstructions = safetyInstructions;
    }

    public AlertCategory getCategory() {
        return category;
    }

    public String getSummary() {
        return "[" + category + "] " + title +
                " | Location: " + affectedLocation +
                " | Severity: " + severityLevel +
                " | Instructions: " + safetyInstructions;
    }
}
