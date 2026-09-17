import java.util.List;

public class SJFPolicy implements SchedulingPolicy {
    @Override
    public String getPolicyName() {
        return "SJF (Shortest Job First)";
    }

    @Override
    public Task selectNextTask(List<Task> waitingQueue) {
        if (waitingQueue == null || waitingQueue.isEmpty())
            return null;

        Task selected = waitingQueue.get(0);
        for (int i = 1; i < waitingQueue.size(); i++) {
            Task current = waitingQueue.get(i);
            // Smallest execution time first
            if (current.getExecutionTime() < selected.getExecutionTime()) {
                selected = current;
            } else if (current.getExecutionTime() == selected.getExecutionTime()) {
                // Tie breaker: earliest start time
                if (current.getStartTime() < selected.getStartTime()) {
                    selected = current;
                }
            }
        }
        return selected;
    }
}
