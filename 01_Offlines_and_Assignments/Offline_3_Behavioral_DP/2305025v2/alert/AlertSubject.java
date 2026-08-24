import java.util.ArrayList;
import java.util.List;

public class AlertSubject implements Subject {
    private final AlertCategory category;
    private final List<AlertObserver> observers = new ArrayList<>();

    public AlertSubject(AlertCategory category) {
        this.category = category;
    }

    @Override
    public void attach(AlertObserver observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }

    @Override
    public void detach(AlertObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Alert alert) {
        // Thread-safe iteration using an unmodifiable snapshot (Java 10+)
        for (AlertObserver observer : List.copyOf(observers)) {
            observer.update(alert);
        }
    }

    public boolean contains(AlertObserver observer) {
        return observers.contains(observer);
    }
}
