/**
 * Concrete Observer: CurrentConditionsDisplay
 * Displays the current temperature, humidity, and heat index.
 */
public class CurrentConditionsDisplay implements Observer, DisplayElement {
    private float temperature;
    private float humidity;
    private final Subject weatherData;

    public CurrentConditionsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void update(WeatherData data) {
        this.temperature = data.getTemperature();
        this.humidity = data.getHumidity();
        display();
    }

    @Override
    public void display() {
        System.out.printf("  [Current Conditions] Temp: %.1f°C | Humidity: %.1f%% | Heat Index: %.1f°C%n",
                temperature, humidity, computeHeatIndex(temperature, humidity));
    }

    private double computeHeatIndex(float t, float rh) {
        // Simplified formula for demonstration
        return t + 0.33 * (rh / 100.0 * 6.105 * Math.exp((17.27 * t) / (237.7 + t))) - 4.0;
    }
}
