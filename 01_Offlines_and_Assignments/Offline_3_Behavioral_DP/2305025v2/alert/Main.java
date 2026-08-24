public class Main {
        public static void main(String[] args) {
                AlertSystem system = new AlertSystem();

                // 1. Instantiation is separated from registration
                Citizen rahim = new Citizen("Rahim");
                Citizen karim = new Citizen("Karim");
                Citizen fatema = new Citizen("Fatema");
                Citizen nusrat = new Citizen("Nusrat");

                system.register(rahim);
                system.register(karim);
                system.register(fatema);
                system.register(nusrat);

                // 2. Subscribe
                system.subscribe(rahim, AlertCategory.EARTHQUAKE, AlertCategory.FLOOD);
                system.subscribe(karim, AlertCategory.FIRE);
                system.subscribe(fatema, AlertCategory.EARTHQUAKE, AlertCategory.FIRE);

                // 3. Initial alerts
                System.out.println("\n--- Initial Alerts ---");
                system.publish(new Alert("6.4 Magnitude Earthquake", AlertCategory.EARTHQUAKE, "Chattogram", "High",
                                "Move to open ground."));
                system.publish(new Alert("Flash Flood Warning", AlertCategory.FLOOD, "Sylhet", "Moderate",
                                "Move to higher ground."));
                system.publish(new Alert("Market Fire", AlertCategory.FIRE, "Dhaka - Mirpur", "Critical",
                                "Evacuate immediately."));

                // 4. Nusrat subscribes after previous alerts
                System.out.println("\n--- Nusrat Subscribes to FLOOD ---");
                system.subscribe(nusrat, AlertCategory.FLOOD);

                // 5. Update Karim's subscription
                System.out.println("\n--- Karim Updates Subscription ---");
                system.unsubscribe(karim, AlertCategory.FIRE);
                system.subscribe(karim, AlertCategory.FLOOD);

                // 6. New alerts
                System.out.println("\n--- New Alerts ---");
                system.publish(new Alert("Aftershock Alert", AlertCategory.EARTHQUAKE, "Chattogram", "Moderate",
                                "Avoid damaged structures."));
                system.publish(new Alert("River Overflow", AlertCategory.FLOOD, "Sirajganj", "High",
                                "Evacuate low-lying areas."));
                system.publish(new Alert("Warehouse Fire", AlertCategory.FIRE, "Narayanganj", "High",
                                "Avoid the area."));

                // 7. Notification history printed using the new formatting method
                System.out.print(rahim.getFormattedAlertHistory());
                System.out.print(karim.getFormattedAlertHistory());
                System.out.print(fatema.getFormattedAlertHistory());
                System.out.print(nusrat.getFormattedAlertHistory());

                // 8. Safely unregistering a citizen entirely
                System.out.println("\n--- Unregistering Rahim ---");
                system.unregister(rahim);
        }
}
