import java.util.*;

/**
 * SmartHome.java
 *
 * NexaHome Smart Home Automation Hub — SOLID refactor.
 *
 * DESIGN OVERVIEW
 * ----------------
 * Two GoF patterns do all the work here:
 *
 *   1. COMPOSITE  — SmartDevice is the uniform contract shared by leaf
 *      devices (SmartLight, SmartThermostat, SmartSpeaker), composites
 *      (Room, Home), and every decorator below. A Room never needs to know
 *      whether a child is a plain SmartLight, a PowerThrottled thermostat,
 *      or an EcoMode-wrapped sub-room — it just calls activate() /
 *      deactivate() / getPowerUsage() / getStatus() on the interface.
 *
 *   2. DECORATOR   — Each upgrade (AccessRestricted, TimerControlled,
 *      PowerThrottled, EcoMode, GuestMode) wraps an existing SmartDevice and
 *      returns something that is *itself* a SmartDevice. That's what makes
 *      stacking free: new AccessRestricted(new PowerThrottled(...), pin) is
 *      just object composition, not a new class for every combination.
 *
 * SOLID MAPPING
 * -------------
 *   S — Each class has exactly one reason to change: a leaf device owns its
 *       own on/off + wattage; a composite owns aggregation/cascading; each
 *       decorator owns exactly one upgrade's behavior.
 *   O — Adding a new device type (e.g. SmartLock) means implementing
 *       SmartDevice — zero existing classes touched. Adding a new upgrade
 *       means writing one new DeviceDecorator subclass — Room, Home, and
 *       every existing decorator are untouched.
 *   L — Anywhere a SmartDevice is expected, ANY leaf, composite, or
 *       decorated device can be substituted without breaking behavior.
 *       That's precisely why Room.activate() has no "if upgraded" branch.
 *   I — SmartDevice only exposes the four operations every entity needs.
 *       The extra getChildren()/getName() that only composites need live on
 *       the separate DeviceGroup interface, so leaf devices aren't forced
 *       to implement irrelevant methods (and, as a bonus, this is exactly
 *       what makes `new EcoMode(new SmartLight(), 100)` fail to compile —
 *       EcoMode/GuestMode require a DeviceGroup, and a leaf isn't one).
 *   D — Room, Home, and every decorator depend only on the SmartDevice
 *       abstraction, never on concrete classes like SmartLight or Room
 *       itself. High-level cascading logic doesn't know low-level device
 *       details.
 */
public class SmartHome {

    public static void main(String[] args) {
        demoA_UniformHandling();
        demoB_StackedDeviceUpgrades();
        demoC_EcoMode();
        demoD_OrderSensitivity();
        demoE_GuestMode();
        demoF_UpgradeAnEntireRoom();
    }

    private static void header(String title) {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("  " + title);
        System.out.println("=".repeat(55));
    }

    // DEMO A — one interface, three levels of nesting
    static void demoA_UniformHandling() {
        header("DEMO A: Uniform Handling (device / room / home)");

        Room living = new Room("Living Room");
        living.addDevice(new SmartLight());
        living.addDevice(new SmartSpeaker());

        Room bedroom = new Room("Bedroom");
        bedroom.addDevice(new SmartLight());
        bedroom.addDevice(new SmartThermostat());

        Home home = new Home("My Home");
        home.addRoom(living);
        home.addRoom(bedroom);

        System.out.println("Before activation: " + home.getPowerUsage() + "W");
        home.activate(); // one call, cascades through Home -> Room -> device uniformly
        System.out.println(home.getStatus());
        System.out.println("After activation: " + home.getPowerUsage() + "W");
    }

