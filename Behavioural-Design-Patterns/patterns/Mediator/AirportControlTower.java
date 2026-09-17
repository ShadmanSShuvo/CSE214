import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Concrete Mediator: AirportControlTower
 * Central hub coordinating runway allocation, queuing, priority emergency
 * handling,
 * and dispatching advisories across all aircraft.
 */
public class AirportControlTower implements AirTrafficControlMediator {
    private final String airportName;
    private final Runway runway;
    private final List<Aircraft> registeredAircraft;
    private final Deque<Aircraft> landingQueue;
    private final Deque<Aircraft> takeoffQueue;

    public AirportControlTower(String airportName, String runwayName) {
        this.airportName = airportName;
        this.runway = new Runway(runwayName);
        this.registeredAircraft = new ArrayList<>();
        this.landingQueue = new ArrayDeque<>();
        this.takeoffQueue = new ArrayDeque<>();
    }

    @Override
    public void registerAircraft(Aircraft aircraft) {
        if (!registeredAircraft.contains(aircraft)) {
            registeredAircraft.add(aircraft);
            System.out.println("[TOWER " + airportName + "] Registered " + aircraft);
        }
    }

    @Override
    public boolean requestLanding(Aircraft aircraft) {
        if (aircraft.isEmergency()) {
            System.out.println("[TOWER] ⚠️ EMERGENCY LANDING REQUEST from " + aircraft.getCallSign() + "!");
            if (!runway.isOccupied()) {
                grantLanding(aircraft);
                return true;
            } else {
                System.out.println("[TOWER] Runway currently occupied by " + runway.getCurrentAircraft().getCallSign()
                        + ". Placing emergency aircraft " + aircraft.getCallSign() + " at front of holding pattern!");
                landingQueue.addFirst(aircraft); // Emergency gets top priority
                return false;
            }
        }

        if (!runway.isOccupied() && landingQueue.isEmpty()) {
            grantLanding(aircraft);
            return true;
        } else {
            System.out.println("[TOWER] Runway busy. Instructing " + aircraft.getCallSign()
                    + " to enter holding pattern (Queue position: #" + (landingQueue.size() + 1) + ").");
            landingQueue.addLast(aircraft);
            return false;
        }
    }

    @Override
    public boolean requestTakeoff(Aircraft aircraft) {
        if (!runway.isOccupied() && landingQueue.isEmpty() && takeoffQueue.isEmpty()) {
            grantTakeoff(aircraft);
            return true;
        } else {
            System.out.println("[TOWER] Runway busy or inbound traffic arriving. Queuing " + aircraft.getCallSign()
                    + " for departure (Queue position: #" + (takeoffQueue.size() + 1) + ").");
            takeoffQueue.addLast(aircraft);
            return false;
        }
    }

    @Override
    public void vacateRunway(Aircraft aircraft) {
        if (runway.getCurrentAircraft() == aircraft) {
            runway.vacate();
            processNextInQueue();
        } else {
            System.out.println(
                    "[TOWER WARNING] " + aircraft.getCallSign() + " attempted to vacate runway, but was not on it.");
        }
    }

    @Override
    public void broadcastMessage(Aircraft sender, String message) {
        System.out.println("[TOWER BROADCAST] Advisory originated from " + sender.getCallSign() + ":");
        for (Aircraft aircraft : registeredAircraft) {
            if (aircraft != sender) {
                aircraft.receiveMessage(sender.getCallSign(), message);
            }
        }
    }

    private void grantLanding(Aircraft aircraft) {
        runway.allocateTo(aircraft);
        System.out.println("[TOWER] Clearance GRANTED to LAND: " + aircraft.getCallSign());
        aircraft.onClearanceGranted("LANDING");
    }

    private void grantTakeoff(Aircraft aircraft) {
        runway.allocateTo(aircraft);
        System.out.println("[TOWER] Clearance GRANTED for TAKEOFF: " + aircraft.getCallSign());
        aircraft.onClearanceGranted("TAKEOFF");
    }

    private void processNextInQueue() {
        // Priority rule: Landing traffic has precedence over takeoff traffic
        if (!landingQueue.isEmpty()) {
            Aircraft nextLanding = landingQueue.poll();
            System.out.println("\n[TOWER] Calling next aircraft from holding pattern: " + nextLanding.getCallSign());
            grantLanding(nextLanding);
        } else if (!takeoffQueue.isEmpty()) {
            Aircraft nextTakeoff = takeoffQueue.poll();
            System.out.println(
                    "\n[TOWER] Calling next aircraft from taxiway for departure: " + nextTakeoff.getCallSign());
            grantTakeoff(nextTakeoff);
        } else {
            System.out.println("[TOWER] Runway " + runway.getName() + " is now IDLE and clear.");
        }
    }

    @Override
    public void printStatus() {
        System.out.println("\n================ AIRPORT STATUS ================");
        System.out.println("Airport: " + airportName + " | Active Runway: " + runway.getName());
        System.out.println("Runway Status: "
                + (runway.isOccupied() ? "OCCUPIED by " + runway.getCurrentAircraft().getCallSign() : "AVAILABLE"));
        System.out.println("Inbound Holding Queue: " + landingQueue.size() + " aircraft");
        for (Aircraft a : landingQueue) {
            System.out.println("  - " + a);
        }
        System.out.println("Departure Taxi Queue:  " + takeoffQueue.size() + " aircraft");
        for (Aircraft a : takeoffQueue) {
            System.out.println("  - " + a);
        }
        System.out.println("================================================\n");
    }
}
