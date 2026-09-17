public class Main {
    public static void main(String[] args) {
        AlertSystem system = new AlertSystem();

        Citizen rahim = new Citizen("Rahim");
        Citizen karim = new Citizen("Karim");
        Citizen fatema = new Citizen("Fatema");
        Citizen nusrat = new Citizen("Nusrat");

        system.register(rahim);
        system.register(karim);
        system.register(fatema);
        system.register(nusrat);

        system.subscribe(rahim, AlertCategory.EARTHQUAKE, AlertCategory.FLOOD);
        system.subscribe(karim, AlertCategory.FIRE);
        system.subscribe(fatema, AlertCategory.EARTHQUAKE, AlertCategory.FIRE);

        System.out.println("\n--- Initial Alerts ---");
        system.publish(new Alert("6.4 Magnitude Earthquake", AlertCategory.EARTHQUAKE, "Chattogram", "High",
                "Move to open ground."));
        system.publish(new Alert("Flash Flood Warning", AlertCategory.FLOOD, "Sylhet", "Moderate",
                "Move to higher ground."));
        system.publish(new Alert("Market Fire", AlertCategory.FIRE, "Dhaka - Mirpur", "Critical",
                "Evacuate immediately."));

        System.out.println("\n--- Nusrat Subscribes to FLOOD ---");
        system.subscribe(nusrat, AlertCategory.FLOOD);

        System.out.println("\n--- Karim Updates Subscription ---");
        system.unsubscribe(karim, AlertCategory.FIRE);
        system.subscribe(karim, AlertCategory.FLOOD);

        System.out.println("\n--- New Alerts ---");
        system.publish(new Alert("Aftershock Alert", AlertCategory.EARTHQUAKE, "Chattogram", "Moderate",
                "Avoid damaged structures."));
        system.publish(new Alert("River Overflow", AlertCategory.FLOOD, "Sirajganj", "High",
                "Evacuate low-lying areas."));
        system.publish(new Alert("Warehouse Fire", AlertCategory.FIRE, "Narayanganj", "High",
                "Avoid the area."));

        System.out.print(rahim.getFormattedAlertHistory());
        System.out.print(karim.getFormattedAlertHistory());
        System.out.print(fatema.getFormattedAlertHistory());
        System.out.print(nusrat.getFormattedAlertHistory());

        System.out.println("\n--- Unregistering Rahim ---");
        system.unregister(rahim);
    }
}
