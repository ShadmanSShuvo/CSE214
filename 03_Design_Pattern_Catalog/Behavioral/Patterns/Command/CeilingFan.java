/**
 * Receiver: CeilingFan
 * Demonstrates commands that track previous state to support intelligent multi-level undo.
 */
public class CeilingFan {
    public static final int OFF = 0;
    public static final int LOW = 1;
    public static final int MEDIUM = 2;
    public static final int HIGH = 3;

    private final String location;
    private int speed;

    public CeilingFan(String location) {
        this.location = location;
        this.speed = OFF;
    }

    public void high() {
        this.speed = HIGH;
        System.out.println("[" + location + " Ceiling Fan] set to HIGH speed.");
    }

    public void medium() {
        this.speed = MEDIUM;
        System.out.println("[" + location + " Ceiling Fan] set to MEDIUM speed.");
    }

    public void low() {
        this.speed = LOW;
        System.out.println("[" + location + " Ceiling Fan] set to LOW speed.");
    }

    public void off() {
        this.speed = OFF;
        System.out.println("[" + location + " Ceiling Fan] turned OFF.");
    }

    public int getSpeed() {
        return speed;
    }

    public String getLocation() {
        return location;
    }

    public String getSpeedName() {
        switch (speed) {
            case HIGH: return "HIGH";
            case MEDIUM: return "MEDIUM";
            case LOW: return "LOW";
            default: return "OFF";
        }
    }
}
