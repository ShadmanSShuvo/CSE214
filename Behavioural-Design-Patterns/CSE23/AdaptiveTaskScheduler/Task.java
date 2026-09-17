public class Task {
    private final String id;
    private final int startTime;
    private final int endTime;
    private final Priority priority;

    public Task(String id, int startTime, int endTime, Priority priority) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public Priority getPriority() {
        return priority;
    }

    public int getExecutionTime() {
        return endTime - startTime;
    }

    @Override
    public String toString() {
        return String.format("[%s: Start=%d, End=%d, ExecTime=%d, Priority=%s]",
                id, startTime, endTime, getExecutionTime(), priority);
    }
}
