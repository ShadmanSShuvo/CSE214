/**
 * Concrete Model Strategy: CoreMind
 * Balanced model designed for moderately complex requests.
 * Capability: Medium
 * Processing Cost: Medium
 * Prompt Limit: 50 prompts
 */
public class CoreMind extends AbstractAIModel {
    public static final int PROMPT_LIMIT = 50;

    public CoreMind() {
        super("CoreMind", "Medium", "Medium", PROMPT_LIMIT);
    }

    @Override
    public String generateResponse(String prompt) {
        return "[CoreMind Response] Balanced contextual analysis generated for: \"" + prompt + "\"";
    }
}
