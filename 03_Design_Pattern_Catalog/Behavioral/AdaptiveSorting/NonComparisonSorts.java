import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Non-comparison-based sorts. These beat the O(n log n) comparison lower bound
 * by exploiting structure in the data (bounded integer range, digit decomposition,
 * or uniform distribution), rather than comparing elements pairwise.
 */
public class NonComparisonSorts {

    // ---------- Counting Sort ----------
    // Best for: integers with a small range relative to n (range = max - min).
    // O(n + k) time and O(k) space where k = range. Stable.
    // Handles negative numbers via an offset.
    public static void countingSort(int[] arr) {
        if (arr.length == 0) return;
        int min = arr[0], max = arr[0];
        for (int v : arr) {
            if (v < min) min = v;
            if (v > max) max = v;
        }
        int range = max - min + 1;
        int[] counts = new int[range];
        for (int v : arr) counts[v - min]++;
        for (int i = 1; i < range; i++) counts[i] += counts[i - 1];

        int[] output = new int[arr.length];
        // Iterate right-to-left for stability.
        for (int i = arr.length - 1; i >= 0; i--) {
            int v = arr[i];
            output[--counts[v - min]] = v;
        }
        System.arraycopy(output, 0, arr, 0, arr.length);
    }

    // ---------- Radix Sort (LSD, base 256) ----------
    // Best for: integers, especially when range is large but bounded by a fixed
    // number of digits/bytes. O(d * (n + k)) where d = number of digit passes,
    // k = base (256 here). Handles negatives by flipping the sign bit trick.
    public static void radixSort(int[] arr) {
        if (arr.length == 0) return;

        // Convert to a representation where unsigned comparison order matches
        // signed integer order: flip the sign bit.
        int n = arr.length;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = arr[i] ^ Integer.MIN_VALUE;
        }

        int[] buffer = new int[n];
        final int BITS = 8;
        final int BUCKETS = 1 << BITS; // 256
        final int MASK = BUCKETS - 1;

        for (int shift = 0; shift < 32; shift += BITS) {
            int[] counts = new int[BUCKETS + 1];
            for (int v : a) {
                int bucket = (v >>> shift) & MASK;
                counts[bucket + 1]++;
            }
            for (int i = 0; i < BUCKETS; i++) counts[i + 1] += counts[i];
            for (int v : a) {
                int bucket = (v >>> shift) & MASK;
                buffer[counts[bucket]++] = v;
            }
            System.arraycopy(buffer, 0, a, 0, n);
        }

        for (int i = 0; i < n; i++) {
            arr[i] = a[i] ^ Integer.MIN_VALUE;
        }
    }

    // ---------- Bucket Sort ----------
    // Best for: values roughly uniformly distributed over a known range.
    // Divides range into buckets, sorts each bucket (insertion sort, since
    // buckets should be small if distribution is uniform), then concatenates.
    // O(n + k) average case, degrades to O(n^2) if all values land in one bucket
    // (which is why ArrayAnalyzer checks uniformity before recommending this).
    public static void bucketSort(int[] arr) {
        int n = arr.length;
        if (n == 0) return;

        int min = arr[0], max = arr[0];
        for (int v : arr) {
            if (v < min) min = v;
            if (v > max) max = v;
        }
        long range = (long) max - min;
        if (range == 0) return; // all identical, already sorted

        int bucketCount = Math.max(1, n);
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) buckets.add(new ArrayList<>());

        for (int v : arr) {
            int idx = (int) (((long) (v - min) * (bucketCount - 1)) / range);
            buckets.get(idx).add(v);
        }

        int pos = 0;
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket); // small buckets -> Java's sort (Timsort) is fine here
            for (int v : bucket) {
                arr[pos++] = v;
            }
        }
    }
}
