import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Test Driver: Strategy Pattern Template Demonstration
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("         STRATEGY DESIGN PATTERN TEMPLATE DEMO            ");
        System.out.println("==========================================================");

        // 1. Setup Domain Waypoints
        Location centralStation = new Location("Central Station", 40.7128, -74.0060);
        Location techPark = new Location("Innovation Tech Park", 40.7589, -73.9851);

        // 2. Instantiate Strategies
        RouteStrategy drivingFastest = new DrivingStrategy(false);
        RouteStrategy drivingTollFree = new DrivingStrategy(true);
        RouteStrategy walking = new WalkingStrategy();
        RouteStrategy bicycling = new BicyclingStrategy();
        RouteStrategy publicTransit = new PublicTransitStrategy();

        // 3. Initialize Context with default strategy (Driving)
        System.out.println("\n--- Section 1: Initial Strategy (Driving) ---");
        Navigator navigator = new Navigator(drivingFastest);
        Route drivingRoute = navigator.calculateRoute(centralStation, techPark);
        drivingRoute.printRouteDetails();

        // 4. Runtime Strategy Switching to Public Transit
        System.out.println("\n--- Section 2: Dynamic Strategy Switch (Public Transit) ---");
        navigator.setStrategy(publicTransit);
        Route transitRoute = navigator.calculateRoute(centralStation, techPark);
        transitRoute.printRouteDetails();

        // 5. Runtime Strategy Switching to Bicycling
        System.out.println("\n--- Section 3: Dynamic Strategy Switch (Bicycling) ---");
        navigator.setStrategy(bicycling);
        Route bikeRoute = navigator.calculateRoute(centralStation, techPark);
        bikeRoute.printRouteDetails();

        // 6. Modern Java Functional / Lambda Strategy (Autonomous Drone Taxi)
        System.out.println("\n--- Section 4: Functional / Lambda Strategy Injection ---");
        RouteStrategy droneTaxiStrategy = RouteStrategy.of("AutonomousDroneTaxi", (start, end) -> {
            double straightLineDist = start.distanceTo(end);
            int flightDurationMinutes = 4; // High-speed direct line
            double premiumFare = 35.00;
            return new Route(
                    "Autonomous Drone Taxi",
                    straightLineDist,
                    flightDurationMinutes,
                    premiumFare,
                    Collections.singletonList("Direct flight corridor from " + start.getName() + " rooftop pad to "
                            + end.getName() + " vertiport"));
        });

        navigator.setStrategy(droneTaxiStrategy);
        Route droneRoute = navigator.calculateRoute(centralStation, techPark);
        droneRoute.printRouteDetails();

        // 7. Comparative Decision Analysis
        System.out.println("\n--- Section 5: Comparative Evaluation of All Strategies ---");
        List<RouteStrategy> allStrategies = Arrays.asList(
                drivingFastest,
                drivingTollFree,
                publicTransit,
                bicycling,
                walking,
                droneTaxiStrategy);
        navigator.compareStrategies(centralStation, techPark, allStrategies);

        System.out.println("==========================================================");
        System.out.println("        STRATEGY PATTERN DEMO COMPLETED SUCCESSFULLY      ");
        System.out.println("==========================================================");
    }
}
