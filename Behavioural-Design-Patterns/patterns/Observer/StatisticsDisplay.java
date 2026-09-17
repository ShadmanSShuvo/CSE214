/**
 * Concrete Observer: StatisticsDisplay
 * Computes and displays historical statistics (average, minimum, maximum
 * temperature).
 */
public class StatisticsDisplay implements Observer, DisplayElement {
    private float maxTemp = Float.MIN_VALUE;
    private float minTemp = Float.MAX_VALUE;
    private float tempSum = 0.0f;
    private int numReadings = 0;
    private final Subject weatherData;

    public StatisticsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void update(WeatherData data) {
        float temp = data.getTemperature();
        tempSum += temp;
        numReadings++;

        if (temp > maxTemp) {
            maxTemp = temp;
        }
        if (temp < minTemp) {
            minTemp = temp;
        }

        display();
    }

    @Override
    public void display() {
        float avg = tempSum / numReadings;
        System.out.printf(
                "  [Weather Statistics] Avg Temp: %.1f°C | Min Temp: %.1f°C | Max Temp: %.1f°C (over %d readings)%n",
                avg, minTemp, maxTemp, numReadings);
    }
}
