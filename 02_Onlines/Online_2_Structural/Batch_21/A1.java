// Legacy Weather Service, which we cannot modify
class LegacyWeatherService {
    public String getWeatherData() {
        return "Legacy weather data";
    }
}

// Client Interface expected by the application
interface WeatherProvider {
    String fetchWeather();
}

// Adapter: wraps LegacyWeatherService, exposes WeatherProvider interface
class WeatherServiceAdapter implements WeatherProvider {
    private LegacyWeatherService legacyWeatherService;

    public WeatherServiceAdapter(LegacyWeatherService legacyWeatherService) {
        this.legacyWeatherService = legacyWeatherService;
    }

    @Override
    public String fetchWeather() {
        return legacyWeatherService.getWeatherData();
    }
}

class WeatherApp {
    private WeatherProvider weatherProvider;

    public WeatherApp(WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
    }

    public void displayWeather() {
        System.out.println(weatherProvider.fetchWeather());
    }
}

public class A1 {
    public static void main(String[] args) {
        LegacyWeatherService legacyWeatherService = new LegacyWeatherService();
        WeatherProvider adapter = new WeatherServiceAdapter(legacyWeatherService);
        WeatherApp app = new WeatherApp(adapter);
        app.displayWeather(); // Output: Legacy weather data
    }
}
