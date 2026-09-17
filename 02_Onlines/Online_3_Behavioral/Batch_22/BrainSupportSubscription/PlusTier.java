public class PlusTier implements SubscriptionTier {
    @Override
    public String getName() {
        return "Plus";
    }

    @Override
    public int getSafeRangeKm() {
        return 50;
    }

    @Override
    public void promote(PatientBrainSimulator simulator) {
        System.out.println("[Tier Promotion] Promoted subscription from Plus to Lux.");
        simulator.setTier(new LuxTier());
    }

    @Override
    public void demote(PatientBrainSimulator simulator) {
        System.out.println("[Tier Demotion] Demoted subscription from Plus to Common.");
        simulator.setTier(new CommonTier());
    }

    @Override
    public void setMood(String mood, PatientBrainSimulator simulator) {
        System.out.println("Mood control unavailable.");
    }
}
