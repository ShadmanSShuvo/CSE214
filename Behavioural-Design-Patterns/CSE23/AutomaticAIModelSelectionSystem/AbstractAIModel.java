/**
 * Abstract Base Class: AbstractAIModel
 * Provides common state management (name, limits, usage, fallback reference)
 * for all concrete AI model strategies.
 */
public abstract class AbstractAIModel implements AIModel {
    public static final int UNLIMITED = -1;

    protected final String name;
    protected final String capability;
    protected final String processingCost;
    protected final int limit;
    protected int currentUsage;
    protected AIModel fallbackModel;

    public AbstractAIModel(String name, String capability, String processingCost, int limit) {
        this.name = name;
        this.capability = capability;
        this.processingCost = processingCost;
        this.limit = limit;
        this.currentUsage = 0;
        this.fallbackModel = null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCapability() {
        return capability;
    }

    @Override
    public String getProcessingCost() {
        return processingCost;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public int getCurrentUsage() {
        return currentUsage;
    }

    @Override
    public void setUsage(int usage) {
        this.currentUsage = Math.max(0, usage);
    }

    @Override
    public boolean isAvailable() {
        if (limit == UNLIMITED) {
            return true;
        }
        return currentUsage < limit;
    }

    @Override
    public void incrementUsage() {
        this.currentUsage++;
    }

    @Override
    public AIModel getFallbackModel() {
        return fallbackModel;
    }

    @Override
    public void setFallbackModel(AIModel fallbackModel) {
        this.fallbackModel = fallbackModel;
    }

    @Override
    public String getUsageStatus() {
        if (limit == UNLIMITED) {
            return currentUsage + " / Unlimited";
        }
        return currentUsage + " / " + limit;
    }

    @Override
    public String toString() {
        return name + " [Capability: " + capability + ", Cost: " + processingCost + ", Usage: " + getUsageStatus() + "]";
    }
}
