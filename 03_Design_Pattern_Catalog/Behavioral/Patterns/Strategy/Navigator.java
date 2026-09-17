import java.util.List;

/**
 * Context: Navigator
 * Configured with a concrete RouteStrategy object and maintains a reference to
 * a Strategy object.
 * Can change strategies dynamically at runtime based on user preference or
 * external constraints.
 */
public class Navigator {
    private RouteStrategy strategy;

    public Navigator(RouteStrategy initialStrategy) {
        this.strategy = initialStrategy;
    }

    public void setStrategy(RouteStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }
        System.out.println("\n[Navigator] Switched routing strategy to: " + strategy.getStrategyName());
        this.strategy = strategy;
    }

    public Route calculateRoute(Location start, Location end) {
        if (strategy == null) {
            throw new IllegalStateException("Routing strategy has not been set.");
        }
        System.out.println("[Navigator] Calculating route from " + start.getName() + " to " + end.getName()
                + " using " + strategy.getStrategyName() + "...");
        return strategy.calculateRoute(start, end);
    }

    /**
     * Helper to compare multiple strategies side-by-side for the user.
     */
    public void compareStrategies(Location start, Location end, List<RouteStrategy> candidateStrategies) {
        System.out.println("\n================ MULTI-MODAL ROUTE COMPARISON ================");
        System.out.printf("Origin: %s  |  Destination: %s%n", start.getName(), end.getName());
        System.out.printf("%-24s | %-12s | %-12s | %-10s%n", "Transport Strategy", "Distance", "Duration", "Est. Cost");
        System.out.println("------------------------------------------------------------------");

        for (RouteStrategy strat : candidateStrategies) {
            Route route = strat.calculateRoute(start, end);
            System.out.printf("%-24s | %8.1f km  | %7d mins  |  $%-8.2f%n",
                    strat.getStrategyName(), route.getDistanceKm(), route.getDurationMinutes(),
                    route.getEstimatedCostUsd());
        }
        System.out.println("==================================================================\n");
    }

    public RouteStrategy getStrategy() {
        return strategy;
    }
}
