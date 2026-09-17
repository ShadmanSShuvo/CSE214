public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("   KING'S LANDING RAVEN BOARD (OBSERVER PATTERN)          ");
        System.out.println("==========================================================");

        RavenBoard ravenBoard = new RavenBoard();

        KingdomObserver commander = new Commander("Lord Commander");
        KingdomObserver scouts = new Scouts("Kingswood Scouts");
        KingdomObserver supplyTeam = new SupplyTeam("Royal Quartermaster");

        // Initial subscriptions
        System.out.println("\n--- Step 1: Initial Observers Subscribing ---");
        ravenBoard.subscribe(commander);
        ravenBoard.subscribe(scouts);
        ravenBoard.subscribe(supplyTeam);

        // Scroll 1: Enemy spotted near the river
        ravenBoard.deliverScroll("Enemy spotted near the river");

        // Runtime dynamic change: Scouts leave the war room (unsubscribe)
        System.out.println("\n--- Step 2: Runtime Unsubscription ---");
        ravenBoard.unsubscribe(scouts);

        // Scroll 2: Winter supplies running low
        ravenBoard.deliverScroll("Winter supplies running low");

        // Runtime dynamic change: Scouts return (resubscribe)
        System.out.println("\n--- Step 3: Runtime Resubscription ---");
        ravenBoard.subscribe(scouts);

        // Scroll 3: Ships seen in the east
        ravenBoard.deliverScroll("Ships seen in the east");

        System.out.println("\n==========================================================");
        System.out.println("   Raven Board demonstration completed successfully.      ");
        System.out.println("==========================================================");
    }
}
