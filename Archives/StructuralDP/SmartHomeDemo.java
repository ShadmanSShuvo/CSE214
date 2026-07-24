import java.util.*;

// ============================================================
//  No shared Behaviour. Each device is its own island.
//  Upgraded functionalities are booleans and fields crammed into each class.
//  Everything "works" but every new feature touches everything.
// ============================================================
/**
 * InnerSmartHomeSpaghettiDemo
 */
interface SmartDevice {
    void activate();

    void deactivate();

    double getPowerUsage();

    String getStatus();

}

class SmartLight implements SmartDevice {
    boolean on = false;
    // Pro upgrade flags
    boolean accessRestricted = false;
    int pin = 0;
    boolean locked = false;
    boolean timerControlled = false;
    int timerSeconds = 0;
    boolean timerRunning = false;
    boolean powerThrottled = false;
    double powerCap = 0;

    @Override
    public void activate() {
        if (accessRestricted && locked)
            return;
        on = true;
        if (timerControlled)
            timerRunning = true;
    }

    public void deactivate() {
        if (accessRestricted && locked)
            return;
        on = false;
        timerRunning = false;
    }

    @Override
    public double getPowerUsage() {
        double p = on ? 10.0 : 0.0;
        if (powerThrottled && p > powerCap)
            p = powerCap;
        return p;
    }

    @Override
    public String getStatus() {
        String s = "SmartLight: " + (on ? "ON" : "OFF");
        if (accessRestricted && locked)
            s += " [LOCKED]";
        if (timerControlled && timerRunning)
            s += " (auto-off in " + timerSeconds + "s)";
        if (powerThrottled && on && 10.0 > powerCap)
            s += " [throttled to " + powerCap + "W]";
        return s;
    }
}

class SmartThermostat implements SmartDevice {
    boolean on = false;
    // Same flags copy-pasted from SmartLight
    boolean accessRestricted = false;
    int pin = 0;
    boolean locked = false;
    boolean timerControlled = false;
    int timerSeconds = 0;
    boolean timerRunning = false;
    boolean powerThrottled = false;
    double powerCap = 0;

    @Override
    public void activate() {
        if (accessRestricted && locked)
            return;
        on = true;
        if (timerControlled)
            timerRunning = true;
    }

    @Override
    public void deactivate() {
        if (accessRestricted && locked)
            return;
        on = false;
        timerRunning = false;
    }

    @Override
    public double getPowerUsage() {
        double p = on ? 150.0 : 0.0;
        if (powerThrottled && p > powerCap)
            p = powerCap;
        return p;
    }

    @Override
    public String getStatus() {
        String s = "SmartThermostat: " + (on ? "ON" : "OFF");
        if (accessRestricted && locked)
            s += " [LOCKED]";
        if (timerControlled && timerRunning)
            s += " (auto-off in " + timerSeconds + "s)";
        if (powerThrottled && on && 150.0 > powerCap)
            s += " [throttled to " + powerCap + "W]";
        return s;
    }
}

class SmartSpeaker implements SmartDevice {
    boolean on = false;
    // Same flags AGAIN — copy-pasted a third time
    boolean accessRestricted = false;
    int pin = 0;
    boolean locked = false;
    boolean timerControlled = false;
    int timerSeconds = 0;
    boolean timerRunning = false;
    boolean powerThrottled = false;
    double powerCap = 0;

    @Override
    public void activate() {
        if (accessRestricted && locked)
            return;
        on = true;
        if (timerControlled)
            timerRunning = true;
    }

    @Override
    public void deactivate() {
        if (accessRestricted && locked)
            return;
        on = false;
        timerRunning = false;
    }

    @Override
    public double getPowerUsage() {
        double p = on ? 5.0 : 0.0;
        if (powerThrottled && p > powerCap)
            p = powerCap;
        return p;
    }

    @Override
    public String getStatus() {
        String s = "SmartSpeaker: " + (on ? "Playing" : "Idle");
        if (accessRestricted && locked)
            s += " [LOCKED]";
        if (timerControlled && timerRunning)
            s += " (auto-off in " + timerSeconds + "s)";
        if (powerThrottled && on && 5.0 > powerCap)
            s += " [throttled to " + powerCap + "W]";
        return s;
    }
}

