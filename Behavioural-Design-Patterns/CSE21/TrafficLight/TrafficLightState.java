public interface TrafficLightState {
    String getColor();
    int getDurationSeconds();
    void handle(TrafficLightContext context);
}
