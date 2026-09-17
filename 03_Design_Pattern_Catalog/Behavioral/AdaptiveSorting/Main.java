import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Correctness tests ===");
        runCorrectnessTests();

        System.out.println();
        System.out.println("=== Selector demo across different array shapes ===");
        runSelectorDemo();

        System.out.println();
        System.out.println("=== Benchmark: selector's choice vs. all algorithms ===");
        runBenchmarkComparison();
    }

    // ---------------------------------------------------------------
    // Correctness: verify every algorithm actually sorts correctly,
    // including edge cases (empty, single element, all-duplicates,
    // negatives, already sorted, reverse sorted).
    // ---------------------------------------------------------------
    private static void runCorrectnessTests() {
        Random rng = new Random(42);
        int[][] testCases = {
            {},
            {1},
            {2, 1},
            {5, 5, 5, 5, 5},
            {-5, -1, -100, 0, 3, -3},
            makeSorted(1000),
            makeReverseSorted(1000),
            makeRandom(1000, rng),
            makeRandom(5000, rng),
        };

        for (SortAlgorithm algo : SortAlgorithm.values()) {
            boolean allPassed = true;
            for (int[] original : testCases) {
                int[] copy = Arrays.copyOf(original, original.length);
                SortSelector.execute(copy, algo);
                int[] expected = Arrays.copyOf(original, original.length);
                Arrays.sort(expected);
                if (!Arrays.equals(copy, expected)) {
                    allPassed = false;
                    System.out.println("  FAILED: " + algo + " on input of size " + original.length);
                }
            }
            System.out.println((allPassed ? "  PASS  " : "  FAIL  ") + algo);
        }
    }

    // ---------------------------------------------------------------
    // Show what the selector picks (and why) for several distinct
    // "shapes" of input data.
    // ---------------------------------------------------------------
    private static void runSelectorDemo() {
        Random rng = new Random(7);

        demoOne("Tiny array (n=10)", makeRandom(10, rng));
        demoOne("Nearly sorted (n=5000)", makeNearlySorted(5000, rng));
        demoOne("Small integer range (n=100000, values 0-500)", makeSmallRange(100_000, 500, rng));
        demoOne("Uniform wide range (n=100000, values 0-10000000)", makeUniform(100_000, 10_000_000, rng));
        demoOne("Heavy duplicates (n=50000, only 5 distinct values)", makeHeavyDuplicates(50_000, 5, rng));
        demoOne("Large random general case (n=200000)", makeRandom(200_000, rng));
    }

    private static void demoOne(String label, int[] arr) {
        System.out.println("-- " + label + " --");
        SortMaster.Result result = SortMaster.sortVerbose(arr);
        System.out.println(result);
        System.out.println("  Correctly sorted: " + isSorted(arr));
        System.out.println();
    }

    // ---------------------------------------------------------------
    // For a few array shapes, run every algorithm and print timings,
    // so you can see the selector's pick is actually competitive.
    // ---------------------------------------------------------------
    private static void runBenchmarkComparison() {
        Random rng = new Random(123);

        benchmarkShape("Small range array (n=200000, range 0-1000)",
            () -> makeSmallRange(200_000, 1000, rng));

        benchmarkShape("Nearly sorted array (n=200000)",
            () -> makeNearlySorted(200_000, rng));

        benchmarkShape("Heavy duplicates (n=200000, 8 distinct values)",
            () -> makeHeavyDuplicates(200_000, 8, rng));

        benchmarkShape("General random large array (n=200000)",
            () -> makeRandom(200_000, rng));
    }

    private static void benchmarkShape(String label, java.util.function.Supplier<int[]> generator) {
        System.out.println("-- " + label + " --");
        int[] template = generator.get();

        ArrayAnalyzer.Profile profile = ArrayAnalyzer.analyze(template);
        SortSelector.Decision decision = SortSelector.select(profile);
        System.out.println("  Selector picked: " + decision.algorithm);
        System.out.println("  Reason: " + decision.reasoning);
        System.out.println();

        // Skip O(n^2) algorithms for large n so the benchmark finishes in reasonable time,
        // unless n is small enough that they're actually relevant.
        boolean skipQuadratic = template.length > 20_000;

        for (SortAlgorithm algo : SortAlgorithm.values()) {
            if (skipQuadratic && (algo == SortAlgorithm.INSERTION_SORT
                    || algo == SortAlgorithm.SELECTION_SORT
                    || algo == SortAlgorithm.BUBBLE_SORT)) {
                System.out.printf("  %-18s SKIPPED (O(n^2), n too large)%n", algo);
                continue;
            }
            // Some algorithms only make sense / stay correct on suitable data;
            // counting/radix/bucket assume non-negative or bounded-range ints,
            // which our generators satisfy here.
            int[] copy = Arrays.copyOf(template, template.length);
            long elapsed;
            try {
                elapsed = SortMaster.sortWith(copy, algo);
            } catch (Exception e) {
                System.out.printf("  %-18s ERROR: %s%n", algo, e.getMessage());
                continue;
            }
            boolean correct = isSorted(copy);
            String marker = (algo == decision.algorithm) ? "  <-- selector's choice" : "";
            System.out.printf("  %-18s %8.3f ms   correct=%s%s%n",
                algo, elapsed / 1_000_000.0, correct, marker);
        }
        System.out.println();
    }

    // ---------------------------------------------------------------
    // Test data generators
    // ---------------------------------------------------------------
    private static int[] makeRandom(int n, Random rng) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rng.nextInt(1_000_000);
        return arr;
    }

    private static int[] makeSorted(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i;
        return arr;
    }

    private static int[] makeReverseSorted(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = n - i;
        return arr;
    }

    private static int[] makeNearlySorted(int n, Random rng) {
        int[] arr = makeSorted(n);
        // Perform a small number of random swaps to simulate "almost sorted" data.
        int swaps = Math.max(1, n / 100);
        for (int i = 0; i < swaps; i++) {
            int a = rng.nextInt(n), b = rng.nextInt(n);
            int tmp = arr[a]; arr[a] = arr[b]; arr[b] = tmp;
        }
        return arr;
    }

    private static int[] makeSmallRange(int n, int range, Random rng) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rng.nextInt(range + 1);
        return arr;
    }

    private static int[] makeUniform(int n, int range, Random rng) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rng.nextInt(range + 1);
        return arr;
    }

    private static int[] makeHeavyDuplicates(int n, int distinctValues, Random rng) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rng.nextInt(distinctValues);
        return arr;
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) return false;
        }
        return true;
    }
}
