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

    public void register(Citizen citizen) {
        citizens.put(citizen.getName(), citizen);
    }

    public void unregister(Citizen citizen) {
        citizens.remove(citizen.getName());
        for (AlertCategory category : AlertCategory.values()) {
            unsubscribe(citizen, category);
        }
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

    public boolean isSubscribed(Citizen citizen, AlertCategory category) {
        return subjects.get(category).contains(citizen);
    }
}
