/**
 * Concrete Observer: ForecastDisplay
 * Predicts upcoming weather trends based on changes in barometric pressure.
 */
public class ForecastDisplay implements Observer, DisplayElement {
    private float currentPressure = 1013.25f;
    private float lastPressure;
    private final Subject weatherData;

    public ForecastDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        this.lastPressure = currentPressure;
        weatherData.registerObserver(this);
    }

    @Override
    public void update(WeatherData data) {
        lastPressure = currentPressure;
        currentPressure = data.getPressure();
        display();
    }

    @Override
    public void display() {
        System.out.print("  [Weather Forecast]   Barometric Trend: ");
        if (currentPressure > lastPressure + 1.0f) {
            System.out.println("Improving weather conditions on the way (High Pressure System) ☀️");
        } else if (currentPressure < lastPressure - 1.0f) {
            System.out.println("Watch out for cooler, rainy weather / approaching depression 🌧️");
        } else {
            System.out.println("Stable conditions continue (More of the same) ⛅");
        }
    }

    public void unsubscribe() {
        weatherData.removeObserver(this);
    }
}
