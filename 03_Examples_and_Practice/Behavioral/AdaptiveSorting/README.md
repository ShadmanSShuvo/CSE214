# SortMaster

A master sorting program: analyzes an int[] array's properties (size, value
range, duplicate ratio, sortedness, distribution uniformity) and automatically
picks the best-suited sorting algorithm, rather than always using one fixed
algorithm.

## Build & run

```bash
cd src
javac *.java
java Main
```

## Use in your own code

```java
int[] data = {5, 3, 8, 1, 9, 2};
SortMaster.sort(data);                 // auto-selects + sorts in place

// Or get details on what was chosen and why:
SortMaster.Result result = SortMaster.sortVerbose(data);
System.out.println(result);

// Bias selection toward a priority:
SortMaster.sort(data, SortSelector.Priority.STABILITY);
SortMaster.sort(data, SortSelector.Priority.LOW_MEMORY);
```

## Files

- `ArrayAnalyzer.java`    — computes a Profile (size, range, duplicates, sortedness, uniformity)
- `SortAlgorithm.java`    — enum of supported algorithms + complexity metadata
- `ComparisonSorts.java`  — insertion, selection, bubble, merge, quicksort, 3-way quicksort, heapsort
- `NonComparisonSorts.java` — counting sort, radix sort (LSD base-256), bucket sort
- `SortSelector.java`     — the decision engine (profile -> algorithm + reasoning)
- `SortMaster.java`       — public API you actually call
- `Main.java`             — correctness tests + selector demo + benchmark harness

## Selection logic (in order of precedence)

1. n <= 1 -> trivial
2. n < 48 -> **insertion sort** (constant factors dominate at small n)
3. Nearly sorted (>=90% of sampled adjacent pairs in order) and n < 10,000 -> **insertion sort** (adaptive, ~O(n))
4. LOW_MEMORY priority + large n -> **heap sort** (O(1) extra space, guaranteed O(n log n))
5. Value range small relative to n (range <= 4n) -> **counting sort** (O(n + range))
6. Range large but values roughly uniform (chi-square-like bucket occupancy check) -> **bucket sort** (~O(n) average)
7. Large n (>=50,000), wide non-uniform range -> **radix sort** (O(d*(n+k)), byte-wise passes)
8. Duplicate ratio > 30% -> **3-way (Dutch flag) quicksort** (avoids O(n^2) degeneracy from repeated keys)
9. STABILITY priority -> **merge sort**
10. Otherwise -> **randomized quicksort** (best general-purpose constant factor)

Note: I couldn't compile/run this in the sandbox (no `javac` available and the
JDK package fetch failed via apt), so I verified the decision logic by porting
the analyzer/selector heuristics to Python and running them against the same
test cases used in `Main.java` — the choices matched expectations for every
scenario (tiny array, nearly-sorted, small-range, uniform-wide-range, heavy
duplicates, general random). Please compile locally with `javac *.java` and
run `Main` to also confirm actual sort correctness on your machine; the sort
implementations themselves follow standard, well-tested algorithm structure
(randomized quicksort with insertion-sort cutoff, LSD radix, Dutch-flag 3-way
partition, etc.), but I want to flag that I haven't executed the Java bytecode
directly.
