public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("  BRAIN-SUPPORT SUBSCRIPTION SIMULATOR (STATE PATTERN)    ");
        System.out.println("==========================================================");

        // Initialize patient with Common Tier
        PatientBrainSimulator patient = new PatientBrainSimulator("Subject-7", new CommonTier());

        // 1. Initial distance checks under Common (0-10 km)
        System.out.println("\n--- Step 1: Distance Checks under Common Tier ---");
        patient.travelCheck(5); // STABLE
        patient.travelCheck(15); // Exceeds 10km -> UNSTABLE, blackout alert
        patient.travelCheck(0); // Back in range -> STABLE, regains consciousness

        // 2. Mood check under Common (should fail)
        System.out.println("\n--- Step 2: Set Mood under Common Tier ---");
        patient.setMood("calm"); // Should print "Mood control unavailable."

        // 3. Promotion to Plus
        System.out.println("\n--- Step 3: Promotion from Common to Plus ---");
        patient.promote();
        patient.travelCheck(35); // STABLE under Plus (0-50 km)
        patient.setMood("happy"); // Should still print "Mood control unavailable."

        // 4. Temporary Lux Activation and return to previous tier
        System.out.println("\n--- Step 4: Temporary Lux Activation ---");
        patient.activateLux(2); // Activates Lux temporarily and returns to Plus
        System.out.println("Current tier after temporary Lux expiration: " + patient.getTier().getName());

        // 5. Promotion to Lux
        System.out.println("\n--- Step 5: Promotion to Permanent Lux Tier ---");
        patient.promote(); // Plus -> Lux
        System.out.println("Current tier: " + patient.getTier().getName());
        patient.setMood("happy"); // Works under Lux!
        patient.setMood("calm"); // Works under Lux!
        patient.promote(); // Lux -> Lux (no change)

        // 6. Demotion sequence
        System.out.println("\n--- Step 6: Demotion Sequence ---");
        patient.demote(); // Lux -> Plus
        patient.setMood("exhausted"); // Mood control unavailable
        patient.demote(); // Plus -> Common
        patient.demote(); // Common -> Common (no change)

        System.out.println("\n==========================================================");
        System.out.println("  Simulation completed successfully.                      ");
        System.out.println("==========================================================");
    }
}
