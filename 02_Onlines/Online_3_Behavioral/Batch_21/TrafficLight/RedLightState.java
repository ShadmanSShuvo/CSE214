public class RedLightState implements TrafficLightState {
    private static final int DURATION = 5;

    @Override
    public String getColor() {
        return "RED";
    }

    @Override
    public int getDurationSeconds() {
        return DURATION;
    }

    @Override
    public void handle(TrafficLightContext context) {
        System.out.println("\n[LIGHT: RED] STOP! Waiting for " + DURATION + " seconds...");
        context.countdown(DURATION);
        System.out.println("[State Transition] Red duration ended. Switching to YELLOW.");
        context.setState(new YellowLightState());
    }
}
