// State interface
interface TrafficLightState {
    void handle(TrafficLight context);

    String getColor();
}

// Concrete State - Red
class RedState implements TrafficLightState {

    @Override
    public void handle(TrafficLight context) {
        System.out.println("RED - Stop!");
        context.setState(new GreenState()); // decides next state
    }

    @Override
    public String getColor() {
        return "RED";
    }
}

// Concrete State - Green
class GreenState implements TrafficLightState {

    @Override
    public void handle(TrafficLight context) {
        System.out.println("GREEN - Go!");
        context.setState(new YellowState());
    }

    @Override
    public String getColor() {
        return "GREEN";
    }
}

// Concrete State - Yellow
class YellowState implements TrafficLightState {

    @Override
    public void handle(TrafficLight context) {
        System.out.println("YELLOW - Caution!");
        context.setState(new RedState());
    }

    @Override
    public String getColor() {
        return "YELLOW";
    }
}

// Context
class TrafficLight {

    private TrafficLightState state;

    public TrafficLight() {
        state = new RedState(); // initial state
    }

    public void setState(TrafficLightState state) {
        this.state = state;
    }

    public TrafficLightState getState() {
        return state;
    }

    // Delegates behavior to current state
    public void change() {
        state.handle(this);
    }
}

// Client
public class StateDemo {

    public static void main(String[] args) {

        TrafficLight light = new TrafficLight();

        light.change(); // RED -> GREEN
        light.change(); // GREEN -> YELLOW
        light.change(); // YELLOW -> RED
    }
}
