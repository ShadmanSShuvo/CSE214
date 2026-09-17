public class TrafficLightContext {
    private TrafficLightState currentState;
    private final int sleepMillisPerSecond;

    public TrafficLightContext() {
        this(1000); // Default 1000 ms = 1 second
    }

    public TrafficLightContext(int sleepMillisPerSecond) {
        this.sleepMillisPerSecond = sleepMillisPerSecond;
        this.currentState = new RedLightState(); // Default starts at RED
    }

    public void setState(TrafficLightState state) {
        this.currentState = state;
    }

    public TrafficLightState getState() {
        return currentState;
    }

    public void countdown(int seconds) {
        for (int i = seconds; i >= 1; i--) {
            System.out.printf("  [%s] %d second(s) remaining...\n", currentState.getColor(), i);
            try {
                Thread.sleep(sleepMillisPerSecond);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Timer interrupted.");
                break;
            }
        }
    }

    public void change() {
        currentState.handle(this);
    }

    public void runCycles(int totalCycles) {
        for (int cycle = 1; cycle <= totalCycles; cycle++) {
            System.out.println("==================================================");
            System.out.println(">>> STARTING TRAFFIC CYCLE #" + cycle + " <<<");
            System.out.println("==================================================");
            // Cycle consists of Red -> Yellow -> Green
            change(); // Red -> Yellow
            change(); // Yellow -> Green
            change(); // Green -> Red
            System.out.println("\n>>> CYCLE #" + cycle + " COMPLETE <<<\n");
        }
    }
}