    // DEMO B — stacking device-level upgrades via decorator composition
    static void demoB_StackedDeviceUpgrades() {
        header("DEMO B: AccessRestricted + TimerControlled (stacked)");

        AccessRestricted locked = new AccessRestricted(new SmartLight(), 1234);
        TimerControlled timedAndLocked = new TimerControlled(locked, 60);

        System.out.println("Step 1 - Activate while locked:");
        timedAndLocked.activate();
        System.out.println("  Status: " + timedAndLocked.getStatus());
        System.out.println("  Power:  " + timedAndLocked.getPowerUsage() + "W");

        System.out.println("\nStep 2 - Wrong PIN:");
        locked.unlock(0000);
        System.out.println("  Status: " + timedAndLocked.getStatus());

        System.out.println("\nStep 3 - Correct PIN, activate:");
        locked.unlock(1234);
        timedAndLocked.activate();
        System.out.println("  Status: " + timedAndLocked.getStatus());
        System.out.println("  Power:  " + timedAndLocked.getPowerUsage() + "W");

        System.out.println("\nStep 4 - Timer expires:");
        timedAndLocked.simulateTimerExpiry();
        System.out.println("  Status: " + timedAndLocked.getStatus());
        System.out.println("  Power:  " + timedAndLocked.getPowerUsage() + "W");
    }

    // DEMO C — EcoMode sheds most-recently-added devices first
    static void demoC_EcoMode() {
        header("DEMO C: EcoMode (budget = 100W)");

        Room office = new Room("Office");
        office.addDevice(new SmartLight());
        office.addDevice(new SmartLight());
        office.addDevice(new SmartThermostat());

        EcoMode ecoOffice = new EcoMode(office, 100);
        ecoOffice.activate();
        System.out.println(ecoOffice.getStatus());
        System.out.println("Power: " + ecoOffice.getPowerUsage() + "W");
    }

    // DEMO D — order sensitivity: throttle-then-eco vs raw-then-eco
    static void demoD_OrderSensitivity() {
        header("DEMO D: Order Matters");

        Room lab1 = new Room("Lab-1");
        lab1.addDevice(new SmartLight());
        lab1.addDevice(new SmartLight());
        lab1.addDevice(new PowerThrottled(new SmartThermostat(), 80)); // throttled BEFORE eco
        EcoMode eco1 = new EcoMode(lab1, 100);

        System.out.println("Setup 1: Throttled thermostat (80W) + EcoMode(100W)");
        eco1.activate();
        System.out.println(eco1.getStatus());
        System.out.println("Power: " + eco1.getPowerUsage() + "W");

        Room lab2 = new Room("Lab-2");
        lab2.addDevice(new SmartLight());
        lab2.addDevice(new SmartLight());
        lab2.addDevice(new SmartThermostat()); // raw — eco has to shed it entirely
        EcoMode eco2 = new EcoMode(lab2, 100);

        System.out.println("\nSetup 2: Raw thermostat (150W) + EcoMode(100W)");
        eco2.activate();
        System.out.println(eco2.getStatus());
        System.out.println("Power: " + eco2.getPowerUsage() + "W");

        System.out.println("\n>> Same budget, different outcome: " + eco1.getPowerUsage()
                + "W (kept everything, throttle absorbed the overage) vs "
                + eco2.getPowerUsage() + "W (had to shed the whole thermostat).");
    }

    // DEMO E — GuestMode + mixed device-level enhancements together
    static void demoE_GuestMode() {
        header("DEMO E: GuestMode + Mixed Enhancements");

        Room guestRoom = new Room("Guest Room");
        guestRoom.addDevice(new SmartSpeaker());
        guestRoom.addDevice(new AccessRestricted(new SmartThermostat(), 9999));
        guestRoom.addDevice(new TimerControlled(new SmartLight(), 120));

        Set<Class<?>> allowed = new HashSet<>(Arrays.asList(SmartLight.class, SmartSpeaker.class));
        GuestMode guestMode = new GuestMode(guestRoom, allowed);

        guestMode.activate();
        System.out.println(guestMode.getStatus());
        System.out.println("Guest-visible power: " + guestMode.getPowerUsage() + "W");
    }

