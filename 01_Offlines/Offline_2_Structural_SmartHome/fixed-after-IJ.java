import java.util.*;

// Base Component interface
interface SmartDevice {
    void activate();
    void deactivate();
    double getPowerUsage();
    String getStatus();
    String getDeviceType();
}

// -------------------------------------------------------
// LEAF DEVICES
// -------------------------------------------------------

class SmartLight implements SmartDevice {
    private boolean on = false;

    @Override
    public void activate() { on = true; }

    @Override
    public void deactivate() { on = false; }

    @Override
    public double getPowerUsage() { return on ? 10.0 : 0.0; }

    @Override
    public String getStatus() { return "SmartLight: " + (on ? "ON" : "OFF"); }

    @Override
    public String getDeviceType() { return "SmartLight"; }
}

class SmartThermostat implements SmartDevice {
    private boolean on = false;

    @Override
    public void activate() { on = true; }

    @Override
    public void deactivate() { on = false; }

    @Override
    public double getPowerUsage() { return on ? 150.0 : 0.0; }

    @Override
    public String getStatus() { return "SmartThermostat: " + (on ? "ON" : "OFF"); }

    @Override
    public String getDeviceType() { return "SmartThermostat"; }
}

class SmartSpeaker implements SmartDevice {
    private boolean on = false;

    @Override
    public void activate() { on = true; }

    @Override
    public void deactivate() { on = false; }

    @Override
    public double getPowerUsage() { return on ? 5.0 : 0.0; }

    @Override
    public String getStatus() { return "SmartSpeaker: " + (on ? "Playing" : "Idle"); }

    @Override
    public String getDeviceType() { return "SmartSpeaker"; }
}

// -------------------------------------------------------
// DECORATORS (DEVICES & ROOM LEVEL)
// -------------------------------------------------------

abstract class DeviceDecorator implements SmartDevice {
    protected SmartDevice wrapped;

    public DeviceDecorator(SmartDevice wrapped) {
        this.wrapped = wrapped;
    }

    public SmartDevice getWrapped() {
        return wrapped;
    }

    @Override
    public String getDeviceType() {
        return wrapped.getDeviceType();
    }
}

class AccessRestricted extends DeviceDecorator {
    private final int pin;
    private boolean locked = true;

    public AccessRestricted(SmartDevice wrapped, int pin) {
        super(wrapped);
        this.pin = pin;
    }

    @Override
    public void activate() {
        if (!locked) wrapped.activate();
    }

    @Override
    public void deactivate() {
        if (!locked) wrapped.deactivate();
    }

    @Override
    public double getPowerUsage() {
        return wrapped.getPowerUsage();
    }

    @Override
    public String getStatus() {
        String s = wrapped.getStatus();
        if (locked) s += " [LOCKED]";
        return s;
    }

    public void unlock(int inputPin) {
        if (inputPin == pin) locked = false;
    }
}

class TimerControlled extends DeviceDecorator {
    private final int timerSeconds;
    private boolean timerRunning = false;

    public TimerControlled(SmartDevice wrapped, int timerSeconds) {
        super(wrapped);
        this.timerSeconds = timerSeconds;
    }

    @Override
    public void activate() {
        wrapped.activate();
        timerRunning = true;
    }

    @Override
    public void deactivate() {
        wrapped.deactivate();
        timerRunning = false;
    }

    @Override
    public double getPowerUsage() {
        return wrapped.getPowerUsage();
    }

    @Override
    public String getStatus() {
        String s = wrapped.getStatus();
        if (timerRunning) s += " (auto-off in " + timerSeconds + "s)";
        return s;
    }

    public void simulateTimerExpiry() {
        if (timerRunning) {
            System.out.println("    >> Timer expired — auto-deactivating.");
            deactivate();
        }
    }
}

class PowerThrottled extends DeviceDecorator {
    private final double powerCap;

    public PowerThrottled(SmartDevice wrapped, double powerCap) {
        super(wrapped);
        this.powerCap = powerCap;
    }

    @Override
    public void activate() { wrapped.activate(); }

    @Override
    public void deactivate() { wrapped.deactivate(); }

    @Override
    public double getPowerUsage() {
        return Math.min(wrapped.getPowerUsage(), powerCap);
    }

    @Override
    public String getStatus() {
        String s = wrapped.getStatus();
        double originalPower = wrapped.getPowerUsage();
        if (originalPower > powerCap) {
            s += " [throttled to " + powerCap + "W]";
        }
        return s;
    }
}

// -------------------------------------------------------
// COMPOSITE CONTAINERS (ROOM & HOME)
// -------------------------------------------------------

interface DeviceContainer extends SmartDevice {
    List<SmartDevice> getDevices();
    String getName();
}

