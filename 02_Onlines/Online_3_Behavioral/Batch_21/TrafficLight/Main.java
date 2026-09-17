public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     TRAFFIC LIGHT CONTROLLER (STATE PATTERN)     ");
        System.out.println("==================================================");

        // We run 1 full cycle with an accelerated simulation tick (50ms per second)
        // for fast, deterministic demo output while preserving exact timing logic.
        System.out.println("Initializing Traffic Light System...");
        System.out.println("Rule: RED (5s) -> YELLOW (2s) -> GREEN (10s) -> RED (repeat)\n");

        TrafficLightContext controller = new TrafficLightContext(50); // 50ms per second for fast demo
        controller.runCycles(1);

        System.out.println("Traffic light controller simulation completed successfully.");
    }
}
