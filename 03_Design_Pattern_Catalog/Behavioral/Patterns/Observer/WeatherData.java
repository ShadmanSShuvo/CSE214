import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Subject: WeatherData
 * Collects data from physical sensors and notifies registered observers of any
 * changes.
 */
public class WeatherData implements Subject {
    private final List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;
    private boolean changed;

    public WeatherData() {
        this.observers = new ArrayList<>();
        this.changed = false;
    }

    @Override
    public void registerObserver(Observer o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
            System.out.println("[WeatherData] Registered observer: " + o.getClass().getSimpleName());
        }
    }

    @Override
    public void removeObserver(Observer o) {
        if (observers.remove(o)) {
            System.out.println("[WeatherData] Unregistered observer: " + o.getClass().getSimpleName());
        }
    }

    @Override
    public void notifyObservers() {
        if (changed) {
            // Create a defensive copy in case an observer modifies subscriptions during
            // notification
            List<Observer> observersSnapshot = new ArrayList<>(this.observers);
            for (Observer observer : observersSnapshot) {
                observer.update(this);
            }
            this.changed = false;
        }
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.changed = true;
        measurementsChanged();
    }

    public void measurementsChanged() {
        notifyObservers();
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public int getObserverCount() {
        return observers.size();
    }
}
