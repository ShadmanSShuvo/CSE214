public class AutomaticBlinds extends Device {
    private boolean isClosed;

    public AutomaticBlinds(String name) {
        super(name);
        this.isClosed = false;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void close() {
        if (!isClosed) {
            this.isClosed = true;
            System.out.println("[" + name + "] Motor active: Closing the window blinds completely.");
            System.out.println("[" + name + "] Blinds are now CLOSED. Notifying Central Hub...");
            if (hub != null) {
                hub.notify(this, "BLINDS_CLOSED");
            }
        } else {
            System.out.println("[" + name + "] Blinds are already closed.");
        }
    }

    public void open() {
        this.isClosed = false;
        System.out.println("[" + name + "] Motor active: Opening the window blinds.");
    }
}