abstract class DeviceDecorator implements SmartDevice {
    protected SmartDevice wrapped;

    DeviceDecorator(SmartDevice wrapped) {
        this.wrapped = wrapped;
    }
}

// Rooms are basically a list of devices. But here, rooms can't just hold a list
// of "devices" — there's no shared type.
// So it holds three separate lists. Adding a fourth device type means
// editing Room, every helper method, and every demo.
class Room implements SmartDevice {
    String name;
    List<SmartLight> lights = new ArrayList<>();
    List<SmartThermostat> thermostats = new ArrayList<>();
    List<SmartSpeaker> speakers = new ArrayList<>();
    // Track insertion order separately because three lists lost it
    List<Object> insertionOrder = new ArrayList<>();
    List<SmartDevice> children = new ArrayList<>(); // for future-proofing

    // Room-level enhancement flags
    boolean ecoMode = false;
    double ecoBudget = 0;
    boolean guestMode = false;
    Set<String> guestAllowed = new HashSet<>(); // "SmartLight", "SmartThermostat", "SmartSpeaker"

    Room(String name) {
        this.name = name;
    }

    void addLight(SmartLight l) {
        lights.add(l);
        children.add(l);
        insertionOrder.add(l);
    }

    void addThermostat(SmartThermostat t) {
        thermostats.add(t);
        children.add(t);
        insertionOrder.add(t);
    }

    void addSpeaker(SmartSpeaker s) {
        speakers.add(s);
        children.add(s);
        insertionOrder.add(s);
    }

    void addDevice(SmartDevice device) {
        children.add(device); // no instanceof, no branches, no type check
    }

    void activateAll() {
        for (SmartDevice d : children)
            d.activate();

        // EcoMode: shed in reverse insertion order
        if (ecoMode && getTotalPower() > ecoBudget) {
            for (int i = insertionOrder.size() - 1; i >= 0 && getTotalPower() > ecoBudget; i--) {
                Object dev = insertionOrder.get(i);
                if (dev instanceof SmartDevice) {
                    ((SmartDevice) dev).deactivate();
                }
            }
        }
    }

    void deactivateAll() {
        for (SmartDevice d : children)
            d.deactivate();
    }

    @Override
    public void activate() {
        activateAll();
    }

    @Override
    public void deactivate() {
        deactivateAll();
    }

    double getTotalPower() {
        double total = 0;
        if (guestMode) {
            for (Object dev : insertionOrder) {
                if (dev instanceof SmartDevice) {
                    SmartDevice d = (SmartDevice) dev;
                    String className = d.getClass().getSimpleName();
                    if (guestAllowed.contains(className)) {
                        total += d.getPowerUsage();
                    }
                }
            }
        } else {
            for (SmartDevice d : children)
                total += d.getPowerUsage();
        }
        if (ecoMode && total > ecoBudget)
            total = ecoBudget;
        return total;
    }

    @Override
    public double getPowerUsage() {
        return getTotalPower();
    }

    @Override
    public String getStatus() {
        StringBuilder sb = new StringBuilder("[" + name + "]");
        if (ecoMode)
            sb.insert(0, "[ECO: " + ecoBudget + "W budget]\n");
        if (guestMode)
            sb.insert(0, "[GUEST MODE]\n");

        // Can't just loop "devices" — have to loop each list separately
        for (Object dev : insertionOrder) {
            if (dev instanceof SmartDevice) {
                sb.append("\n  ").append(((SmartDevice) dev).getStatus());
            }
        }
        return sb.toString();
    }
}

// Home is basically Room's logic copy-pasted with "rooms" instead of "devices"
class Home implements SmartDevice {
    String name;
    List<Room> rooms = new ArrayList<>();
    // Home-level eco/guest — duplicated from Room
    boolean ecoMode = false;
    double ecoBudget = 0;
    boolean guestMode = false;
    Set<String> guestAllowed = new HashSet<>();

    Home(String name) {
        this.name = name;
    }

    void addRoom(Room r) {
        rooms.add(r);
    }

    void addRoom(SmartDevice r) {
        if (r instanceof Room) {
            addRoom((Room) r);
        } else {
            throw new IllegalArgumentException("Unsupported room type: " + r.getClass().getName());
        }
    }

