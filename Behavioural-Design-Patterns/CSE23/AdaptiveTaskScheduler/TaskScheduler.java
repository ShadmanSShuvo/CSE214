import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskScheduler {
    private final List<Task> queue;
    private SchedulingPolicy preferredPolicy;

    // Available policy strategies
    private final SchedulingPolicy fcfsPolicy = new FCFSPolicy();
    private final SchedulingPolicy priorityPolicy = new PrioritySchedulingPolicy();
    private final SchedulingPolicy sjfPolicy = new SJFPolicy();

    public TaskScheduler(SchedulingPolicy preferredPolicy) {
        this.preferredPolicy = preferredPolicy;
        this.queue = new ArrayList<>();
        System.out.println("[Scheduler Initialized] Preferred Policy: " + preferredPolicy.getPolicyName());
    }

    public void addTask(Task task) {
        queue.add(task);
        System.out.println("  + Added Task: " + task);
    }

    public void setPreferredPolicy(SchedulingPolicy preferredPolicy) {
        System.out.println("[Policy Update] Preferred policy changed to: " + preferredPolicy.getPolicyName());
        this.preferredPolicy = preferredPolicy;
    }

    public SchedulingPolicy getPreferredPolicy() {
        return preferredPolicy;
    }

    public List<Task> getWaitingQueue() {
        return Collections.unmodifiableList(queue);
    }

    public SchedulingPolicy determineEffectivePolicy() {
        if (queue.isEmpty()) {
            return preferredPolicy;
        }

        // Rule 1: Urgent Workload
        // If at least one waiting task has HIGH priority, use Priority Scheduling
        for (Task t : queue) {
            if (t.getPriority() == Priority.HIGH) {
                return priorityPolicy;
            }
        }

        // Rule 2: Short-Task Workload
        // If no HIGH priority, but at least 3 tasks have execution time <= 3, use SJF
        int shortTaskCount = 0;
        for (Task t : queue) {
            if (t.getExecutionTime() <= 3) {
                shortTaskCount++;
            }
        }
        if (shortTaskCount >= 3) {
            return sjfPolicy;
        }

        // Rule 3: Normal Workload
        // Use preferred policy
        return preferredPolicy;
    }

    public Task executeNextTask() {
        if (queue.isEmpty()) {
            System.out.println("[Scheduler] No tasks waiting in the queue.");
            return null;
        }

        SchedulingPolicy activePolicy = determineEffectivePolicy();
        Task selected = activePolicy.selectNextTask(queue);
        queue.remove(selected);

        System.out.printf("[DISPATCH] Selected Task: %s | Policy Used: %s | Remaining in queue: %d\n",
                selected.getId(), activePolicy.getPolicyName(), queue.size());
        return selected;
    }

    public void executeAll() {
        System.out.println("\n>>> Starting executeAll() queue processing <<<");
        int step = 1;
        while (!queue.isEmpty()) {
            System.out.printf("Step %d: ", step++);
            executeNextTask();
        }
        System.out.println(">>> Queue is now empty. All tasks executed. <<<\n");
    }
}
