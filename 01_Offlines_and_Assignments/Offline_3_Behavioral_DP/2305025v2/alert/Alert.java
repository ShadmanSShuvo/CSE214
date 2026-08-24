public class Alert {
    private final String title;
    private final AlertCategory category;
    private final String location;
    private final String severity;
    private final String instruction;

    public Alert(String title, AlertCategory category, String location, String severity, String instruction) {
        this.title = title;
        this.category = category;
        this.location = location;
        this.severity = severity;
        this.instruction = instruction;
    }

    public AlertCategory getCategory() {
        return category;
    }

    public String getSummary() {
        return "[" + category + "] " + title
                + " | Location: " + location
                + " | Severity: " + severity
                + " | Instruction: " + instruction;
    }
}
