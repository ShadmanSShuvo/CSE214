# Batch 21 Online 2 (A1) - Weather Service (Adapter Pattern)

## Problem Statement
A weather forecasting application relies on a `WeatherProvider` target interface:
```java
interface WeatherProvider {
    String fetchWeather();
}
```
The application core class is `WeatherApp`:
```java
class WeatherApp {
    private WeatherProvider weatherProvider;
    public WeatherApp(WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
    }
    public void displayWeather() {
        System.out.println(weatherProvider.fetchWeather());
    }
}
```
You are provided with a legacy weather service that cannot be modified:
```java
class LegacyWeatherService {
    public String getWeatherData() {
        return "Legacy weather data";
    }
}
```
**Task**: Use an appropriate structural design pattern so that `LegacyWeatherService` can be utilized by `WeatherApp` without modifying `LegacyWeatherService` or `WeatherApp`.

---

## Design Pattern Analysis

### Pattern Applied: **Adapter Pattern (Object Adapter)**

### Why Adapter?
- **Interface Incompatibility**: `WeatherApp` expects `fetchWeather()`, while `LegacyWeatherService` exposes `getWeatherData()`.
- **Closed for Modification**: The prompt explicitly specifies: *"Legacy Weather Service, which we cannot modify"*.
- **The Solution**: An object adapter `WeatherServiceAdapter` implements `WeatherProvider`, encapsulates an instance of `LegacyWeatherService`, and redirects calls to `fetchWeather()` to `legacyWeatherService.getWeatherData()`.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Target Interface** | `WeatherProvider` | Defines domain-specific method `fetchWeather()` expected by client. |
| **Adaptee** | `LegacyWeatherService` | Existing legacy class with method `getWeatherData()`. Cannot be altered. |
| **Adapter** | `WeatherServiceAdapter` | Implements `WeatherProvider`, holds reference to `LegacyWeatherService`, translates method call. |
| **Client** | `WeatherApp`, `A1` | Uses `WeatherProvider` to retrieve and display weather information. |

---

## Class Architecture

```
         <<interface>>
        WeatherProvider
       +fetchWeather(): String
              ^
              | implements
    WeatherServiceAdapter  ------>  LegacyWeatherService (Adaptee)
    -legacyService: Legacy          +getWeatherData(): String
    +fetchWeather(): String
              ^
              | uses
          WeatherApp
    +displayWeather(): void
```

---

## Solution Walkthrough

1. **Adapter Implementation**:
   ```java
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
   ```
2. **Client Wiring**:
   ```java
   LegacyWeatherService legacy = new LegacyWeatherService();
   WeatherProvider adapter = new WeatherServiceAdapter(legacy);
   WeatherApp app = new WeatherApp(adapter);
   app.displayWeather(); // prints "Legacy weather data"
   ```

---

## How to Compile & Run

```bash
cd Batch_21/A1_WeatherServiceAdapter
javac *.java
java A1
```

### Output
```
Legacy weather data
```
