import java.util.Collections;
import java.util.List;

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
 * affectedLocations supports one or more locations (e.g. a single
 * earthquake alert that spans several districts).
 */
class Alert {
    private final String title;
    private final AlertCategory category;
    private final List<String> affectedLocations;
    private final String severityLevel;
    private final String safetyInstructions;

    // Convenience constructor for a single affected location.
    public Alert(String title, AlertCategory category, String affectedLocation,
                 String severityLevel, String safetyInstructions) {
        this(title, category, List.of(affectedLocation), severityLevel, safetyInstructions);
    }

    // Constructor for multiple affected locations.
    public Alert(String title, AlertCategory category, List<String> affectedLocations,
                 String severityLevel, String safetyInstructions) {
        this.title = title;
        this.category = category;
        this.affectedLocations = Collections.unmodifiableList(affectedLocations);
        this.severityLevel = severityLevel;
        this.safetyInstructions = safetyInstructions;
    }

    public AlertCategory getCategory() {
        return category;
    }

    public List<String> getAffectedLocations() {
        return affectedLocations;
    }

    public String getSummary() {
        return "[" + category + "] " + title +
                " | Location(s): " + String.join(", ", affectedLocations) +
                " | Severity: " + severityLevel +
                " | Instructions: " + safetyInstructions;
    }
}
