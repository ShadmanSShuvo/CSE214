/**
 * Concrete Colleague: RescueHelicopter
 * High-priority emergency medical / search-and-rescue vehicle that preempts
 * normal queues.
 */
public class RescueHelicopter extends Aircraft {
    private boolean emergency;

    public RescueHelicopter(AirTrafficControlMediator mediator, String callSign, int altitude, boolean onGround) {
        super(mediator, callSign, altitude, onGround);
        this.emergency = false;
    }

    public void declareEmergency(boolean emergency) {
        this.emergency = emergency;
        if (emergency) {
            System.out.println("🚨 [" + callSign + "] MAYDAY! EMERGENCY DECLARED! Critical patient aboard!");
        }
    }

    @Override
    public boolean isEmergency() {
        return emergency;
    }

    @Override
    public void receiveMessage(String senderCallSign, String message) {
        System.out.println("  -> [" + callSign + " Radio] Dispatch from " + senderCallSign + ": \"" + message + "\"");
    }

    @Override
    public void onClearanceGranted(String clearanceType) {
        if ("LANDING".equalsIgnoreCase(clearanceType)) {
            System.out.println("  -> [" + callSign + "] Direct touchdown on helipad/runway. Medical team standing by.");
            this.altitude = 0;
            this.onGround = true;
            this.emergency = false;
        } else if ("TAKEOFF".equalsIgnoreCase(clearanceType)) {
            System.out.println("  -> [" + callSign + "] Immediate vertical ascent. Departing airspace.");
            this.altitude = 2000;
            this.onGround = false;
        }
    }
}
