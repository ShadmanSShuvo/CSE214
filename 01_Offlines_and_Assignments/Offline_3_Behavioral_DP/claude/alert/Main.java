/**
 * Demonstration driver for Task 1: BD Alert (Observer Design Pattern).
 *
 * Pattern justification:
 * Each AlertCategory acts as a Subject that maintains a list of Citizen
 * Observers. Citizens subscribe/unsubscribe from categories independently,
 * and whenever a new alert of a category is published, the Subject pushes
 * the update to exactly the observers currently attached to it -- which is
 * the textbook definition of the Observer pattern (one-to-many dependency,
 * automatic notification on state change, decoupled publisher/subscribers).
 */
public class Main {
    public static void main(String[] args) {
        BDAlertSystem bdAlert = new BDAlertSystem();

        System.out.println("---- Step 1: Register citizens ----");
        Citizen rahim = bdAlert.registerCitizen("Rahim");
        Citizen karim = bdAlert.registerCitizen("Karim");
        Citizen fatema = bdAlert.registerCitizen("Fatema");
        Citizen nusrat = bdAlert.registerCitizen("Nusrat");

        System.out.println("\n---- Step 2: Subscribe citizens to categories ----");
        bdAlert.subscribe(rahim, AlertCategory.EARTHQUAKE, AlertCategory.FLOOD);
        bdAlert.subscribe(karim, AlertCategory.FIRE);
        bdAlert.subscribe(fatema, AlertCategory.EARTHQUAKE, AlertCategory.FIRE);
        // Nusrat is registered but not subscribed to anything yet.

        System.out.println("\n---- Step 3: Publish initial alerts ----");
        bdAlert.publishAlert(new Alert(
                "6.4 Magnitude Earthquake", AlertCategory.EARTHQUAKE,
                "Chattogram", "High",
                "Move to open ground; avoid damaged buildings."));

        bdAlert.publishAlert(new Alert(
                "Flash Flood Warning", AlertCategory.FLOOD,
                "Sylhet", "Moderate",
                "Move belongings to higher ground; avoid riverbanks."));

        bdAlert.publishAlert(new Alert(
                "Market Fire Outbreak", AlertCategory.FIRE,
                "Dhaka - Mirpur", "Critical",
                "Evacuate immediately; do not use elevators."));

        System.out.println("---- Step 4: Nusrat subscribes AFTER the above alerts ----");
        bdAlert.subscribe(nusrat, AlertCategory.FLOOD);
        System.out.println("(Nusrat should NOT have received the Sylhet flood alert above,\n"
                + " since she subscribed only after it was published.)\n");

        System.out.println("---- Step 5: Update subscriptions (Karim unsubscribes from FIRE, subscribes to FLOOD) ----");
        bdAlert.unsubscribe(karim, AlertCategory.FIRE);
        bdAlert.subscribe(karim, AlertCategory.FLOOD);

        System.out.println("\n---- Step 6: Publish another round of alerts to verify updated subscriptions ----");
        bdAlert.publishAlert(new Alert(
                "Aftershock Alert", AlertCategory.EARTHQUAKE,
                "Chattogram", "Moderate",
                "Stay away from weakened structures."));

        bdAlert.publishAlert(new Alert(
                "River Overflow", AlertCategory.FLOOD,
                "Sirajganj", "High",
                "Evacuate low-lying areas immediately."));

        bdAlert.publishAlert(new Alert(
                "Warehouse Fire", AlertCategory.FIRE,
                "Narayanganj", "High",
                "Firefighting units dispatched; avoid the area."));

        System.out.println("---- Step 7: Display notification history for each citizen ----");
        rahim.displayNotifications();
        System.out.println();
        karim.displayNotifications();
        System.out.println();
        fatema.displayNotifications();
        System.out.println();
        nusrat.displayNotifications();
    }
}
