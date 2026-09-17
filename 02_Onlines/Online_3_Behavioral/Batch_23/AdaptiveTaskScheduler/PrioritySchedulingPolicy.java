import java.util.List;

public class PrioritySchedulingPolicy implements SchedulingPolicy {
    @Override
    public String getPolicyName() {
        return "Priority Scheduling";
    }

    @Override
    public Task selectNextTask(List<Task> waitingQueue) {
        if (waitingQueue == null || waitingQueue.isEmpty())
            return null;

        Task selected = waitingQueue.get(0);
        for (int i = 1; i < waitingQueue.size(); i++) {
            Task current = waitingQueue.get(i);
            // Higher priority level first (HIGH > MEDIUM > LOW)
            if (current.getPriority().getLevel() > selected.getPriority().getLevel()) {
                selected = current;
            } else if (current.getPriority().getLevel() == selected.getPriority().getLevel()) {
                // Tie breaker: earliest start time
                if (current.getStartTime() < selected.getStartTime()) {
                    selected = current;
                }
            }
        }
        return selected;
    }
}
