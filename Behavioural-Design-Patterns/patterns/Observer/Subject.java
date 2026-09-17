/**
 * Subject Interface: Subject
 * Defines methods for subscribing, unsubscribing, and notifying observers.
 */
public interface Subject {
    /**
     * Registers an observer to receive state change notifications.
     */
    void registerObserver(Observer o);

    /**
     * Removes an observer from the notification list.
     */
    void removeObserver(Observer o);

    /**
     * Notifies all registered observers that the state has changed.
     */
    void notifyObservers();
}
