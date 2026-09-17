/**
 * Concrete Colleague: CommercialFlight
 * Models a passenger aircraft with passenger count and standard flight
 * protocols.
 */
public class CommercialFlight extends Aircraft {
    private final int passengerCount;

    public CommercialFlight(AirTrafficControlMediator mediator, String callSign, int altitude, boolean onGround,
            int passengerCount) {
        super(mediator, callSign, altitude, onGround);
        this.passengerCount = passengerCount;
    }

    @Override
    public void receiveMessage(String senderCallSign, String message) {
        System.out.println("  -> [" + callSign + " Cockpit] Advisory from " + senderCallSign + ": \"" + message + "\"");
    }

    @Override
    public void onClearanceGranted(String clearanceType) {
        if ("LANDING".equalsIgnoreCase(clearanceType)) {
            System.out.println("  -> [" + callSign + "] Touchdown confirmed with " + passengerCount
                    + " passengers aboard. Braking...");
            this.altitude = 0;
            this.onGround = true;
        } else if ("TAKEOFF".equalsIgnoreCase(clearanceType)) {
            System.out.println("  -> [" + callSign + "] Airborne! Climbing to cruising altitude with " + passengerCount
                    + " passengers.");
            this.altitude = 10000;
            this.onGround = false;
        }
    }

    public int getPassengerCount() {
        return passengerCount;
    }
}
