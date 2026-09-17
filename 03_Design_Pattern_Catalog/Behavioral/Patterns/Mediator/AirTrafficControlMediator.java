/**
 * Mediator Interface: AirTrafficControlMediator
 * Defines the contract for all air traffic coordination, runway management,
 * and inter-aircraft messaging without direct colleague references.
 */
public interface AirTrafficControlMediator {
    /**
     * Registers an aircraft with the air traffic control network.
     */
    void registerAircraft(Aircraft aircraft);

    /**
     * Requests clearance for landing on the designated runway.
     * 
     * @return true if clearance is immediately granted; false if queued.
     */
    boolean requestLanding(Aircraft aircraft);

    /**
     * Requests clearance for departure/takeoff.
     */
    boolean requestTakeoff(Aircraft aircraft);

    /**
     * Notifies the tower that an aircraft has completed landing/takeoff and cleared
     * the runway.
     */
    void vacateRunway(Aircraft aircraft);

    /**
     * Broadcasts an alert or advisory message from one aircraft (or tower) to all
     * other aircraft.
     */
    void broadcastMessage(Aircraft sender, String message);

    /**
     * Prints the current airport status (runway state, holding queue, departure
     * queue).
     */
    void printStatus();
}
