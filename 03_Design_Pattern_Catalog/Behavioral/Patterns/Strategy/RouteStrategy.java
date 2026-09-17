import java.util.function.BiFunction;

/**
 * Strategy Interface: RouteStrategy
 * Functional interface declaring the algorithm contract for calculating routes
 * between two points.
 */
@FunctionalInterface
public interface RouteStrategy {
    /**
     * Computes optimal route between start and end locations according to the
     * specific transport strategy.
     */
    Route calculateRoute(Location start, Location end);

    /**
     * Returns the human-readable identifier of the strategy.
     */
    default String getStrategyName() {
        return getClass().getSimpleName();
    }

    /**
     * Factory helper to create named functional strategies cleanly using modern
     * Java lambdas.
     */
    static RouteStrategy of(String name, BiFunction<Location, Location, Route> calculator) {
        return new RouteStrategy() {
            @Override
            public Route calculateRoute(Location start, Location end) {
                return calculator.apply(start, end);
            }

            @Override
            public String getStrategyName() {
                return name;
            }
        };
    }
}
