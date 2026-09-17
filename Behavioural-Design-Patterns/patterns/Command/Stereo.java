/**
 * Receiver: Stereo
 * Knows how to control audio entertainment equipment.
 */
public class Stereo {
    private final String location;
    private boolean isOn;
    private String mode; // CD, Radio, Bluetooth
    private int volume; // 0 to 100

    public Stereo(String location) {
        this.location = location;
        this.isOn = false;
        this.mode = "Off";
        this.volume = 0;
    }

    public void on() {
        this.isOn = true;
        System.out.println("[" + location + " Stereo] powered ON.");
    }

    public void off() {
        this.isOn = false;
        System.out.println("[" + location + " Stereo] powered OFF.");
    }

    public void setCD() {
        this.mode = "CD";
        System.out.println("[" + location + " Stereo] input set to CD.");
    }

    public void setRadio() {
        this.mode = "Radio";
        System.out.println("[" + location + " Stereo] input set to Radio.");
    }

    public void setVolume(int volume) {
        this.volume = Math.max(0, Math.min(100, volume));
        System.out.println("[" + location + " Stereo] volume set to " + this.volume + ".");
    }

    public String getLocation() {
        return location;
    }

    public boolean isOn() {
        return isOn;
    }

    public int getVolume() {
        return volume;
    }

    public String getMode() {
        return mode;
    }
}
