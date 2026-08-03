import java.util.Random;

/**
 * Classic comparison-based sorting algorithms, all operating in-place on int[]
 * (except merge sort, which needs auxiliary space by nature).
 */
public class ComparisonSorts {

    private static final Random RNG = new Random();

    // ---------- Insertion Sort ----------
    // Best for: small n, or nearly-sorted data (adaptive: close to O(n) when nearly sorted)
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // ---------- Selection Sort ----------
    // Rarely optimal, included for completeness. Useful when swap cost is very high
    // and you want to minimize the number of swaps (does at most n swaps).
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) minIdx = j;
            }
            if (minIdx != i) {
                int tmp = arr[i];
                arr[i] = arr[minIdx];
                arr[minIdx] = tmp;
            }
        }
    }

    // ---------- Bubble Sort ----------
    // Included for completeness / educational baseline. Adaptive with early exit.
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (arr[i - 1] > arr[i]) {
                    int tmp = arr[i - 1];
                    arr[i - 1] = arr[i];
                    arr[i] = tmp;
                    swapped = true;
                }
            }
            n--; // last element is now in place
        } while (swapped);
    }

    // ---------- Merge Sort ----------
    // Best for: guaranteed O(n log n), stability required, linked-list-like access patterns,
    // or large arrays where worst-case guarantees matter more than average-case speed.
    public static void mergeSort(int[] arr) {
        if (arr.length < 2) return;
        int[] buffer = new int[arr.length];
        mergeSortHelper(arr, buffer, 0, arr.length - 1);
    }

    private static void mergeSortHelper(int[] arr, int[] buffer, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        mergeSortHelper(arr, buffer, lo, mid);
        mergeSortHelper(arr, buffer, mid + 1, hi);
        merge(arr, buffer, lo, mid, hi);
    }

    private static void merge(int[] arr, int[] buffer, int lo, int mid, int hi) {
        System.arraycopy(arr, lo, buffer, lo, hi - lo + 1);
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            arr[k++] = (buffer[i] <= buffer[j]) ? buffer[i++] : buffer[j++];
        }
        while (i <= mid) arr[k++] = buffer[i++];
        while (j <= hi) arr[k++] = buffer[j++];
    }

    // ---------- Quick Sort (randomized pivot, standard 2-way partition) ----------
    // Best for: general-purpose, in-place, good cache behavior, low overhead.
    // Weak point: many duplicate keys degrade partitioning quality (use 3-way variant instead).
    public static void quickSort(int[] arr) {
        quickSortHelper(arr, 0, arr.length - 1);
    }

    private static void quickSortHelper(int[] arr, int lo, int hi) {
        while (lo < hi) {
            // Insertion sort cutoff for small subarrays -- classic optimization
            if (hi - lo < 16) {
                insertionSortRange(arr, lo, hi);
                return;
            }
            int p = partition(arr, lo, hi);
            // Recurse into smaller side, loop on larger side (bounds stack depth to O(log n))
            if (p - lo < hi - p) {
                quickSortHelper(arr, lo, p - 1);
                lo = p + 1;
            } else {
                quickSortHelper(arr, p + 1, hi);
                hi = p - 1;
            }
        }
    }

    private static int partition(int[] arr, int lo, int hi) {
        int pivotIdx = lo + RNG.nextInt(hi - lo + 1);
        swap(arr, pivotIdx, hi);
        int pivot = arr[hi];
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, hi);
        return i + 1;
    }

    private static void insertionSortRange(int[] arr, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= lo && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // ---------- 3-Way (Dutch National Flag) Quick Sort ----------
    // Best for: arrays with many duplicate keys. Partitions into <, ==, > pivot
    // so equal elements are never re-examined, avoiding O(n^2) degenerate behavior.
    public static void threeWayQuickSort(int[] arr) {
        threeWayHelper(arr, 0, arr.length - 1);
    }

    private static void threeWayHelper(int[] arr, int lo, int hi) {
        if (hi <= lo) return;
        if (hi - lo < 16) {
            insertionSortRange(arr, lo, hi);
            return;
        }
        int pivotIdx = lo + RNG.nextInt(hi - lo + 1);
        int pivot = arr[pivotIdx];

        int lt = lo, i = lo, gt = hi;
        while (i <= gt) {
            if (arr[i] < pivot) {
                swap(arr, lt++, i++);
            } else if (arr[i] > pivot) {
                swap(arr, i, gt--);
            } else {
                i++;
            }
        }
        threeWayHelper(arr, lo, lt - 1);
        threeWayHelper(arr, gt + 1, hi);
    }

    // ---------- Heap Sort ----------
    // Best for: guaranteed O(n log n) with O(1) extra space (no recursion stack issues
    // like quicksort's worst case, no auxiliary array like merge sort).
    // Good when memory is tight and worst-case guarantees matter.
    public static void heapSort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(arr, i, n);
        }
        for (int end = n - 1; end > 0; end--) {
            swap(arr, 0, end);
            siftDown(arr, 0, end);
        }
    }

    private static void siftDown(int[] arr, int root, int size) {
        while (true) {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            int largest = root;
            if (left < size && arr[left] > arr[largest]) largest = left;
            if (right < size && arr[right] > arr[largest]) largest = right;
            if (largest == root) break;
            swap(arr, root, largest);
            root = largest;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
