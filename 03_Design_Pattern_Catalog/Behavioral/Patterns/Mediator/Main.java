/**
 * Test Driver: Mediator Pattern Template Demonstration
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("          MEDIATOR DESIGN PATTERN TEMPLATE DEMO           ");
        System.out.println("==========================================================");

        // 1. Instantiate the Mediator (Airport Control Tower)
        AirportControlTower tower = new AirportControlTower("Metropolitan International (MET)", "09R/27L");

        // 2. Instantiate Colleagues with reference to Mediator
        CommercialFlight flightBA = new CommercialFlight(tower, "BA-402", 5000, false, 185);
        CommercialFlight flightUA = new CommercialFlight(tower, "UA-811", 7000, false, 240);
        CargoFlight cargoFedEx = new CargoFlight(tower, "FDX-99", 0, true, 42.5);
        RescueHelicopter medevac = new RescueHelicopter(tower, "MEDEVAC-1", 1500, false);

        // 3. Register aircraft with the mediator
        System.out.println("\n--- Section 1: Registering Colleagues with Mediator ---");
        tower.registerAircraft(flightBA);
        tower.registerAircraft(flightUA);
        tower.registerAircraft(cargoFedEx);
        tower.registerAircraft(medevac);

        // 4. Simultaneous Operations & Runway Contention
        System.out.println("\n--- Section 2: Traffic Coordination & Queueing ---");
        flightBA.requestLanding(); // Runway is free -> Granted immediately

        flightUA.requestLanding(); // Runway occupied -> Added to holding queue
        cargoFedEx.requestTakeoff(); // Runway occupied -> Added to departure queue

        tower.printStatus();

        // 5. Emergency Preemption
        System.out.println("--- Section 3: Emergency Handling & Priority Escalation ---");
        medevac.declareEmergency(true);
        medevac.requestLanding(); // Should jump to front of landing queue!

        tower.printStatus();

        // 6. Cascade Resolution via Vacating Runway
        System.out.println("--- Section 4: Clearing Runway & Cascading Clearances ---");
        System.out.println("\nFlight BA-402 finishes rollout and vacates runway:");
        flightBA.vacateRunway(); // Frees runway -> Emergency Medevac lands next!

        System.out.println("\nMEDEVAC-1 completes emergency transfer and clears runway:");
        medevac.vacateRunway(); // Frees runway -> Flight UA-811 lands next!

        System.out.println("\nFlight UA-811 taxiing to terminal gate:");
        flightUA.vacateRunway(); // Frees runway -> Cargo FDX-99 takes off!

        System.out.println("\nCargo FDX-99 departs airport airspace:");
        cargoFedEx.vacateRunway(); // Runway becomes idle

        tower.printStatus();

        // 7. Inter-Colleague Communication via Mediator
        System.out.println("--- Section 5: Decoupled Inter-Colleague Messaging ---");
        flightBA.sendMessage("Severe low-altitude wind shear detected on final approach at 1200ft.");

        System.out.println("\n==========================================================");
        System.out.println("          MEDIATOR PATTERN DEMO COMPLETED SUCCESSFULLY    ");
        System.out.println("==========================================================");
    }
}
