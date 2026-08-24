public class Main {
    public static void main(String[] args) {
        AlertSystem system = new AlertSystem();

        // Register
        Citizen rahim = system.register("Rahim");
        Citizen karim = system.register("Karim");
        Citizen fatema = system.register("Fatema");
        Citizen nusrat = system.register("Nusrat");

        // Subscribe
        system.subscribe(
                rahim,
                AlertCategory.EARTHQUAKE,
                AlertCategory.FLOOD);

        system.subscribe(karim, AlertCategory.FIRE);

        system.subscribe(
                fatema,
                AlertCategory.EARTHQUAKE,
                AlertCategory.FIRE);

        // Initial alerts
        System.out.println("\n--- Initial Alerts ---");

        system.publish(new Alert(
                "6.4 Magnitude Earthquake",
                AlertCategory.EARTHQUAKE,
                "Chattogram",
                "High",
                "Move to open ground."));

        system.publish(new Alert(
                "Flash Flood Warning",
                AlertCategory.FLOOD,
                "Sylhet",
                "Moderate",
                "Move to higher ground."));

        system.publish(new Alert(
                "Market Fire",
                AlertCategory.FIRE,
                "Dhaka - Mirpur",
                "Critical",
                "Evacuate immediately."));

        // Nusrat subscribes after previous alerts
        System.out.println("\n--- Nusrat Subscribes to FLOOD ---");

        system.subscribe(nusrat, AlertCategory.FLOOD);

        // Update Karim's subscription
        System.out.println("\n--- Karim Updates Subscription ---");

        system.unsubscribe(karim, AlertCategory.FIRE);
        system.subscribe(karim, AlertCategory.FLOOD);

        // New alerts
        System.out.println("\n--- New Alerts ---");

        system.publish(new Alert(
                "Aftershock Alert",
                AlertCategory.EARTHQUAKE,
                "Chattogram",
                "Moderate",
                "Avoid damaged structures."));

        system.publish(new Alert(
                "River Overflow",
                AlertCategory.FLOOD,
                "Sirajganj",
                "High",
                "Evacuate low-lying areas."));

        system.publish(new Alert(
                "Warehouse Fire",
                AlertCategory.FIRE,
                "Narayanganj",
                "High",
                "Avoid the area."));

        // Notification history
        rahim.showAlerts();
        karim.showAlerts();
        fatema.showAlerts();
        nusrat.showAlerts();
    }
}
