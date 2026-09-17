import java.util.List;

public class FCFSPolicy implements SchedulingPolicy {
    @Override
    public String getPolicyName() {
        return "FCFS (First Come First Served)";
    }

    @Override
    public Task selectNextTask(List<Task> waitingQueue) {
        if (waitingQueue == null || waitingQueue.isEmpty())
            return null;

        Task selected = waitingQueue.get(0);
        for (int i = 1; i < waitingQueue.size(); i++) {
            Task current = waitingQueue.get(i);
            if (current.getStartTime() < selected.getStartTime()) {
                selected = current;
            }
        }
        return selected;
    }
}
