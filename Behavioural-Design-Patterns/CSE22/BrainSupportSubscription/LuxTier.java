public class LuxTier implements SubscriptionTier {
    @Override
    public String getName() {
        return "Lux";
    }

    @Override
    public int getSafeRangeKm() {
        return 50;
    }

    @Override
    public void promote(PatientBrainSimulator simulator) {
        System.out.println("[Tier Promotion] Already at highest tier (Lux). No change.");
    }

    @Override
    public void demote(PatientBrainSimulator simulator) {
        System.out.println("[Tier Demotion] Demoted subscription from Lux to Plus.");
        simulator.setTier(new PlusTier());
    }

    @Override
    public void setMood(String mood, PatientBrainSimulator simulator) {
        if ("calm".equalsIgnoreCase(mood) || "exhausted".equalsIgnoreCase(mood) || "happy".equalsIgnoreCase(mood)) {
            System.out.println("[Mood Control Active] Patient mood successfully calibrated to: " + mood.toLowerCase());
        } else {
            System.out.println("[Mood Control Active] Setting custom mood: " + mood);
        }
    }
}
