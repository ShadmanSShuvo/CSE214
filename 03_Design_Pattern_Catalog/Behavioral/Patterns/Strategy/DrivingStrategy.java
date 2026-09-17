import java.util.Arrays;
import java.util.List;

/**
 * Concrete Strategy: DrivingStrategy
 * Optimizes for roadway travel, higher speeds, highway interchanges, and tolls.
 */
public class DrivingStrategy implements RouteStrategy {
    private final boolean avoidTolls;

    public DrivingStrategy() {
        this(false);
    }

    public DrivingStrategy(boolean avoidTolls) {
        this.avoidTolls = avoidTolls;
    }

    @Override
    public Route calculateRoute(Location start, Location end) {
        double straightDist = start.distanceTo(end);
        // Road networks have winding factors (~1.3x)
        double distance = straightDist * 1.35;
        // Average driving speed: 45 km/h in city with highway legs
        int duration = (int) Math.round((distance / 45.0) * 60.0);
        double fuelCost = (distance / 12.0) * 1.50; // 12km/L at $1.50/L
        double tollCost = avoidTolls ? 0.0 : 4.50;
        double totalCost = fuelCost + tollCost;

        List<String> directions = Arrays.asList(
                "Depart from " + start.getName() + " onto Interstate Hwy 101 North",
                avoidTolls ? "Take Exit 24B (Scenic Toll-Free Bypass)" : "Pass through Express Tollway Gate #3 ($4.50)",
                "Merge onto Tech Parkway West",
                "Arrive at destination: " + end.getName());

        return new Route("Driving (" + (avoidTolls ? "No Tolls" : "Fastest") + ")", distance, duration, totalCost,
                directions);
    }

    @Override
    public String getStrategyName() {
        return "DrivingStrategy" + (avoidTolls ? "[TollFree]" : "[Express]");
    }
}