    // DEMO F — upgrading an ENTIRE ROOM, not just a device.
    // Because Room implements SmartDevice, it can be wrapped by the exact
    // same decorators used on a single device. No parallel "RoomUpgrade"
    // classes needed.
    static void demoF_UpgradeAnEntireRoom() {
        header("DEMO F: prepareForNight wraps a whole Room");

        Room kids = new Room("Kids Room");
        kids.addDevice(new SmartLight());
        kids.addDevice(new SmartSpeaker());
        kids.addDevice(new SmartThermostat());

        // Wrap the WHOLE room, same decorators used for single devices earlier.
        AccessRestricted lockedKids = new AccessRestricted(kids, 0);
        TimerControlled nightRoom = new TimerControlled(lockedKids, 3600);

        System.out.println("Step 1 - Activate while locked (nothing happens):");
        nightRoom.activate();
        System.out.println("  Status:\n" + nightRoom.getStatus());
        System.out.println("  Power: " + nightRoom.getPowerUsage() + "W");

        System.out.println("\nStep 2 - Unlock and activate:");
        lockedKids.unlock(0);
        nightRoom.activate();
        System.out.println("  Status:\n" + nightRoom.getStatus());
        System.out.println("  Power: " + nightRoom.getPowerUsage() + "W");

        System.out.println("\nStep 3 - Timer expires (entire room shuts off):");
        nightRoom.simulateTimerExpiry();
        System.out.println("  Status:\n" + nightRoom.getStatus());
        System.out.println("  Power: " + nightRoom.getPowerUsage() + "W");

        System.out.println("\nStep 4 - Add the (still-upgraded) room to a Home:");
        Home home = new Home("Night Home");
        home.addRoom(nightRoom); // Home.addRoom takes a SmartDevice, so this just works
        System.out.println("  Home power: " + home.getPowerUsage() + "W");
    }

    /** Reusable across a single device, a Room, or a Home — all are SmartDevice. */
    static SmartDevice prepareForNight(SmartDevice entity) {
        return new TimerControlled(new AccessRestricted(entity, 0), 3600);
    }
}

// =====================================================================
//  CORE ABSTRACTIONS
// =====================================================================

/**
 * The single contract every entity in the system implements: a lone device,
 * a room full of devices, an entire home, or any upgraded version of any of
 * those. This is what makes uniform handling (requirement 1) possible.
 */
interface SmartDevice {
    void activate();
    void deactivate();
    double getPowerUsage();
    String getStatus();

    /**
     * The "effective" leaf device type, e.g. SmartLight.class — used by
     * GuestMode to decide whether a (possibly decorated) child is an
     * allowed type, without any instanceof chains. Decorators delegate this
     * to whatever they wrap, so it always resolves to the real leaf type.
     */
    Class<?> getType();
}

/**
 * Extra capability needed only by composites (Room, Home): access to their
 * children in insertion order, and a name for status reporting. Kept
 * separate from SmartDevice (Interface Segregation) so leaf devices aren't
 * forced to implement it — and so EcoMode/GuestMode constructors can require
 * a DeviceGroup specifically, which is what makes applying them to a bare
 * SmartLight a compile error rather than a runtime check.
 */
interface DeviceGroup extends SmartDevice {
    List<SmartDevice> getChildren();
    String getName();
}

// =====================================================================
//  LEAF DEVICES
// =====================================================================

class SmartLight implements SmartDevice {
    private static final double WATTAGE = 10.0;
    private boolean on = false;

    @Override public void activate() { on = true; }
    @Override public void deactivate() { on = false; }
    @Override public double getPowerUsage() { return on ? WATTAGE : 0.0; }
    @Override public String getStatus() { return "Light: " + (on ? "ON" : "OFF"); }
    @Override public Class<?> getType() { return SmartLight.class; }
}

class SmartThermostat implements SmartDevice {
    private static final double WATTAGE = 150.0;
    private boolean on = false;

    @Override public void activate() { on = true; }
    @Override public void deactivate() { on = false; }
    @Override public double getPowerUsage() { return on ? WATTAGE : 0.0; }
    @Override public String getStatus() { return "Thermostat: " + (on ? "ON" : "OFF"); }
    @Override public Class<?> getType() { return SmartThermostat.class; }
}

class SmartSpeaker implements SmartDevice {
    private static final double WATTAGE = 5.0;
    private boolean on = false;

    @Override public void activate() { on = true; }
    @Override public void deactivate() { on = false; }
    @Override public double getPowerUsage() { return on ? WATTAGE : 0.0; }
    @Override public String getStatus() { return "Speaker: " + (on ? "Playing" : "Idle"); }
    @Override public Class<?> getType() { return SmartSpeaker.class; }
}

// =====================================================================
//  COMPOSITES — Room and Home
// =====================================================================

