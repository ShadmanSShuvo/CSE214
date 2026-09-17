/**
 * Observer Interface: Observer
 * Implemented by all subscriber components that wish to be updated by a
 * Subject.
 */
public interface Observer {
    /**
     * Called when the observed Subject changes its state.
     * Demonstrates the Hybrid Push/Pull model: receives a reference to the Subject,
     * allowing the observer to pull specifically what it needs.
     */
    void update(WeatherData weatherData);
}
