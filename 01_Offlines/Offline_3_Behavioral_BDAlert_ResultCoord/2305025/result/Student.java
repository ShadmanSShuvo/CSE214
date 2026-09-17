class Student extends Colleague {
    private final String id;
    private final String name;

    public Student(Coordinator coordinator, String id, String name) {
        super(coordinator);
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void notify(String msg) {
        System.out.println("[Student " + name + "] " + msg);
    }

    public void checkStatus() {
        coordinator.status(id);
    }
}
