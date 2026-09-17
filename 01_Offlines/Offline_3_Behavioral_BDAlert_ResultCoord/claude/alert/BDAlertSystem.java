
import java.util.HashMap;
import java.util.Map;

/**
 * BD Alert System.
 *
 * Manages:
 * - Citizen registration
 * - Subscriptions per AlertCategory (backed by a Subject per category)
 * - Publishing alerts so only the correctly subscribed citizens are notified
 *
 * This class is the "administrative" layer sitting on top of the
 * Observer pattern implemented by Subject / AlertCategorySubject / Citizen.
 */
class BDAlertSystem {
    private final Map<String, Citizen> citizens = new HashMap<>();
    private final Map<AlertCategory, AlertCategorySubject> categorySubjects = new HashMap<>();

    public BDAlertSystem() {
        for (AlertCategory category : AlertCategory.values()) {
            categorySubjects.put(category, new AlertCategorySubject(category));
        }
    }

    // 1. Register citizens in the system.
    public Citizen registerCitizen(String name) {
        Citizen citizen = new Citizen(name);
        citizens.put(name, citizen);
        System.out.println("Registered citizen: " + name);
        return citizen;
    }

    // 2. Subscribe a citizen to one or more disaster categories.
    public void subscribe(Citizen citizen, AlertCategory... categories) {
        for (AlertCategory category : categories) {
            categorySubjects.get(category).attach(citizen);
            System.out.println(citizen.getName() + " subscribed to " + category);
        }
    }

    // 3. Update subscriptions / unsubscribe from a category at any time.
    public void unsubscribe(Citizen citizen, AlertCategory category) {
        categorySubjects.get(category).detach(citizen);
        System.out.println(citizen.getName() + " unsubscribed from " + category);
    }

    // 4. Publish an alert; 5. Notify only citizens subscribed to that category.
    public void publishAlert(Alert alert) {
        System.out.println("\n=== PUBLISHING ALERT ===");
        System.out.println(alert.getSummary());
        categorySubjects.get(alert.getCategory()).notifyObservers(alert);
        System.out.println("=== END OF PUBLICATION ===\n");
    }

    public boolean isSubscribed(Citizen citizen, AlertCategory category) {
        return categorySubjects.get(category).isSubscribed(citizen);
    }
}
