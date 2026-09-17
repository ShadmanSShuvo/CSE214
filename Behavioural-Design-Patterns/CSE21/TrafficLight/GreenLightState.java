public class GreenLightState implements TrafficLightState {
    private static final int DURATION = 10;

    @Override
    public String getColor() {
        return "GREEN";
    }

    @Override
    public int getDurationSeconds() {
        return DURATION;
    }

    @Override
    public void handle(TrafficLightContext context) {
        System.out.println("\n[LIGHT: GREEN] GO! Traffic moving. Waiting for " + DURATION + " seconds...");
        context.countdown(DURATION);
        System.out.println("[State Transition] Green duration ended. Switching back to RED.");
        context.setState(new RedLightState());
    }
}
