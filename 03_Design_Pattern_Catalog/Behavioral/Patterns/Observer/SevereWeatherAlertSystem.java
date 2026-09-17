/**
 * Concrete Observer: SevereWeatherAlertSystem
 * Evaluates telemetry against safety thresholds to trigger emergency broadcast
 * warnings.
 */
public class SevereWeatherAlertSystem implements Observer, DisplayElement {
    private static final float EXTREME_HEAT_THRESHOLD = 38.0f;
    private static final float STORM_LOW_PRESSURE_THRESHOLD = 990.0f;

    private boolean heatAlert = false;
    private boolean stormAlert = false;
    private float lastTemp;
    private float lastPressure;

    public SevereWeatherAlertSystem(Subject weatherData) {
        weatherData.registerObserver(this);
    }

    @Override
    public void update(WeatherData data) {
        this.lastTemp = data.getTemperature();
        this.lastPressure = data.getPressure();

        this.heatAlert = lastTemp >= EXTREME_HEAT_THRESHOLD;
        this.stormAlert = lastPressure <= STORM_LOW_PRESSURE_THRESHOLD;

        if (heatAlert || stormAlert) {
            display();
        }
    }

    @Override
    public void display() {
        System.out.println("  🚨 [EMERGENCY ALERT SYSTEM ACTIVATED] 🚨");
        if (heatAlert) {
            System.out.printf("     ⚠️  DANGEROUS HEAT WARNING: Recorded %.1f°C exceeds safety threshold (%.1f°C)!%n",
                    lastTemp, EXTREME_HEAT_THRESHOLD);
        }
        if (stormAlert) {
            System.out.printf(
                    "     🌪️  CYCLONIC DEPRESSION WARNING: Barometric pressure dropped to %.1f hPa (Threshold: %.1f hPa)!%n",
                    lastPressure, STORM_LOW_PRESSURE_THRESHOLD);
        }
    }
}