/**
 * Shared composite behavior for Room and Home. Cascading activate/deactivate
 * and summing power usage are implemented ONCE here, purely in terms of the
 * SmartDevice interface — no instanceof, no type checks, so it automatically
 * handles plain devices, upgraded devices, and nested sub-groups alike
 * (requirement 3: no special-casing).
 */
abstract class AbstractDeviceGroup implements DeviceGroup {
    private final String name;
    private final List<SmartDevice> children = new ArrayList<>();

    protected AbstractDeviceGroup(String name) {
        this.name = name;
    }

    protected void addChild(SmartDevice device) {
        children.add(device);
    }

    @Override
    public String getName() { return name; }

    @Override
    public List<SmartDevice> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void activate() {
        for (SmartDevice child : children) child.activate();
    }

    @Override
    public void deactivate() {
        for (SmartDevice child : children) child.deactivate();
    }

    @Override
    public double getPowerUsage() {
        double total = 0.0;
        for (SmartDevice child : children) total += child.getPowerUsage();
        return total;
    }

    @Override
    public String getStatus() {
        StringBuilder sb = new StringBuilder("[" + name + "]");
        for (SmartDevice child : children) {
            sb.append("\n  ").append(child.getStatus().replace("\n", "\n  "));
        }
        return sb.toString();
    }

    @Override
    public Class<?> getType() { return this.getClass(); }
}

class Room extends AbstractDeviceGroup {
    public Room(String name) { super(name); }

    /** Accepts any SmartDevice — plain, decorated, or a nested Room. */
    public void addDevice(SmartDevice device) { addChild(device); }
}

class Home extends AbstractDeviceGroup {
    public Home(String name) { super(name); }

    /** Accepts any SmartDevice — a plain Room, or a Room wrapped in upgrades. */
    public void addRoom(SmartDevice room) { addChild(room); }
}

// =====================================================================
//  DECORATOR BASE — device-level and group-level upgrades both build on this
// =====================================================================

/**
 * Base for every upgrade. By default it transparently forwards every call to
 * the wrapped device, so a concrete decorator only needs to override the
 * methods its specific upgrade actually changes. This is what makes stacking
 * upgrades (requirement 2) just a matter of nesting constructors.
 */
abstract class DeviceDecorator implements SmartDevice {
    protected final SmartDevice wrapped;

    protected DeviceDecorator(SmartDevice wrapped) {
        this.wrapped = wrapped;
    }

    @Override public void activate() { wrapped.activate(); }
    @Override public void deactivate() { wrapped.deactivate(); }
    @Override public double getPowerUsage() { return wrapped.getPowerUsage(); }
    @Override public String getStatus() { return wrapped.getStatus(); }
    @Override public Class<?> getType() { return wrapped.getType(); }
}

// =====================================================================
//  DEVICE-LEVEL UPGRADES
// =====================================================================

/**
 * PIN-protects any SmartDevice. A locked device ignores activate/deactivate,
 * but power reporting always passes through untouched — locking doesn't cut
 * power to something already running, it only blocks further control.
 * Starts locked by default; call unlock(pin) to release it.
 */
class AccessRestricted extends DeviceDecorator {
    private final int pin;
    private boolean locked = true;

    public AccessRestricted(SmartDevice device, int pin) {
        super(device);
        this.pin = pin;
    }

    public void unlock(int attempt) {
        if (attempt == pin) locked = false;
    }

    public void lock() { locked = true; }

    public boolean isLocked() { return locked; }

    @Override
    public void activate() {
        if (locked) return;
        wrapped.activate();
    }

    @Override
    public void deactivate() {
        if (locked) return;
        wrapped.deactivate();
    }

    @Override
    public String getStatus() {
        return wrapped.getStatus() + (locked ? " [LOCKED]" : "");
    }
    // getPowerUsage() intentionally NOT overridden — it always delegates
    // straight through, so a device already running keeps reporting power
    // even while locked.
}

/**
 * Adds an auto-shutoff timer to any SmartDevice. Activating starts the
 * countdown; simulateTimerExpiry() fires the auto-off; a manual deactivate()
 * cancels the pending timer.
 */
class TimerControlled extends DeviceDecorator {
    private final int timerSeconds;
    private boolean timerRunning = false;

    public TimerControlled(SmartDevice device, int timerSeconds) {
        super(device);
        this.timerSeconds = timerSeconds;
    }

