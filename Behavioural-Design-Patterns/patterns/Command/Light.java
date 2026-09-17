/**
 * Receiver: Light
 * Knows how to perform the actual operations related to lighting.
 */
public class Light {
    private final String location;
    private boolean isOn;
    private int brightness; // 0 to 100

    public Light(String location) {
        this.location = location;
        this.isOn = false;
        this.brightness = 100;
    }

    public void on() {
        this.isOn = true;
        System.out.println("[" + location + " Light] switched ON (Brightness: " + brightness + "%).");
    }

    public void off() {
        this.isOn = false;
        System.out.println("[" + location + " Light] switched OFF.");
    }

    public void dim(int level) {
        this.brightness = Math.max(0, Math.min(100, level));
        if (!this.isOn && brightness > 0) {
            this.isOn = true;
        }
        System.out.println("[" + location + " Light] dimmed to " + brightness + "%.");
    }

    public String getLocation() {
        return location;
    }

    public boolean isOn() {
        return isOn;
    }

    public int getBrightness() {
        return brightness;
    }
}