class Room implements DeviceContainer {
    private final String name;
    private final List<SmartDevice> devices = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    // Prevents nested rooms by accepting SmartDevice directly
    // If you want strictly non-room devices, you can enforce checks here
    public void addDevice(SmartDevice device) {
        if (device instanceof Room || device instanceof Home) {
            throw new IllegalArgumentException("Cannot add a Room or Home inside a Room!");
        }
        devices.add(device);
    }

    @Override
    public List<SmartDevice> getDevices() {
        return devices;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void activate() {
        for (SmartDevice d : devices) d.activate();
    }

    @Override
    public void deactivate() {
        for (SmartDevice d : devices) d.deactivate();
    }

    @Override
    public double getPowerUsage() {
        double total = 0;
        for (SmartDevice d : devices) total += d.getPowerUsage();
        return total;
    }

    @Override
    public String getStatus() {
        StringBuilder sb = new StringBuilder("[" + name + "]");
        for (SmartDevice d : devices) {
            sb.append("\n  ").append(d.getStatus());
        }
        return sb.toString();
    }

    @Override
    public String getDeviceType() {
        return "Room";
    }
}

class Home implements SmartDevice {
    private final String name;
    private final List<SmartDevice> rooms = new ArrayList<>();

    public Home(String name) {
        this.name = name;
    }

    public void addRoom(SmartDevice room) {
        rooms.add(room);
    }

    @Override
    public void activate() {
        for (SmartDevice r : rooms) r.activate();
    }

    @Override
    public void deactivate() {
        for (SmartDevice r : rooms) r.deactivate();
    }

    @Override
    public double getPowerUsage() {
        double total = 0;
        for (SmartDevice r : rooms) total += r.getPowerUsage();
        return total;
    }

    @Override
    public String getStatus() {
        StringBuilder sb = new StringBuilder("=== " + name + " ===");
        for (SmartDevice r : rooms) {
            sb.append("\n").append(r.getStatus());
        }
        return sb.toString();
    }

    @Override
    public String getDeviceType() {
        return "Home";
    }
}

// -------------------------------------------------------
// ADVANCED ROOM DECORATORS (ECO & GUEST MODES)
// -------------------------------------------------------

class EcoMode extends DeviceDecorator {
    private final DeviceContainer container;
    private final double budget;

    public EcoMode(DeviceContainer wrapped, double budget) {
        super(wrapped);
        this.container = wrapped;
        this.budget = budget;
    }

    @Override
    public void activate() {
        wrapped.activate();
        List<SmartDevice> children = container.getDevices();
        double total = rawPower();
        
        // Shed load in reverse order of addition
        for (int i = children.size() - 1; i >= 0 && total > budget; i--) {
            SmartDevice d = children.get(i);
            if (d.getPowerUsage() > 0) {
                d.deactivate();
                total = rawPower();
            }
        }
    }

    @Override
    public void deactivate() {
        wrapped.deactivate();
    }

    private double rawPower() {
        double total = 0;
        for (SmartDevice d : container.getDevices()) total += d.getPowerUsage();
        return total;
    }

    @Override
    public double getPowerUsage() {
        return Math.min(rawPower(), budget);
    }

    @Override
    public String getStatus() {
        return "[ECO: " + budget + "W budget]\n" + wrapped.getStatus();
    }
}

class GuestMode extends DeviceDecorator {
    private final DeviceContainer container;
    private final Set<String> allowedDevices;

    public GuestMode(DeviceContainer wrapped, Set<Class<?>> allowedClasses) {
        super(wrapped);
        this.container = wrapped;
        this.allowedDevices = new HashSet<>();
        for (Class<?> c : allowedClasses) {
            this.allowedDevices.add(c.getSimpleName());
        }
    }

    @Override
    public void activate() {
        for (SmartDevice d : container.getDevices()) {
            if (allowedDevices.contains(d.getDeviceType())) {
                d.activate();
            }
        }
    }

    @Override
    public void deactivate() {
        for (SmartDevice d : container.getDevices()) {
            if (allowedDevices.contains(d.getDeviceType())) {
                d.deactivate();
            }
        }
    }

    @Override
    public double getPowerUsage() {
        double total = 0;
        for (SmartDevice d : container.getDevices()) {
            if (allowedDevices.contains(d.getDeviceType())) {
                total += d.getPowerUsage();
            }
        }
        return total;
    }

    @Override
    public String getStatus() {
        StringBuilder sb = new StringBuilder("[GUEST MODE]\n[" + container.getName() + "]");
        for (SmartDevice d : container.getDevices()) {
            String s = d.getStatus();
            if (!allowedDevices.contains(d.getDeviceType())) {
                s += " [guest-restricted]";
            }
            sb.append("\n  ").append(s);
        }
        return sb.toString();
    }
}