public class Patient {
    private final String id;
    private final String name;

    public Patient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void receiveUrgentNotification(String message) {
        System.out.println("URGENT notification sent to Patient " + id + ".");
    }

    public void receiveCompleteResults(String summary) {
        System.out.println("Complete results sent to Patient " + id + ".");
    }
}
