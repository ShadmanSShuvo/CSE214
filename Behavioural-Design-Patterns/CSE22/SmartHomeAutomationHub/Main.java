public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("   SMART HOME AUTOMATION HUB (MEDIATOR PATTERN)           ");
        System.out.println("==========================================================");

        // 1. Instantiate the Central Hub (Mediator)
        SmartHomeHub hub = new SmartHomeHub();

        // 2. Instantiate Colleagues (Devices)
        LightSensor lightSensor = new LightSensor("LivingRoom-LightSensor");
        AutomaticBlinds blinds = new AutomaticBlinds("LivingRoom-Blinds");
        AirConditioner ac = new AirConditioner("LivingRoom-AC");

        // 3. Register devices with the Mediator
        hub.registerDevices(lightSensor, blinds, ac);

        // 4. Test normal light level (no cascade triggered)
        System.out.println("\n--- Scenario 1: Normal Ambient Light ---");
        lightSensor.detectBrightness("Normal Brightness");

        // 5. Test High Brightness -> triggers cascade via Central Hub:
        // Sensor detects High Brightness -> Hub -> Blinds close -> Hub -> AC turns on
        System.out.println("\n--- Scenario 2: High Brightness Detected (Triggering Mediated Flow) ---");
        lightSensor.detectBrightness("High Brightness");

        System.out.println("\n==========================================================");
        System.out.println("Final Device States:");
        System.out.println("Blinds closed: " + blinds.isClosed());
        System.out.println("Air Conditioner running: " + ac.isOn());
        System.out.println("Smart Home Hub demonstration completed successfully.");
        System.out.println("==========================================================");
    }
}
