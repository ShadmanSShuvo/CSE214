public class PatientBrainSimulator {
    private final String patientName;
    private SubscriptionTier currentTier;
    private boolean isConscious;

    public PatientBrainSimulator(String patientName, SubscriptionTier initialTier) {
        this.patientName = patientName;
        this.currentTier = initialTier;
        this.isConscious = true;
    }

    public String getPatientName() {
        return patientName;
    }

    public SubscriptionTier getTier() {
        return currentTier;
    }

    public void setTier(SubscriptionTier tier) {
        this.currentTier = tier;
    }

    public boolean isConscious() {
        return isConscious;
    }

    public void travelCheck(int km) {
        int safeRange = currentTier.getSafeRangeKm();
        System.out.printf("[TravelCheck] Distance: %d km | Tier: %s (Safe Range: 0-%d km)\n", km, currentTier.getName(),
                safeRange);

        if (km <= safeRange) {
            if (!isConscious) {
                isConscious = true;
                System.out.println("Status: STABLE. Patient has returned to coverage and regains consciousness.");
            } else {
                System.out.println("Status: STABLE. Brain-support signal is optimal.");
            }
        } else {
            isConscious = false;
            System.out.println("Status: UNSTABLE. Patient distance exceeds safe coverage radius!");
            System.out.println(
                    ">>> ALERT: Patient has blacked out! Please bring the patient back into coverage immediately! <<<");
        }
    }

    public void activateLux(int hours) {
        activateLux(hours, 500); // 500ms simulation time
    }

    public void activateLux(int hours, int simulationMillis) {
        SubscriptionTier previousTier = this.currentTier;
        System.out.println("\n>>> [Temporary Activation] Activating Lux Tier for " + hours + " hour(s) <<<");
        System.out.println("Previous Tier saved: " + previousTier.getName());
        this.currentTier = new LuxTier();

        try {
            System.out.println("... Lux subscription active during period ...");
            Thread.sleep(simulationMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(
                ">>> [Temporary Activation Expired] Returning to previous tier: " + previousTier.getName() + " <<<\n");
        this.currentTier = previousTier;
    }

    public void setMood(String mood) {
        currentTier.setMood(mood, this);
    }

    public void promote() {
        currentTier.promote(this);
    }

    public void demote() {
        currentTier.demote(this);
    }
}
