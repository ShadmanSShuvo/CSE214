public class CommonTier implements SubscriptionTier {
    @Override
    public String getName() {
        return "Common";
    }

    @Override
    public int getSafeRangeKm() {
        return 10;
    }

    @Override
    public void promote(PatientBrainSimulator simulator) {
        System.out.println("[Tier Promotion] Promoted subscription from Common to Plus.");
        simulator.setTier(new PlusTier());
    }

    @Override
    public void demote(PatientBrainSimulator simulator) {
        System.out.println("[Tier Demotion] Already at lowest tier (Common). No change.");
    }

    @Override
    public void setMood(String mood, PatientBrainSimulator simulator) {
        System.out.println("Mood control unavailable.");
    }
}
