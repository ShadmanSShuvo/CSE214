import java.util.HashSet;

/**
 * Inspects an int array and produces a profile of its characteristics.
 * This profile is what SortSelector uses to decide which algorithm to run.
 */
public class ArrayAnalyzer {

    public static class Profile {
        public int size;
        public int min;
        public int max;
        public long range;             // max - min
        public int distinctCount;      // number of distinct values (sampled/exact for small n)
        public double duplicateRatio;  // 1 - distinct/size  -> closer to 1 means many repeats
        public int runCount;           // number of maximal ascending runs (measures "sortedness")
        public double sortedness;      // 1.0 = fully sorted, 0.0 = fully reversed/random-ish
        public boolean nearlySorted;   // heuristic flag
        public boolean smallRange;     // range is small relative to size -> good for counting/radix
        public boolean uniformish;     // values look roughly uniformly spread across range

        @Override
        public String toString() {
            return String.format(
                "Profile[size=%d, range=%d, distinct=%d, dupRatio=%.2f, runs=%d, " +
                "sortedness=%.2f, nearlySorted=%s, smallRange=%s, uniformish=%s]",
                size, range, distinctCount, duplicateRatio, runCount,
                sortedness, nearlySorted, smallRange, uniformish
            );
        }
    }

    public static Profile analyze(int[] arr) {
        Profile p = new Profile();
        int n = arr.length;
        p.size = n;

        if (n == 0) {
            return p;
        }

        int min = arr[0], max = arr[0];
        for (int v : arr) {
            if (v < min) min = v;
            if (v > max) max = v;
        }
        p.min = min;
        p.max = max;
        p.range = (long) max - (long) min;

        // Distinct count: exact for reasonably sized arrays, sampled for huge ones
        // to keep analysis itself cheap (analysis should not dominate runtime).
        if (n <= 200_000) {
            HashSet<Integer> seen = new HashSet<>();
            for (int v : arr) seen.add(v);
            p.distinctCount = seen.size();
        } else {
            // Sample ~20,000 elements to estimate distinctness.
            HashSet<Integer> seen = new HashSet<>();
            int step = Math.max(1, n / 20_000);
            for (int i = 0; i < n; i += step) seen.add(arr[i]);
            double estimatedRatio = (double) seen.size() / (n / step);
            p.distinctCount = (int) Math.min(n, estimatedRatio * n);
        }
        p.duplicateRatio = 1.0 - ((double) p.distinctCount / n);

        // Count ascending runs to measure sortedness.
        int runs = 1;
        int inversionsSampled = 0;
        int comparisons = 0;
        int sampleStep = Math.max(1, n / 50_000); // cap cost on huge arrays
        for (int i = sampleStep; i < n; i += sampleStep) {
            comparisons++;
            if (arr[i] < arr[i - sampleStep]) {
                runs++;
                inversionsSampled++;
            }
        }
        p.runCount = runs;
        // sortedness: fraction of sampled adjacent pairs that were in order
        p.sortedness = comparisons == 0 ? 1.0 : 1.0 - ((double) inversionsSampled / comparisons);
        p.nearlySorted = p.sortedness >= 0.90;

        // Range considered "small" if it's within a small constant factor of n.
        // This is the classic condition where counting sort / radix sort shine.
        p.smallRange = p.range > 0 && p.range <= (long) n * 4L;

        // Rough uniformity check: bucket the values into sqrt(n) buckets and
        // see how close the bucket occupancy is to a uniform distribution.
        // Used to decide if bucket sort is a good fit (bucket sort assumes
        // roughly uniform distribution over the range to hit O(n) average case).
        if (p.range > 0) {
            int bucketCount = Math.max(1, (int) Math.sqrt(n));
            int[] buckets = new int[bucketCount];
            for (int v : arr) {
                int idx = (int) (((long) (v - min) * (bucketCount - 1)) / Math.max(1, p.range));
                buckets[Math.min(idx, bucketCount - 1)]++;
            }
            double expected = (double) n / bucketCount;
            double chiSquareLike = 0;
            for (int count : buckets) {
                double diff = count - expected;
                chiSquareLike += (diff * diff) / expected;
            }
            // Normalize: lower chiSquareLike relative to bucketCount means more uniform.
            double normalized = chiSquareLike / bucketCount;
            p.uniformish = normalized < 3.0; // empirical threshold
        } else {
            p.uniformish = false; // all same value; irrelevant for bucket sort anyway
        }

        return p;
    }
}
