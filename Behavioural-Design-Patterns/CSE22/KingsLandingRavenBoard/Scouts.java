public class Scouts implements KingdomObserver {
    private final String name;

    public Scouts(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void onScrollDelivered(String scroll) {
        System.out.println("  [" + name + "]: Dispatch riders! Saddle horses and reconnoiter the reported area immediately.");
    }
}
