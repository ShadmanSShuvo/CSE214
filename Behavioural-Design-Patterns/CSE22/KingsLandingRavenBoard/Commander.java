public class Commander implements KingdomObserver {
    private final String name;

    public Commander(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void onScrollDelivered(String scroll) {
        System.out.println("  [" + name + "]: Man the walls and prepare our city defenses! Immediate tactical readiness ordered.");
    }
}
