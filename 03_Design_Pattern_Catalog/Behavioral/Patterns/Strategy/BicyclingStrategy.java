import java.util.Arrays;
import java.util.List;

/**
 * Concrete Strategy: BicyclingStrategy
 * Optimizes for dedicated bike lanes, elevation moderation, and greenway
 * corridors.
 */
public class BicyclingStrategy implements RouteStrategy {

    @Override
    public Route calculateRoute(Location start, Location end) {
        double straightDist = start.distanceTo(end);
        double distance = straightDist * 1.22;
        // Average cycling speed: 16 km/h
        int duration = (int) Math.round((distance / 16.0) * 60.0);
        double cost = 0.0;

        List<String> directions = Arrays.asList(
                "Unlock bicycle at " + start.getName() + " bikeshare dock",
                "Head north along Riverfront Protected Bike Greenway (4.2 km)",
                "Turn right onto 5th Ave Protected Cycle Track",
                "Lock bicycle at dock near " + end.getName());

        return new Route("Bicycling", distance, duration, cost, directions);
    }
}
