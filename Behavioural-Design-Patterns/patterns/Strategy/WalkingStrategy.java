import java.util.Arrays;
import java.util.List;

/**
 * Concrete Strategy: WalkingStrategy
 * Prioritizes pedestrian walkways, parks, footbridges, and crosswalks. Zero
 * monetary cost.
 */
public class WalkingStrategy implements RouteStrategy {

    @Override
    public Route calculateRoute(Location start, Location end) {
        double straightDist = start.distanceTo(end);
        // Pedestrian footpaths can cut through parks (~1.15x)
        double distance = straightDist * 1.15;
        // Average walking speed: 4.8 km/h
        int duration = (int) Math.round((distance / 4.8) * 60.0);
        double cost = 0.0;

        List<String> directions = Arrays.asList(
                "Exit " + start.getName() + " on foot via Central Plaza Walkway",
                "Cut through Memorial Park pedestrian trail",
                "Cross Broadway St at pedestrian signal",
                "Arrive at destination: " + end.getName());

        return new Route("Walking", distance, duration, cost, directions);
    }
}
