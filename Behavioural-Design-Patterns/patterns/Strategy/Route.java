import java.util.List;

/**
 * Domain Model: Route
 * Encapsulates the output produced by a routing strategy.
 */
public class Route {
    private final String transportMode;
    private final double distanceKm;
    private final int durationMinutes;
    private final double estimatedCostUsd;
    private final List<String> directions;

    public Route(String transportMode, double distanceKm, int durationMinutes, double estimatedCostUsd,
            List<String> directions) {
        this.transportMode = transportMode;
        this.distanceKm = distanceKm;
        this.durationMinutes = durationMinutes;
        this.estimatedCostUsd = estimatedCostUsd;
        this.directions = directions;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public double getEstimatedCostUsd() {
        return estimatedCostUsd;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void printRouteDetails() {
        System.out.printf("  Mode: %-16s | Distance: %5.1f km | Duration: %3d mins | Cost: $%5.2f%n",
                transportMode, distanceKm, durationMinutes, estimatedCostUsd);
        System.out.println("  Waypoints / Directions:");
        for (int i = 0; i < directions.size(); i++) {
            System.out.println("    " + (i + 1) + ". " + directions.get(i));
        }
    }
}
