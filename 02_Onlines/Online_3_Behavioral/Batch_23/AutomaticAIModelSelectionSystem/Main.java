/**
 * Test Driver: Automatic AI Model Selection System
 * Exercises the exact scenarios from the problem specification and edge case fallbacks.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("       AUTOMATIC AI MODEL SELECTION SYSTEM (STRATEGY + CHAIN)     ");
        System.out.println("==================================================================\n");

        AIPlatform platform = new AIPlatform();

        // -------------------------------------------------------------
        // Scenario 1 (From Specification)
        // Complexity Score = 84
        // DeepMindX Usage = 7 / 10
        // CoreMind Usage = 24 / 50
        // -------------------------------------------------------------
        System.out.println("================== EXAMPLE SCENARIO 1 ==================");
        platform.getDeepMindX().setUsage(7);
        platform.getCoreMind().setUsage(24);
        platform.printUsageSummary();

        platform.processPrompt("Synthesize a unified mathematical proof for Riemannian topology.", 84);

        // -------------------------------------------------------------
        // Scenario 2 (From Specification)
        // Complexity Score = 90
        // DeepMindX Usage = 10 / 10 (Limit Reached)
        // CoreMind Usage = 37 / 50
        // -------------------------------------------------------------
        System.out.println("================== EXAMPLE SCENARIO 2 ==================");
        platform.getDeepMindX().setUsage(10);
        platform.getCoreMind().setUsage(37);
        platform.printUsageSummary();

        platform.processPrompt("Design a distributed multi-datacenter consensus algorithm.", 90);

        // -------------------------------------------------------------
        // Scenario 3 (From Specification)
        // Complexity Score = 22
        // DeepMindX Usage = 10 / 10 (or any)
        // Simple prompt: must NOT select a more powerful model unnecessarily
        // -------------------------------------------------------------
        System.out.println("================== EXAMPLE SCENARIO 3 ==================");
        platform.printUsageSummary();

        platform.processPrompt("What is the capital of France?", 22);

        // -------------------------------------------------------------
        // Scenario 4: Double Fallback (DeepMindX & CoreMind both exhausted)
        // Complexity Score = 95
        // DeepMindX = 10 / 10, CoreMind = 50 / 50
        // Expected: DeepMindX unavailable -> CoreMind unavailable -> FlashMind
        // -------------------------------------------------------------
        System.out.println("============== SCENARIO 4: DOUBLE FALLBACK =============");
        platform.getDeepMindX().setUsage(10);
        platform.getCoreMind().setUsage(50);
        platform.printUsageSummary();

        platform.processPrompt("Solve advanced quantum chromodynamics lattice simulations.", 95);

        // -------------------------------------------------------------
        // Scenario 5: Moderately Complex Prompt Fallback
        // Complexity Score = 55 (Requires CoreMind)
        // CoreMind = 50 / 50 (Exhausted)
        // Expected: CoreMind unavailable -> FlashMind
        // -------------------------------------------------------------
        System.out.println("======== SCENARIO 5: MODERATE COMPLEXITY FALLBACK =======");
        platform.getCoreMind().setUsage(50);
        platform.printUsageSummary();

        platform.processPrompt("Draft a quarterly project roadmap and sprint schedule.", 55);

        // -------------------------------------------------------------
        // Scenario 6: Natural Prompt Submission (Automatic Random Complexity)
        // -------------------------------------------------------------
        System.out.println("==== SCENARIO 6: NATURAL PROMPT WITH RANDOM ANALYZER ===");
        platform.processPrompt("Explain gradient descent optimization in machine learning.");

        System.out.println("==================================================================");
        System.out.println("         ALL AI MODEL SELECTION SCENARIOS COMPLETED SUCCESSFULLY  ");
        System.out.println("==================================================================");
    }
}
