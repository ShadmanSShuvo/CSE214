/**
 * Abstract Colleague: Aircraft
 * Maintains a reference to the AirTrafficControlMediator.
 * Delegates all coordination, request for landing, takeoff, and inter-aircraft
 * alerts to the mediator.
 */
public abstract class Aircraft {
    protected final AirTrafficControlMediator mediator;
    protected final String callSign;
    protected int altitude;
    protected boolean onGround;

    public Aircraft(AirTrafficControlMediator mediator, String callSign, int altitude, boolean onGround) {
        this.mediator = mediator;
        this.callSign = callSign;
        this.altitude = altitude;
        this.onGround = onGround;
    }

    public void requestLanding() {
        System.out.println("\n[" + callSign + "] Requesting clearance to LAND (Altitude: " + altitude + "ft)...");
        mediator.requestLanding(this);
    }

    public void requestTakeoff() {
        System.out.println("\n[" + callSign + "] Requesting clearance for TAKEOFF...");
        mediator.requestTakeoff(this);
    }

    public void vacateRunway() {
        System.out.println("[" + callSign + "] Exiting active runway.");
        mediator.vacateRunway(this);
    }

    public void sendMessage(String message) {
        System.out.println("\n[" + callSign + " to Tower] Broadcasting: \"" + message + "\"");
        mediator.broadcastMessage(this, message);
    }

    /**
     * Called by the mediator to deliver messages or advisories.
     */
    public abstract void receiveMessage(String senderCallSign, String message);

    /**
     * Called by the mediator when clearance is granted.
     */
    public abstract void onClearanceGranted(String clearanceType);

    public String getCallSign() {
        return callSign;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean isEmergency() {
        return false;
    }

    @Override
    public String toString() {
        return callSign + " (" + getClass().getSimpleName() + ", Alt: " + altitude + "ft, " + (onGround ? "GND" : "AIR")
                + ")";
    }
}
