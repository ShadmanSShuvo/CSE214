public class LightSensor extends Device {
    public LightSensor(String name) {
        super(name);
    }

    public void detectBrightness(String level) {
        System.out.println("\n[" + name + "] Detected ambient light level: " + level);
        if ("High Brightness".equalsIgnoreCase(level)) {
            System.out.println("[" + name + "] Reporting 'High Brightness' event to Central Hub...");
            if (hub != null) {
                hub.notify(this, "HIGH_BRIGHTNESS");
            }
        } else {
            System.out.println("[" + name + "] Light level is normal. No alert sent.");
        }
    }
}
