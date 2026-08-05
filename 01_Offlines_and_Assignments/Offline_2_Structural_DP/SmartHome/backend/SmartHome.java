import java.util.*;


interface SmartDevice {
    void activate();

    void deactivate();

    double getPowerUsage();

    String getStatus();

    String getDeviceType();
}

class SmartLight implements SmartDevice {
    boolean on = false;

    @Override
    public void activate() {
        on = true;
    }

    @Override
    public void deactivate() {
        on = false;
    }

    @Override
    public double getPowerUsage() {
        return on ? 10.0 : 0.0;
    }

    @Override
    public String getStatus() {
        return "SmartLight: " + (on ? "ON" : "OFF");
    }

    @Override
    public String getDeviceType() {
        return "SmartLight";
    }
}

class SmartThermostat implements SmartDevice {
    boolean on = false;

    @Override
    public void activate() {
        on = true;
    }

    @Override
    public void deactivate() {
        on = false;
    }

    @Override
    public double getPowerUsage() {
        return on ? 150.0 : 0.0;
    }

    @Override
    public String getStatus() {
        return "SmartThermostat: " + (on ? "ON" : "OFF");
    }

    @Override
    public String getDeviceType() {
        return "SmartThermostat";
    }
}

class SmartSpeaker implements SmartDevice {
    boolean on = false;

    @Override
    public void activate() {
        on = true;
    }

    @Override
    public void deactivate() {
        on = false;
    }

    @Override
    public double getPowerUsage() {
        return on ? 5.0 : 0.0;
    }

    @Override
    public String getStatus() {
        return "SmartSpeaker: " + (on ? "Playing" : "Idle");
    }

    @Override
    public String getDeviceType() {
        return "SmartSpeaker";
    }
}

// decorator pattern
abstract class DeviceDecorator implements SmartDevice {
    protected SmartDevice wrapped;

    DeviceDecorator(SmartDevice wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String getDeviceType() {
        return wrapped.getDeviceType();
    }
}

class AccessRestricted extends DeviceDecorator {
    public int pin;
    boolean locked;

    AccessRestricted(SmartDevice wrapped, int pin) {
        super(wrapped);
        this.pin = pin;
        this.locked = true;
    }

    @Override
    public void activate() {
        if (!locked)
            wrapped.activate();
    }

    @Override
    public void deactivate() {
        if (!locked)
            wrapped.deactivate();
    }

    @Override
    public double getPowerUsage() {
        return wrapped.getPowerUsage();
    }

    @Override
    public String getStatus() {
        String s = wrapped.getStatus();
        if (locked)
            s += " [LOCKED]";
        return s;
    }

    public void unlock(int inputPin) {
        if (inputPin == pin)
            locked = false;
    }
}

class TimerControlled extends DeviceDecorator {
    public int timerSeconds;
    public boolean timerRunning;

