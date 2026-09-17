import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class AlertSystem {
    private final Map<String, Citizen> citizens = new HashMap<>();
    private final Map<AlertCategory, AlertSubject> subjects = new EnumMap<>(AlertCategory.class);

    public AlertSystem() {
        for (AlertCategory category : AlertCategory.values())
            subjects.put(category, new AlertSubject(category));
    }

    public Citizen register(String name) {
        Citizen citizen = new Citizen(name);
        citizens.put(name, citizen);
        return citizen;
    }

    public void subscribe(Citizen citizen, AlertCategory... categories) {
        for (AlertCategory category : categories)
            subjects.get(category).attach(citizen);
    }

    public void unsubscribe(Citizen citizen, AlertCategory category) {
        subjects.get(category).detach(citizen);
    }

    public void publish(Alert alert) {
        subjects.get(alert.getCategory()).notifyObservers(alert);
    }

    public boolean isSubscribed(
            Citizen citizen,
            AlertCategory category) {
        return subjects.get(category).contains(citizen);
    }
}
