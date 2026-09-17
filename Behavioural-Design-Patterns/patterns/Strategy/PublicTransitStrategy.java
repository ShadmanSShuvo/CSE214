import java.util.Arrays;
import java.util.List;

/**
 * Concrete Strategy: PublicTransitStrategy
 * Combines short walking legs with subway/metro lines and rapid bus routes.
 */
public class PublicTransitStrategy implements RouteStrategy {
    private static final double STANDARD_TRANSIT_FARE = 2.75;

    @Override
    public Route calculateRoute(Location start, Location end) {
        double straightDist = start.distanceTo(end);
        double distance = straightDist * 1.40;
        // Transit fixed schedule wait time + travel duration
        int duration = 12 + (int) Math.round((distance / 32.0) * 60.0);

        List<String> directions = Arrays.asList(
                "Walk 3 mins from " + start.getName() + " to Central Subway Station",
                "Board Blue Line Metro Train towards North Terminal (6 stops)",
                "Transfer at Union Square to Express Bus #42",
                "Disembark at Innovation Blvd and walk 2 mins to " + end.getName());

        return new Route("Public Transit", distance, duration, STANDARD_TRANSIT_FARE, directions);
    }
}
