public class AirConditioner extends Device {
    private boolean isOn;

    public AirConditioner(String name) {
        super(name);
        this.isOn = false;
    }

    public boolean isOn() {
        return isOn;
    }

    public void turnOn() {
        if (!isOn) {
            this.isOn = true;
            System.out.println(
                    "[" + name + "] Compressor started. Air Conditioner is now TURNED ON. Cooling room to 22°C.");
        } else {
            System.out.println("[" + name + "] Air Conditioner is already running.");
        }
    }

    public void turnOff() {
        this.isOn = false;
        System.out.println("[" + name + "] Air Conditioner is now TURNED OFF.");
    }
}