    void activateAll() {
        for (Room r : rooms)
            r.activateAll();
        // Home-level eco — completely separate logic from Room-level eco
        if (ecoMode && getTotalPower() > ecoBudget) {
            // Shed entire rooms in reverse order... ugly
            for (int i = rooms.size() - 1; i >= 0 && getTotalPower() > ecoBudget; i--) {
                rooms.get(i).deactivateAll();
            }
        }
    }

    void deactivateAll() {
        for (Room r : rooms)
            r.deactivateAll();
    }


    double getTotalPower() {
        double total = 0;
        for (Room r : rooms)
            total += r.getTotalPower();
        if (ecoMode && total > ecoBudget)
            total = ecoBudget;
        return total;
    }


    @Override
    public double getPowerUsage() {
        return getTotalPower();
    }

    @Override
    public void activate() {
        activateAll();
    }

    @Override
    public void deactivate() {
        deactivateAll();
    }

    @Override
    public String getStatus() {
        StringBuilder sb = new StringBuilder("=== " + name + " ===");
        if (ecoMode)
            sb.insert(0, "[ECO: " + ecoBudget + "W budget]\n");
        if (guestMode)
            sb.insert(0, "[GUEST MODE]\n");
        for (Room r : rooms)
            sb.append("\n").append(r.getStatus());
        return sb.toString();
    }
}

class AccessRestricted extends DeviceDecorator {
    int pin;
    boolean locked;

    AccessRestricted(SmartDevice wrapped, int pin) {
        super(wrapped);
        this.pin = pin;
        this.locked = true;
    }

    @Override
    public void activate() {
        if (!locked) {
            wrapped.activate();
        }
    }

    @Override
    public void deactivate() {
        if (!locked) {
            wrapped.deactivate();
        }
    }

    @Override
    public double getPowerUsage() {
        return wrapped.getPowerUsage();
    }

    @Override
    public String getStatus() {
        String status = wrapped.getStatus();
        if (locked) {
            status += " [LOCKED]";
        }
        return status;
    }

    public void unlock(int inputPin) {
        if (inputPin == pin) {
            locked = false;
        }
    }
}


class TimerControlled extends DeviceDecorator {
    int timerSeconds;
    boolean timerRunning;

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
        String status = wrapped.getStatus();
        if (timerRunning) {
            status += " (auto-off in " + timerSeconds + "s)";
        }
        return status;
    }

    public void simulateTimerExpiry() {
        if (timerRunning) {
            wrapped.deactivate();
            timerRunning = false;
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
        double usage = wrapped.getPowerUsage();
        return Math.min(usage, powerCap);
    }

    @Override
    public String getStatus() {
        String status = wrapped.getStatus();
        if (wrapped.getPowerUsage() > powerCap) {
            status += " [throttled to " + powerCap + "W]";
        }
        return status;
    }
}

// EcoMode and GuestMode are not implemented as decorators because they operate at the Room level, not the individual device level. They are implemented as flags within the Room class, which leads to code duplication and complexity when managing device states.
class EcoMode extends DeviceDecorator {
    double budget;

    EcoMode(SmartDevice wrapped, double budget) {
        super(wrapped);
        this.budget = budget;
    }

    @Override
    public void activate() {
        wrapped.activate();
        // EcoMode logic would be applied at the Room level, not here.
    }

    @Override
    public void deactivate() {
        wrapped.deactivate();
    }

    @Override
    public double getPowerUsage() {
        return wrapped.getPowerUsage();
    }

    @Override
    public String getStatus() {
        return wrapped.getStatus();
    }
}

class GuestMode extends DeviceDecorator {
    Set<String> allowedDevices;

    // GuestMode(Room wrapped, Set<String> allowedDevices) {
    //     super(wrapped);
    //     this.allowedDevices = allowedDevices;
    // }

    GuestMode(Room wrapped, Set<Class<?>> allowedDevices) {
        super(wrapped);
        this.allowedDevices = new HashSet<>();
        for (Class<?> device : allowedDevices) {
            this.allowedDevices.add(device.getSimpleName());
        }
    }
    @Override
    public void activate() {
        // GuestMode logic would be applied at the Room level, not here.
        wrapped.activate();
    }

    @Override
    public void deactivate() {
        wrapped.deactivate();
    }

    @Override
    public double getPowerUsage() {
        return wrapped.getPowerUsage();
    }

