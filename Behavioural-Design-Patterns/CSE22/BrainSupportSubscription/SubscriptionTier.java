public interface SubscriptionTier {
    String getName();
    int getSafeRangeKm();
    void promote(PatientBrainSimulator simulator);
    void demote(PatientBrainSimulator simulator);
    void setMood(String mood, PatientBrainSimulator simulator);
}