    TimerControlled(SmartDevice wrapped, int timerSeconds) {
        super(wrapped);
        this.timerSeconds = timerSeconds;
        this.timerRunning = false;
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
        if (timerRunning)
            s += " (auto-off in " + timerSeconds + "s)";
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
    double powerCap;

    PowerThrottled(SmartDevice wrapped, double powerCap) {
        super(wrapped);
        this.powerCap = powerCap;
    }

    @Override
    public void activate() {
        wrapped.activate();
    }

    @Override
    public void deactivate() {
        wrapped.deactivate();
    }

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

// Composite pattern
abstract class CompositeDevice implements SmartDevice {
    protected String name;
    protected List<SmartDevice> children = new ArrayList<>();

    CompositeDevice(String name) {
        this.name = name;
    }

    void addDevice(SmartDevice device) {
        children.add(device);
    }

    List<SmartDevice> getChildren() {
        return children;
    } // NEW

    @Override
    public void activate() {
        for (SmartDevice d : children)
            d.activate();
    }

    @Override
    public void deactivate() {
        for (SmartDevice d : children)
            d.deactivate();
    }

    @Override
    public double getPowerUsage() {
        double total = 0;
        for (SmartDevice d : children)
            total += d.getPowerUsage();
        return total;
    }
}


class Room extends CompositeDevice {
    Room(String name) {
        super(name);
    }

    @Override
    public String getStatus() {
        StringBuilder sb = new StringBuilder("[" + name + "]");
        for (SmartDevice d : children)
            sb.append("\n  ").append(d.getStatus());
        return sb.toString();
    }

    @Override
    public String getDeviceType() {
        return "Room";
    }
}


class Home extends CompositeDevice {
    Home(String name) {
        super(name);
    }

    void addRoom(SmartDevice room) {
        addDevice(room);
    }

    @Override
    public String getStatus() {
        StringBuilder sb = new StringBuilder("=== " + name + " ===");
        for (SmartDevice d : children)
            sb.append("\n").append(d.getStatus());
        return sb.toString();
    }

    @Override
    public String getDeviceType() {
        return "Home";
    }
}



class EcoMode extends DeviceDecorator {
    CompositeDevice room;
    double budget;

    EcoMode(CompositeDevice wrapped, double budget) {
        super(wrapped);
        this.room = wrapped;
        this.budget = budget;
    }

    @Override
    public void activate() {
        wrapped.activate();

        List<SmartDevice> children = room.getChildren();
        double total = rawPower();
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
        for (SmartDevice d : room.getChildren())
            total += d.getPowerUsage();
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
    CompositeDevice room;
    Set<String> allowedDevices;

    GuestMode(CompositeDevice wrapped, Set<Class<?>> allowedDevices) {
        super(wrapped);
        this.room = wrapped;
        this.allowedDevices = new HashSet<>();
        for (Class<?> c : allowedDevices)
            this.allowedDevices.add(c.getSimpleName());
    }

    @Override
    public void activate() {
        for (SmartDevice d : room.getChildren()) {
            if (allowedDevices.contains(d.getDeviceType())) {
                d.activate();
            }
        }
    }

    @Override
    public void deactivate() {
        for (SmartDevice d : room.getChildren()) {
            if (allowedDevices.contains(d.getDeviceType())) {
                d.deactivate();
            }
        }
    }

    @Override
    public double getPowerUsage() {
        double total = 0;
        for (SmartDevice d : room.getChildren()) {
            if (allowedDevices.contains(d.getDeviceType())) {
                total += d.getPowerUsage();
            }
        }
        return total;
    }

    @Override
    public String getStatus() {
        StringBuilder sb = new StringBuilder("[GUEST MODE]\n[" + room.name + "]");
        for (SmartDevice d : room.getChildren()) {
            String s = d.getStatus();
            if (!allowedDevices.contains(d.getDeviceType())) {
                s += " [guest-restricted]";
            }
            sb.append("\n  ").append(s);
        }
        return sb.toString();
    }
}


public class SmartHome {

    public static void main(String[] args) {
        demoA();
        demoB();
        demoC();
        demoD();
        demoE();
        demoF();
    }

    static void header(String title) {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("  " + title);
        System.out.println("=".repeat(55));
    }

    // DEMO A: Home overview
    static void demoA() {
        header("DEMO A: Home Overview");

        Room living = new Room("Living Room");
        living.addDevice(new SmartLight());

        Room bedroom = new Room("Bedroom");
        bedroom.addDevice(new SmartThermostat());

        Home home = new Home("My Home");
        home.addRoom(living);
        home.addRoom(bedroom);

        System.out.println("Before activation:");
        System.out.println(home.getStatus());
        System.out.println("Power: " + home.getPowerUsage() + "W");

        home.activate();
        System.out.println("\nAfter activation:");
        System.out.println(home.getStatus());
        System.out.println("Power: " + home.getPowerUsage() + "W");
    }

    // DEMO B: Stacking device-level upgrades via composition, not flags
    static void demoB() {
        header("DEMO B: AccessRestricted + TimerControlled");

        AccessRestricted lockedLight = new AccessRestricted(new TimerControlled(new SmartLight(), 60), 1234);

        System.out.println("Step 1 — Activate while locked:");
        lockedLight.activate();
        System.out.println("  Status: " + lockedLight.getStatus());
        System.out.println("  Power:  " + lockedLight.getPowerUsage() + "W");

        System.out.println("\nStep 2 — Wrong PIN:");
        lockedLight.unlock(0000);
        lockedLight.activate();
        System.out.println("  Status: " + lockedLight.getStatus());
        System.out.println("  Power:  " + lockedLight.getPowerUsage() + "W");

        System.out.println("\nStep 3 — Correct PIN, activate:");
        lockedLight.unlock(1234);
        lockedLight.activate();
        System.out.println("  Status: " + lockedLight.getStatus());
        System.out.println("  Power:  " + lockedLight.getPowerUsage() + "W");

        System.out.println("\nStep 4 — Timer expires:");

        TimerControlled timer = (TimerControlled) lockedLight.wrapped;
        timer.simulateTimerExpiry();
        System.out.println("  Status: " + lockedLight.getStatus());
        System.out.println("  Power:  " + lockedLight.getPowerUsage() + "W");
    }

    // DEMO C: EcoMode as a decorator around a Room
    static void demoC() {
        header("DEMO C: EcoMode (budget = 100W)");

        Room office = new Room("Office");
        office.addDevice(new SmartLight());
        office.addDevice(new SmartLight());
        office.addDevice(new SmartThermostat());

        SmartDevice ecoOffice = new EcoMode(office, 100);

        System.out.println("Activating with EcoMode:");
        ecoOffice.activate();
        System.out.println("\n" + ecoOffice.getStatus());
        System.out.println("Power: " + ecoOffice.getPowerUsage() + "W");
    }

    // DEMO D: Order matters — throttle-then-eco vs raw-then-eco
    static void demoD() {
        header("DEMO D: Order Matters");

        // Setup 1: Thermostat throttled to 80W BEFORE joining the eco room
        Room room1 = new Room("Lab-1");
        room1.addDevice(new SmartLight());
        room1.addDevice(new SmartLight());
        room1.addDevice(new PowerThrottled(new SmartThermostat(), 80));
        SmartDevice ecoRoom1 = new EcoMode(room1, 100);

        System.out.println("Setup 1: Throttled SmartThermostat (80W) + EcoMode(100W)");
        ecoRoom1.activate();
        System.out.println(ecoRoom1.getStatus());
        System.out.println("Power: " + ecoRoom1.getPowerUsage() + "W");

        // Setup 2: Raw thermostat — EcoMode alone has to shed it
        Room room2 = new Room("Lab-2");
        room2.addDevice(new SmartLight());
        room2.addDevice(new SmartLight());
        room2.addDevice(new SmartThermostat());
        SmartDevice ecoRoom2 = new EcoMode(room2, 100);

        System.out.println("\nSetup 2: Raw SmartThermostat (150W) + EcoMode(100W)");
        ecoRoom2.activate();
        System.out.println(ecoRoom2.getStatus());
        System.out.println("Power: " + ecoRoom2.getPowerUsage() + "W");
    }

    // DEMO E: GuestMode wrapping a room of mixed enhanced devices
    static void demoE() {
        header("DEMO E: GuestMode + Mixed Enhancements");

        Room guest = new Room("Guest Room");
        guest.addDevice(new SmartSpeaker());
        guest.addDevice(new AccessRestricted(new SmartThermostat(), 9999)); // locked
        guest.addDevice(new TimerControlled(new SmartLight(), 120)); // timed

        Set<Class<?>> allowed = new HashSet<>(Arrays.asList(SmartLight.class, SmartSpeaker.class));
        SmartDevice guestRoom = new GuestMode(guest, allowed);

        System.out.println("Activating GuestMode room:");
        guestRoom.activate();
        System.out.println("\n" + guestRoom.getStatus());
        System.out.println("Guest-visible power: " + guestRoom.getPowerUsage() + "W");
    }

    // DEMO F: Enhancing an entire room 
    static void demoF() {
        header("DEMO F: prepareForNight wraps a Room");

        Room kids = new Room("Kids Room");
        kids.addDevice(new SmartLight());
        kids.addDevice(new SmartSpeaker());
        kids.addDevice(new SmartThermostat());

        SmartDevice night = prepareForNight(kids); // AccessRestricted(TimerControlled(...))

        System.out.println("Step 1 — Activate while locked (nothing happens):");
        night.activate();
        System.out.println("  Status:\n" + night.getStatus());
        System.out.println("  Power: " + night.getPowerUsage() + "W");

        System.out.println("\nStep 2 — Unlock and activate:");
        AccessRestricted lock = (AccessRestricted) night;
        lock.unlock(0);
        night.activate();
        System.out.println("  Status:\n" + night.getStatus());
        System.out.println("  Power: " + night.getPowerUsage() + "W");

        System.out.println("\nStep 3 — Timer expires (entire room shuts off):");
        TimerControlled timer = (TimerControlled) lock.wrapped;
        timer.simulateTimerExpiry();
        System.out.println("  Status:\n" + night.getStatus());
        System.out.println("  Power: " + night.getPowerUsage() + "W");

        System.out.println("\nStep 4 — Add to Home:");
        Home home = new Home("Night Home");
        home.addRoom(night); // works because night is just a SmartDevice
        System.out.println("  Home power: " + home.getPowerUsage() + "W");
    }

    static SmartDevice prepareForNight(SmartDevice entity) {
        return new AccessRestricted(new TimerControlled(entity, 3600), 0);
    }
}
