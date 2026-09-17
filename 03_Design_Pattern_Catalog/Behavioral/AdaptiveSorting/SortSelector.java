/**
 * The decision engine. Looks at an ArrayAnalyzer.Profile and chooses the
 * algorithm expected to perform best, with a human-readable justification.
 *
 * Decision order (roughly, most specific/cheap win conditions first):
 *   1. Trivial cases (n <= 1)
 *   2. Very small arrays          -> insertion sort (low constant factor wins)
 *   3. Nearly sorted arrays       -> insertion sort (adaptive, near O(n))
 *   4. Small integer range        -> counting sort (O(n+k), k small)
 *   5. Larger but bounded range,
 *      uniform distribution       -> bucket sort (O(n) average)
 *   6. Large range, not uniform,
 *      but bounded digit width    -> radix sort (still beats n log n often)
 *   7. Heavy duplicates           -> 3-way quicksort (avoids degenerate partitioning)
 *   8. Large n, general case      -> merge sort if stability matters or
 *                                    worst-case guarantee wanted; else quicksort
 *   9. Memory-constrained large n -> heap sort (O(1) extra space, guaranteed n log n)
 *
 * These thresholds are tuned heuristics, not laws of physics -- real-world
 * constant factors (cache behavior, JIT warmup, GC pressure) mean the
 * "best" choice can shift. The Benchmark class exists to empirically verify.
 */
public class SortSelector {

    public enum Priority {
        SPEED,       // pick whatever is empirically/theoretically fastest
        STABILITY,   // must preserve relative order of equal elements
        LOW_MEMORY   // prefer O(1)/O(log n) extra space
    }

    public static class Decision {
        public final SortAlgorithm algorithm;
        public final String reasoning;

        Decision(SortAlgorithm algorithm, String reasoning) {
            this.algorithm = algorithm;
            this.reasoning = reasoning;
        }

        @Override
        public String toString() {
            return algorithm + " -- " + reasoning;
        }
    }

    public static Decision select(ArrayAnalyzer.Profile p) {
        return select(p, Priority.SPEED);
    }

    public static Decision select(ArrayAnalyzer.Profile p, Priority priority) {
        int n = p.size;

        if (n <= 1) {
            return new Decision(SortAlgorithm.INSERTION_SORT,
                "Array has " + n + " element(s); already sorted by definition.");
        }

        // Small arrays: constant factors dominate, O(n^2) insertion sort beats
        // any O(n log n) algorithm's overhead (recursion, allocation, pivoting).
        if (n < 48) {
            return new Decision(SortAlgorithm.INSERTION_SORT,
                "n=" + n + " is small enough that insertion sort's low overhead " +
                "beats the setup cost of any O(n log n) algorithm.");
        }

        // Nearly sorted data: insertion sort is adaptive and runs close to O(n)
        // when the number of inversions is low, beating merge/quicksort's
        // guaranteed-but-unnecessary O(n log n) work.
        if (p.nearlySorted && n < 10_000) {
            return new Decision(SortAlgorithm.INSERTION_SORT,
                String.format("Array is %.0f%% pre-sorted (few inversions detected); " +
                "insertion sort is adaptive and approaches O(n) here.", p.sortedness * 100));
        }

        if (priority == Priority.LOW_MEMORY && n >= 10_000) {
            return new Decision(SortAlgorithm.HEAP_SORT,
                "Low-memory priority requested on a large array; heap sort gives " +
                "guaranteed O(n log n) with O(1) extra space, unlike merge sort's O(n) " +
                "buffer or quicksort's O(log n) stack plus worst-case O(n^2) risk.");
        }

        // Small integer range relative to n: counting sort is O(n + k) and will
        // crush any comparison sort when k is on the order of n.
        if (p.smallRange) {
            return new Decision(SortAlgorithm.COUNTING_SORT,
                String.format("Value range (%d) is small relative to n (%d); " +
                "counting sort runs in O(n + range), avoiding comparisons entirely.",
                p.range, n));
        }

        // Larger bounded range with roughly uniform distribution: bucket sort's
        // average case is O(n) here because each bucket gets ~n/bucketCount items.
        if (p.uniformish && p.range > 0 && n >= 1000) {
            return new Decision(SortAlgorithm.BUCKET_SORT,
                "Values are roughly uniformly distributed across their range; " +
                "bucket sort achieves near O(n) average case by spreading elements " +
                "evenly across buckets, each cheaply sorted.");
        }

        // Large range but still bounded (fits in fixed-width integer keys):
        // radix sort processes digits independent of value magnitude, so it can
        // beat O(n log n) comparison sorts for large n even when range is huge.
        if (n >= 50_000 && !p.uniformish) {
            return new Decision(SortAlgorithm.RADIX_SORT,
                "Large n with a wide, non-uniform value range; radix sort's " +
                "O(d*(n+k)) with fixed digit-width d=4 (32-bit ints, byte-wise passes) " +
                "beats O(n log n) comparison sorts at this scale.");
        }

        // Heavy duplicates: 3-way quicksort partitions equal elements out of
        // future recursion entirely, avoiding the O(n^2) degenerate case that
        // plain quicksort hits when many keys are equal.
        if (p.duplicateRatio > 0.30) {
            return new Decision(SortAlgorithm.THREE_WAY_QUICK,
                String.format("High duplicate ratio (%.0f%% repeated values); " +
                "3-way quicksort partitions equal keys out permanently, avoiding " +
                "the O(n^2) degradation plain quicksort suffers with many duplicates.",
                p.duplicateRatio * 100));
        }

        if (priority == Priority.STABILITY) {
            return new Decision(SortAlgorithm.MERGE_SORT,
                "Stability required; merge sort guarantees O(n log n) while " +
                "preserving relative order of equal elements.");
        }

        // Default general-purpose case: randomized quicksort. Best average-case
        // constant factor, in-place, cache-friendly. This is what most standard
        // library sorts default to for primitives (with insertion-sort cutoff,
        // which our implementation includes).
        return new Decision(SortAlgorithm.QUICK_SORT,
            "General-purpose case: no small range, no strong uniformity, " +
            "no heavy duplication, no stability/memory constraint requested. " +
            "Randomized quicksort offers the best expected constant factor.");
    }

    /** Executes the chosen algorithm in-place on the given array. */
    public static void execute(int[] arr, SortAlgorithm algo) {
        switch (algo) {
            case INSERTION_SORT  -> ComparisonSorts.insertionSort(arr);
            case SELECTION_SORT  -> ComparisonSorts.selectionSort(arr);
            case BUBBLE_SORT     -> ComparisonSorts.bubbleSort(arr);
            case MERGE_SORT      -> ComparisonSorts.mergeSort(arr);
            case QUICK_SORT      -> ComparisonSorts.quickSort(arr);
            case THREE_WAY_QUICK -> ComparisonSorts.threeWayQuickSort(arr);
            case HEAP_SORT       -> ComparisonSorts.heapSort(arr);
            case COUNTING_SORT   -> NonComparisonSorts.countingSort(arr);
            case RADIX_SORT      -> NonComparisonSorts.radixSort(arr);
            case BUCKET_SORT     -> NonComparisonSorts.bucketSort(arr);
        }
    }
}
