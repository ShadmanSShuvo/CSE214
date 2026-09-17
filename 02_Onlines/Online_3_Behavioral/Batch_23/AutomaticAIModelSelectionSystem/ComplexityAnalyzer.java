import java.util.Random;

/**
 * Component: ComplexityAnalyzer
 * Evaluates the complexity of a user prompt and produces a score in range [0, 100].
 * As defined in the specification, defaults to returning a random score between 0 and 100,
 * but also supports deterministic scoring for automated testing and specific scenarios.
 */
public class ComplexityAnalyzer {
    private final Random random;

    public ComplexityAnalyzer() {
        this.random = new Random();
    }

    public ComplexityAnalyzer(long seed) {
        this.random = new Random(seed);
    }

    /**
     * Standard operation specified in problem statement:
     * Accepts a prompt and returns a random complexity score between 0 and 100.
     */
    public int analyze(String prompt) {
        return random.nextInt(101); // 0 to 100 inclusive
    }
}
