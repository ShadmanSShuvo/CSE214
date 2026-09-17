public abstract class Device {
    protected final String name;
    protected HomeHub hub;

    public Device(String name) {
        this.name = name;
    }

    public void setHub(HomeHub hub) {
        this.hub = hub;
    }

    public String getName() {
        return name;
    }
}
