/**
 * Identifies each supported sorting algorithm and its Big-O characteristics.
 * The selector references these traits when reasoning about a choice.
 */
public enum SortAlgorithm {
    INSERTION_SORT   ("O(n^2)",         "O(1)",    true,  false),
    SELECTION_SORT   ("O(n^2)",         "O(1)",    false, false),
    BUBBLE_SORT      ("O(n^2)",         "O(1)",    true,  false),
    MERGE_SORT       ("O(n log n)",     "O(n)",    true,  false),
    QUICK_SORT       ("O(n log n) avg", "O(log n)",false, false),
    THREE_WAY_QUICK  ("O(n log n) avg", "O(log n)",false, false), // handles duplicates well
    HEAP_SORT        ("O(n log n)",     "O(1)",    false, false),
    COUNTING_SORT    ("O(n + k)",       "O(k)",    true,  true),
    RADIX_SORT       ("O(d*(n+k))",     "O(n + k)",true,  true),
    BUCKET_SORT      ("O(n + k) avg",   "O(n)",    true,  true);

    public final String timeComplexity;
    public final String spaceComplexity;
    public final boolean stable;
    public final boolean nonComparisonBased;

    SortAlgorithm(String time, String space, boolean stable, boolean nonComparisonBased) {
        this.timeComplexity = time;
        this.spaceComplexity = space;
        this.stable = stable;
        this.nonComparisonBased = nonComparisonBased;
    }
}
