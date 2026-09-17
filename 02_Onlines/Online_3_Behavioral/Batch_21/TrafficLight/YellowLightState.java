public class YellowLightState implements TrafficLightState {
    private static final int DURATION = 2;

    @Override
    public String getColor() {
        return "YELLOW";
    }

    @Override
    public int getDurationSeconds() {
        return DURATION;
    }

    @Override
    public void handle(TrafficLightContext context) {
        System.out.println("\n[LIGHT: YELLOW] CAUTION! Prepare to go/stop. Waiting for " + DURATION + " seconds...");
        context.countdown(DURATION);
        System.out.println("[State Transition] Yellow duration ended. Switching to GREEN.");
        context.setState(new GreenLightState());
    }
}
