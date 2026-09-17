import java.util.ArrayList;
import java.util.List;

/**
 * Subject interface (Observer Design Pattern).
 */
interface Subject {
    void attach(AlertObserver observer);
    void detach(AlertObserver observer);
    void notifyObservers(Alert alert);
}

/**
 * Concrete Subject: represents ONE disaster category (e.g. FLOOD).
 * Only citizens attached to this specific category subject are notified
 * when an alert of that category is published. Because attach() only
 * affects future notifyObservers() calls, a citizen who subscribes now
 * will never receive alerts that were already published earlier.
 */
class AlertCategorySubject implements Subject {
    private final AlertCategory category;
    private final List<AlertObserver> observers = new ArrayList<>();

    public AlertCategorySubject(AlertCategory category) {
        this.category = category;
    }

    public AlertCategory getCategory() {
        return category;
    }

    @Override
    public void attach(AlertObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void detach(AlertObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Alert alert) {
        System.out.println("Publishing " + category + " alert -> notifying "
                + observers.size() + " subscriber(s):");
        for (AlertObserver o : observers) {
            o.update(alert);
        }
    }

    public boolean isSubscribed(AlertObserver observer) {
        return observers.contains(observer);
    }
}
