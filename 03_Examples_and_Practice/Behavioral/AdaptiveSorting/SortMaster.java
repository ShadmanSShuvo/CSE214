/**
 * Public entry point. This is the "master program": call SortMaster.sort(arr)
 * and it analyzes the array, picks an algorithm, and sorts in place.
 *
 * Usage:
 *   int[] data = {5, 3, 8, 1, 9};
 *   SortMaster.sort(data);                       // auto-selects algorithm
 *   SortMaster.sort(data, SortSelector.Priority.STABILITY); // force a priority
 *   SortMaster.Result r = SortMaster.sortVerbose(data);      // get the decision + timing
 */
public class SortMaster {

    public static class Result {
        public final SortAlgorithm algorithmUsed;
        public final String reasoning;
        public final ArrayAnalyzer.Profile profile;
        public final long elapsedNanos;

        Result(SortAlgorithm algo, String reasoning, ArrayAnalyzer.Profile profile, long elapsedNanos) {
            this.algorithmUsed = algo;
            this.reasoning = reasoning;
            this.profile = profile;
            this.elapsedNanos = elapsedNanos;
        }

        @Override
        public String toString() {
            return String.format(
                "Algorithm: %s%nReasoning: %s%nProfile: %s%nTime: %.3f ms",
                algorithmUsed, reasoning, profile, elapsedNanos / 1_000_000.0
            );
        }
    }

    /** Sorts in place using automatic algorithm selection. Simple entry point. */
    public static void sort(int[] arr) {
        sortVerbose(arr, SortSelector.Priority.SPEED);
    }

    /** Sorts in place, letting you bias selection toward stability or low memory. */
    public static void sort(int[] arr, SortSelector.Priority priority) {
        sortVerbose(arr, priority);
    }

    /** Sorts in place and returns full details: which algorithm, why, and how long it took. */
    public static Result sortVerbose(int[] arr) {
        return sortVerbose(arr, SortSelector.Priority.SPEED);
    }

    public static Result sortVerbose(int[] arr, SortSelector.Priority priority) {
        ArrayAnalyzer.Profile profile = ArrayAnalyzer.analyze(arr);
        SortSelector.Decision decision = SortSelector.select(profile, priority);

        long start = System.nanoTime();
        SortSelector.execute(arr, decision.algorithm);
        long elapsed = System.nanoTime() - start;

        return new Result(decision.algorithm, decision.reasoning, profile, elapsed);
    }

    /** Forces a specific algorithm, bypassing selection. Useful for testing/benchmarking. */
    public static long sortWith(int[] arr, SortAlgorithm algo) {
        long start = System.nanoTime();
        SortSelector.execute(arr, algo);
        return System.nanoTime() - start;
    }
}
