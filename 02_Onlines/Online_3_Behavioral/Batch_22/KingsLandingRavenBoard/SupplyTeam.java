public class SupplyTeam implements KingdomObserver {
    private final String name;

    public SupplyTeam(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void onScrollDelivered(String scroll) {
        System.out.println("  [" + name + "]: Update inventory! Secure granaries and audit our wartime stockpile.");
    }
}
