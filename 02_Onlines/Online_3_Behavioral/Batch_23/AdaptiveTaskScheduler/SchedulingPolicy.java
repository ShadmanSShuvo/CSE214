import java.util.List;

public interface SchedulingPolicy {
    String getPolicyName();
    Task selectNextTask(List<Task> waitingQueue);
}