    @Override
    public void activate() {
        wrapped.activate();
        // Only start the countdown if the device actually engaged — e.g. a
        // locked AccessRestricted device beneath this timer stays off, and
        // the timer shouldn't pretend to be counting down over nothing.
        // Checked via the interface (getPowerUsage), not an instanceof.
        timerRunning = wrapped.getPowerUsage() > 0;
    }

    @Override
    public void deactivate() {
        wrapped.deactivate();
        timerRunning = false;
    }

    /** Fires the auto-off. No-op if the timer isn't running (e.g. already off). */
    public void simulateTimerExpiry() {
        if (!timerRunning) return;
        wrapped.deactivate();
        timerRunning = false;
    }

    @Override
    public String getStatus() {
        return wrapped.getStatus() + (timerRunning ? " (auto-off in " + timerSeconds + "s)" : "");
    }
}

/**
 * Caps a device's reported power draw. The reduction is real (not cosmetic):
 * getPowerUsage() returns min(actual draw, cap).
 */
class PowerThrottled extends DeviceDecorator {
    private final double powerCap;

    public PowerThrottled(SmartDevice device, double powerCap) {
        super(device);
        this.powerCap = powerCap;
    }

    @Override
    public double getPowerUsage() {
        return Math.min(wrapped.getPowerUsage(), powerCap);
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

// =====================================================================
//  ROOM/HOME-LEVEL PREMIUM FEATURES
// =====================================================================

/**
 * Enforces a total power budget on a Room or Home. If the group activates
 * over budget, children are shed in reverse insertion order (most recently
 * added first) until it fits. This is an aggregate constraint, distinct from
 * PowerThrottled, which caps a single device's own draw.
 *
 * Constructor requires a DeviceGroup (not a bare SmartDevice), so applying
 * EcoMode to a lone SmartLight is a compile error, not a runtime surprise —
 * satisfying requirement 13's type-safety expectation.
 */
class EcoMode extends DeviceDecorator {
    private final DeviceGroup group;
    private final double budget;

    public EcoMode(DeviceGroup group, double budget) {
        super(group);
        this.group = group;
        this.budget = budget;
    }

    @Override
    public void activate() {
        wrapped.activate();
        shedUntilWithinBudget();
    }

    private void shedUntilWithinBudget() {
        List<SmartDevice> children = group.getChildren();
        for (int i = children.size() - 1; i >= 0 && getPowerUsage() > budget; i--) {
            children.get(i).deactivate();
        }
    }

    @Override
    public String getStatus() {
        return "[ECO: " + budget + "W budget]\n" + wrapped.getStatus();
    }
}

/**
 * Restricts a Room or Home to a set of allowed device types. Disallowed
 * devices are silently skipped on activate/deactivate, excluded from power
 * totals, and flagged in status output.
 *
 * The type check uses SmartDevice.getType(), which every decorator forwards
 * down to the real leaf type — so a TimerControlled SmartLight is still
 * correctly recognized as a light, with no instanceof chain required.
 */
class GuestMode extends DeviceDecorator {
    private final DeviceGroup group;
    private final Set<Class<?>> allowedTypes;

    public GuestMode(DeviceGroup group, Set<Class<?>> allowedTypes) {
        super(group);
        this.group = group;
        this.allowedTypes = allowedTypes;
    }

    @Override
    public void activate() {
        for (SmartDevice child : group.getChildren()) {
            if (allowedTypes.contains(child.getType())) child.activate();
        }
    }

    @Override
    public void deactivate() {
        for (SmartDevice child : group.getChildren()) {
            if (allowedTypes.contains(child.getType())) child.deactivate();
        }
    }

    @Override
    public double getPowerUsage() {
        double total = 0.0;
        for (SmartDevice child : group.getChildren()) {
            if (allowedTypes.contains(child.getType())) total += child.getPowerUsage();
        }
        return total;
    }

    @Override
    public String getStatus() {
        StringBuilder sb = new StringBuilder("[GUEST MODE]\n[" + group.getName() + "]");
        for (SmartDevice child : group.getChildren()) {
            sb.append("\n  ").append(child.getStatus());
            if (!allowedTypes.contains(child.getType())) {
                sb.append(" [guest-restricted]");
            }
        }
        return sb.toString();
    }
}