    @Override
    public String getStatus() {
        return wrapped.getStatus();
    }
}
// ============================================================
// MAIN — Some demos, but built on the mess above
// ============================================================

public class SmartHomeDemo {

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
        living.addLight(new SmartLight());
        living.addSpeaker(new SmartSpeaker());

        Room bedroom = new Room("Bedroom");
        bedroom.addLight(new SmartLight());
        bedroom.addThermostat(new SmartThermostat());

        Home home = new Home("My Home");
        home.addRoom(living);
        home.addRoom(bedroom);

        System.out.println("Before activation:");
        System.out.println(home.getStatus());
        System.out.println("Power: " + home.getTotalPower() + "W");

        home.activateAll();
        System.out.println("\nAfter activation:");
        System.out.println(home.getStatus());
        System.out.println("Power: " + home.getTotalPower() + "W");
    }

    // DEMO B: Stacking device-level "upgrades"
    static void demoB() {
        header("DEMO B: AccessRestricted + TimerControlled");

        SmartLight SmartLight = new SmartLight();
        // "Upgrading" = flipping flags. No composition, no wrapping.
        SmartLight.accessRestricted = true;
        SmartLight.pin = 1234;
        SmartLight.locked = true;
        SmartLight.timerControlled = true;
        SmartLight.timerSeconds = 60;

        System.out.println("Step 1 — Activate while locked:");
        SmartLight.activate();
        System.out.println("  Status: " + SmartLight.getStatus());
        System.out.println("  Power:  " + SmartLight.getPowerUsage() + "W");

        System.out.println("\nStep 2 — Wrong PIN:");
        // Unlock logic is here in main, not encapsulated anywhere
        if (0000 == SmartLight.pin) {
            SmartLight.locked = false;
            System.out.println("    >> Unlock SUCCESS");
        } else {
            System.out.println("    >> Unlock FAILED");
        }
        System.out.println("  Status: " + SmartLight.getStatus());

        System.out.println("\nStep 3 — Correct PIN, activate:");
        if (1234 == SmartLight.pin) {
            SmartLight.locked = false;
            System.out.println("    >> Unlock SUCCESS");
        } else {
            System.out.println("    >> Unlock FAILED");
        }
        SmartLight.activate();
        System.out.println("  Status: " + SmartLight.getStatus());
        System.out.println("  Power:  " + SmartLight.getPowerUsage() + "W");

        System.out.println("\nStep 4 — Timer expires:");
        // Simulating timer — manually calling deactivate because there's
        // no timer object, no dedicated class, just a flag
        if (SmartLight.timerRunning) {
            System.out.println("    >> Timer expired — auto-deactivating.");
            SmartLight.on = false; // reaching directly into internals
            SmartLight.timerRunning = false;
        }
        System.out.println("  Status: " + SmartLight.getStatus());
        System.out.println("  Power:  " + SmartLight.getPowerUsage() + "W");
    }

    // DEMO C: EcoMode
    static void demoC() {
        header("DEMO C: EcoMode (budget = 100W)");

        Room office = new Room("Office");
        office.addLight(new SmartLight());
        office.addLight(new SmartLight());
        office.addThermostat(new SmartThermostat());
        office.ecoMode = true;
        office.ecoBudget = 100;

        System.out.println("Activating with EcoMode:");
        office.activateAll();
        System.out.println("\n" + office.getStatus());
        System.out.println("Power: " + office.getTotalPower() + "W");
    }

    // DEMO D: Order matters
    static void demoD() {
        header("DEMO D: Order Matters");

        // Setup 1: Throttled SmartThermostat
        Room room1 = new Room("Lab-1");
        room1.addLight(new SmartLight());
        room1.addLight(new SmartLight());
        SmartThermostat t1 = new SmartThermostat();
        t1.powerThrottled = true;
        t1.powerCap = 80;
        room1.addThermostat(t1);
        room1.ecoMode = true;
        room1.ecoBudget = 100;

        System.out.println("Setup 1: Throttled SmartThermostat (80W) + EcoMode(100W)");
        room1.activateAll();
        System.out.println(room1.getStatus());
        System.out.println("Power: " + room1.getTotalPower() + "W");

        // Setup 2: Raw SmartThermostat
        Room room2 = new Room("Lab-2");
        room2.addLight(new SmartLight());
        room2.addLight(new SmartLight());
        room2.addThermostat(new SmartThermostat());
        room2.ecoMode = true;
        room2.ecoBudget = 100;

        System.out.println("\nSetup 2: Raw SmartThermostat (150W) + EcoMode(100W)");
        room2.activateAll();
        System.out.println(room2.getStatus());
        System.out.println("Power: " + room2.getTotalPower() + "W");
    }

    // DEMO E: GuestMode
    static void demoE() {
        header("DEMO E: GuestMode + Mixed Enhancements");

        Room guest = new Room("Guest Room");

        guest.addSpeaker(new SmartSpeaker());

        SmartThermostat lockedThermo = new SmartThermostat();
        lockedThermo.accessRestricted = true;
        lockedThermo.pin = 9999;
        lockedThermo.locked = true;
        guest.addThermostat(lockedThermo);

        SmartLight timedLight = new SmartLight();
        timedLight.timerControlled = true;
        timedLight.timerSeconds = 120;
        guest.addLight(timedLight);

        guest.guestMode = true;
        guest.guestAllowed.add("SmartLight");
        guest.guestAllowed.add("SmartSpeaker");

        System.out.println("Activating GuestMode room:");
        guest.activateAll();
        System.out.println("\n" + guest.getStatus());
        System.out.println("Guest-visible power: " + guest.getTotalPower() + "W");
    }

    // DEMO F: "Enhance an entire room"
    // THIS IS WHERE THE SPAGHETTI CODE STUGGLES MOST.
    // There's no way to upgrade a Room with AccessRestricted or TimerControlled
    // because those are boolean flags on device classes, not composable objects.
    // We have to fake it with MORE flags on the Room itself.
    static void demoF() {
        header("DEMO F: prepareForNight wraps a Room");

        Room kids = new Room("Kids Room");
        kids.addLight(new SmartLight());
        kids.addSpeaker(new SmartSpeaker());
        kids.addThermostat(new SmartThermostat());

        // Can't call prepareForNight(kids) because Room and SmartLight
        // are completely different .
        // So we add MORE flags to Room. Copy-paste from device logic.
        boolean roomLocked = true;
        int roomPin = 0;
        boolean roomTimerControlled = true;
        int roomTimerSeconds = 3600;
        boolean roomTimerRunning = false;

        System.out.println("Step 1 — Activate while locked (nothing happens):");
        if (!roomLocked) {
            kids.activateAll();
        }
        // Have to manually build status with room-level lock annotation
        System.out.println("  Status:\n" + kids.getStatus() + " [LOCKED]");
        System.out.println("  Power: " + kids.getTotalPower() + "W");

        System.out.println("\nStep 2 — Unlock and activate:");
        if (0 == roomPin) {
            roomLocked = false;
            System.out.println("    >> Unlock SUCCESS");
        }
        if (!roomLocked) {
            kids.activateAll();
            roomTimerRunning = true;
        }
        String timerSuffix = roomTimerRunning ? " (auto-off in " + roomTimerSeconds + "s)" : "";
        System.out.println("  Status:\n" + kids.getStatus() + timerSuffix);
        System.out.println("  Power: " + kids.getTotalPower() + "W");

        System.out.println("\nStep 3 — Timer expires (entire room shuts off):");
        if (roomTimerRunning) {
            System.out.println("    >> Timer expired — auto-deactivating.");
            kids.deactivateAll();
            roomTimerRunning = false;
        }
        System.out.println("  Status:\n" + kids.getStatus());
        System.out.println("  Power: " + kids.getTotalPower() + "W");

        System.out.println("\nStep 4 — Add to Home:");
        Home home = new Home("Night Home");
        home.addRoom(kids);
        System.out.println("  Home power: " + home.getTotalPower() + "W");

        // NOTE: prepareForNight as a reusable method is IMPOSSIBLE here.
        // It would need to accept both SmartLight AND Room AND SmartThermostat...
        // You'd need:
        //
        // static Object prepareForNight(Object entity) {
        // if (entity instanceof SmartLight) { ... }
        // else if (entity instanceof SmartThermostat) { ... }
        // else if (entity instanceof Room) { ... }
        // // and return what? Object? Cast everywhere?
        // }
        //
        // This is the point where the design collapses completely.
    }
}
