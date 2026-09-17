/**
 * Context & Routing Dispatcher: AIPlatform
 * Coordinates prompt complexity analysis, model selection, fallback resolution,
 * and tracks execution usage limits across models.
 */
public class AIPlatform {
    private final AIModel flashMind;
    private final AIModel coreMind;
    private final AIModel deepMindX;
    private final ComplexityAnalyzer analyzer;

    public AIPlatform() {
        this(new ComplexityAnalyzer());
    }

    public AIPlatform(ComplexityAnalyzer analyzer) {
        this.flashMind = new FlashMind();
        this.coreMind = new CoreMind();
        this.deepMindX = new DeepMindX();
        this.analyzer = analyzer;

        // Configure the fallback chain: DeepMindX -> CoreMind -> FlashMind
        this.deepMindX.setFallbackModel(this.coreMind);
        this.coreMind.setFallbackModel(this.flashMind);
    }

    /**
     * Maps complexity score [0, 100] to the strictly required AI model.
     * Complexity 0 - 30   -> FlashMind
     * Complexity 31 - 70  -> CoreMind
     * Complexity 71 - 100 -> DeepMindX
     */
    public AIModel determineRequiredModel(int complexityScore) {
        if (complexityScore < 0 || complexityScore > 100) {
            throw new IllegalArgumentException("Complexity score must be between 0 and 100. Received: " + complexityScore);
        }

        if (complexityScore <= 30) {
            return flashMind;
        } else if (complexityScore <= 70) {
            return coreMind;
        } else {
            return deepMindX;
        }
    }

    /**
     * Resolves the effective model using the fallback chain if the required model
     * has exhausted its usage limit.
     */
    public AIModel resolveModelWithFallback(AIModel requiredModel) {
        AIModel candidate = requiredModel;

        while (candidate != null) {
            if (candidate.isAvailable()) {
                return candidate;
            }
            System.out.println(candidate.getName() + " unavailable due to usage limit.");
            candidate = candidate.getFallbackModel();
        }

        // Fallback chain guaranteed to terminate at FlashMind (which has unlimited capacity)
        return flashMind;
    }

    /**
     * Processes a user prompt using automatic random complexity analysis.
     */
    public String processPrompt(String prompt) {
        int score = analyzer.analyze(prompt);
        return processPrompt(prompt, score);
    }

    /**
     * Processes a user prompt with an explicit complexity score (used for deterministic test scenarios).
     */
    public String processPrompt(String prompt, int complexityScore) {
        System.out.println("Prompt: \"" + prompt + "\"");
        System.out.println("Complexity Score = " + complexityScore);

        AIModel requiredModel = determineRequiredModel(complexityScore);
        System.out.println("Required Model = " + requiredModel.getName());

        AIModel selectedModel = resolveModelWithFallback(requiredModel);
        System.out.println("Selected Model = " + selectedModel.getName());

        // Process prompt via selected model strategy
        String response = selectedModel.generateResponse(prompt);
        selectedModel.incrementUsage();

        System.out.println("Response: " + response);
        System.out.println("After processing the prompt:");
        System.out.println("  " + selectedModel.getName() + " Usage = " + selectedModel.getUsageStatus());
        System.out.println();

        return response;
    }

    // Accessors for configuring or inspecting model usage
    public AIModel getFlashMind() {
        return flashMind;
    }

    public AIModel getCoreMind() {
        return coreMind;
    }

    public AIModel getDeepMindX() {
        return deepMindX;
    }

    public void printUsageSummary() {
        System.out.println("----- Model Usage Status -----");
        System.out.println("  " + flashMind.getName() + " Usage = " + flashMind.getUsageStatus());
        System.out.println("  " + coreMind.getName() + " Usage = " + coreMind.getUsageStatus());
        System.out.println("  " + deepMindX.getName() + " Usage = " + deepMindX.getUsageStatus());
        System.out.println("------------------------------\n");
    }
}
