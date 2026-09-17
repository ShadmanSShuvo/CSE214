/**
 * Strategy & Handler Interface: AIModel
 * Defines common operations, metadata, and fallback delegation for all AI models.
 */
public interface AIModel {
    /**
     * Generates an AI response for the given user prompt.
     */
    String generateResponse(String prompt);

    /**
     * Model metadata methods.
     */
    String getName();
    String getCapability();
    String getProcessingCost();
    int getLimit();
    int getCurrentUsage();
    void setUsage(int usage);

    /**
     * Checks whether the model has remaining quota.
     */
    boolean isAvailable();

    /**
     * Records a processed prompt and increments current usage.
     */
    void incrementUsage();

    /**
     * Fallback chain accessors (Chain of Responsibility integration).
     */
    AIModel getFallbackModel();
    void setFallbackModel(AIModel fallbackModel);

    /**
     * Formats usage status string (e.g. "8 / 10" or "15 / Unlimited").
     */
    String getUsageStatus();
}
