/**
 * Test Driver: Observer Pattern Template Demonstration
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("          OBSERVER DESIGN PATTERN TEMPLATE DEMO           ");
        System.out.println("==========================================================");

        // 1. Create Subject
        WeatherData weatherStation = new WeatherData();

        // 2. Create and automatically register Observers
        System.out.println("\n--- Section 1: Registering Observers ---");
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherStation);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherStation);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherStation);
        SevereWeatherAlertSystem alertSystem = new SevereWeatherAlertSystem(weatherStation);

        System.out.println("Active subscribers: " + weatherStation.getObserverCount());

        // 3. First Telemetry Update (Mild Spring Day)
        System.out.println("\n--- Section 2: First Weather Telemetry Update ---");
        System.out.println(">> Sensor reading: 24.0°C, 65% humidity, 1014.2 hPa");
        weatherStation.setMeasurements(24.0f, 65.0f, 1014.2f);

        // 4. Second Telemetry Update (Warmer afternoon)
        System.out.println("\n--- Section 3: Second Weather Telemetry Update ---");
        System.out.println(">> Sensor reading: 29.5°C, 70% humidity, 1012.0 hPa");
        weatherStation.setMeasurements(29.5f, 70.0f, 1012.0f);

        // 5. Dynamic Unsubscription
        System.out.println("\n--- Section 4: Dynamic Unsubscription ---");
        System.out.println("Forecast display opts out of telemetry updates.");
        forecastDisplay.unsubscribe();
        System.out.println("Active subscribers: " + weatherStation.getObserverCount());

        // 6. Third Telemetry Update (Severe Cyclonic Storm Event)
        System.out.println("\n--- Section 5: Third Weather Telemetry Update (Extreme Heat & Storm) ---");
        System.out.println(">> Sensor reading: 40.5°C, 92% humidity, 978.0 hPa");
        weatherStation.setMeasurements(40.5f, 92.0f, 978.0f);

        System.out.println("\n==========================================================");
        System.out.println("          OBSERVER PATTERN DEMO COMPLETED SUCCESSFULLY    ");
        System.out.println("==========================================================");
    }
}
