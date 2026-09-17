/**
 * Concrete Colleague: CargoFlight
 * Models a freight transport aircraft with heavy tonnage considerations.
 */
public class CargoFlight extends Aircraft {
    private final double cargoWeightTons;

    public CargoFlight(AirTrafficControlMediator mediator, String callSign, int altitude, boolean onGround,
            double cargoWeightTons) {
        super(mediator, callSign, altitude, onGround);
        this.cargoWeightTons = cargoWeightTons;
    }

    @Override
    public void receiveMessage(String senderCallSign, String message) {
        System.out
                .println("  -> [" + callSign + " Cargo Deck] Notice from " + senderCallSign + ": \"" + message + "\"");
    }

    @Override
    public void onClearanceGranted(String clearanceType) {
        if ("LANDING".equalsIgnoreCase(clearanceType)) {
            System.out.println("  -> [" + callSign + "] Heavy landing (" + cargoWeightTons
                    + " tons) complete. Taxiing to cargo terminal.");
            this.altitude = 0;
            this.onGround = true;
        } else if ("TAKEOFF".equalsIgnoreCase(clearanceType)) {
            System.out.println("  -> [" + callSign + "] Heavy rotation! Takeoff successful (" + cargoWeightTons
                    + " tons payload).");
            this.altitude = 8000;
            this.onGround = false;
        }
    }

    public double getCargoWeightTons() {
        return cargoWeightTons;
    }
}
