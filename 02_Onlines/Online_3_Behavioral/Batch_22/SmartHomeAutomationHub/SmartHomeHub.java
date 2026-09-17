public class SmartHomeHub implements HomeHub {
    private LightSensor lightSensor;
    private AutomaticBlinds blinds;
    private AirConditioner airConditioner;

    public void registerDevices(LightSensor lightSensor, AutomaticBlinds blinds, AirConditioner airConditioner) {
        this.lightSensor = lightSensor;
        this.blinds = blinds;
        this.airConditioner = airConditioner;

        this.lightSensor.setHub(this);
        this.blinds.setHub(this);
        this.airConditioner.setHub(this);

        System.out.println("[Hub Setup] Central Hub registered: " + lightSensor.getName() + ", " + blinds.getName()
                + ", and " + airConditioner.getName());
    }

    @Override
    public void notify(Device device, String event) {
        System.out.println("  --> [Hub Notification Received] Event '" + event + "' from " + device.getName());

        if ("HIGH_BRIGHTNESS".equals(event)) {
            System.out.println("  [Hub Action]: High brightness detected outside! Ordering Blinds to close...");
            if (blinds != null) {
                blinds.close();
            }
        } else if ("BLINDS_CLOSED".equals(event)) {
            System.out.println(
                    "  [Hub Action]: Blinds are now closed. Room airflow restricted; ordering Air Conditioner to turn on...");
            if (airConditioner != null) {
                airConditioner.turnOn();
            }
        }
    }
}
