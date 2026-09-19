import java.util.ArrayList;
import java.util.List;

interface SmartDevice {
    void turnOn();

    void turnOff();
}

class SmartLight implements SmartDevice {
    @Override
    public void turnOn() {
        System.out.println("Smart Light ON");
    }

    @Override
    public void turnOff() {
        System.out.println("Smart Light OFF");
    }
}

// Third-party classes (cannot modify)
class OldSmartBulb {
    public void powerOn() {
        System.out.println("OldSmartBulb powered on");
    }

    public void powerOff() {
        System.out.println("OldSmartBulb powered off");
    }
}

class LegacyHeater {
    public void startHeating() {
        System.out.println("LegacyHeater started");
    }

    public void stopHeating() {
        System.out.println("LegacyHeater stopped");
    }
}

// Adapters
class OldSmartBulbAdapter implements SmartDevice {
    private final OldSmartBulb bulb;

    OldSmartBulbAdapter(OldSmartBulb bulb) {
        this.bulb = bulb;
    }

    @Override
    public void turnOn() {
        bulb.powerOn();
    }

    @Override
    public void turnOff() {
        bulb.powerOff();
    }
}

class LegacyHeaterAdapter implements SmartDevice {
    private final LegacyHeater heater;

    LegacyHeaterAdapter(LegacyHeater heater) {
        this.heater = heater;
    }

    @Override
    public void turnOn() {
        heater.startHeating();
    }

    @Override
    public void turnOff() {
        heater.stopHeating();
    }
}

// Usage
public class B2Adapter {
    public static void main(String[] args) {
        List<SmartDevice> devices = new ArrayList<>();
        devices.add(new SmartLight());
        devices.add(new OldSmartBulbAdapter(new OldSmartBulb()));
        devices.add(new LegacyHeaterAdapter(new LegacyHeater()));

        for (SmartDevice d : devices) {
            d.turnOn();
            d.turnOff();
        }
        // New third-party devices just need a new adapter class.
    }
}
