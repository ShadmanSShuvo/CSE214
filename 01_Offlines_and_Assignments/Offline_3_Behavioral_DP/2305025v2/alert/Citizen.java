import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Citizen implements AlertObserver {
    private final String name;
    private final List<Alert> alerts = new ArrayList<>();

    public Citizen(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void update(Alert alert) {
        alerts.add(alert);
        // Retaining the real-time console ping to demonstrate the Observer pushing data
        System.out.println(name + " <- " + alert.getSummary());
    }

    // Securely expose the raw list data without allowing external modifications
    public List<Alert> getAlerts() {
        return Collections.unmodifiableList(alerts);
    }

    // Separated presentation logic - formats the string instead of printing
    // directly
    public String getFormattedAlertHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nNotifications: ").append(name).append("\n");

        if (alerts.isEmpty()) {
            sb.append("No notifications.\n");
            return sb.toString();
        }

        for (int i = 0; i < alerts.size(); i++) {
            sb.append((i + 1)).append(". ").append(alerts.get(i).getSummary()).append("\n");
        }
        return sb.toString();
    }
}
