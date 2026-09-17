/**
 * Domain Resource: Runway
 * Represents an airport runway managed exclusively by the Air Traffic Control
 * Tower.
 */
public class Runway {
    private final String name;
    private boolean occupied;
    private Aircraft currentAircraft;

    public Runway(String name) {
        this.name = name;
        this.occupied = false;
        this.currentAircraft = null;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void allocateTo(Aircraft aircraft) {
        this.occupied = true;
        this.currentAircraft = aircraft;
        System.out.println("[RUNWAY " + name + "] Allocated to " + aircraft.getCallSign());
    }

    public void vacate() {
        if (currentAircraft != null) {
            System.out.println("[RUNWAY " + name + "] Vacated by " + currentAircraft.getCallSign());
        }
        this.occupied = false;
        this.currentAircraft = null;
    }

    public String getName() {
        return name;
    }

    public Aircraft getCurrentAircraft() {
        return currentAircraft;
    }
}
