/**
 * Concrete Model Strategy: FlashMind
 * Fast model designed for simple requests.
 * Capability: Basic
 * Processing Cost: Low
 * Prompt Limit: Unlimited
 */
public class FlashMind extends AbstractAIModel {

    public FlashMind() {
        super("FlashMind", "Basic", "Low", UNLIMITED);
    }

    @Override
    public String generateResponse(String prompt) {
        return "[FlashMind Response] Fast direct answer generated for: \"" + prompt + "\"";
    }
}
