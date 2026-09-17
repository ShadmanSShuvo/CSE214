public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("   ADAPTIVE TASK SCHEDULER (STRATEGY PATTERN)             ");
        System.out.println("==========================================================");

        // User sets preferred policy to FCFS
        TaskScheduler scheduler = new TaskScheduler(new FCFSPolicy());

        // Add tasks according to the example scenario in the specification:
        // Task Start End Priority Execution Time
        // T1 0 8 MEDIUM 8
        // T2 1 4 LOW 3
        // T3 2 4 MEDIUM 2
        // T4 3 4 LOW 1
        // T5 4 9 HIGH 5
        System.out.println("\n--- Adding Tasks to Queue ---");
        scheduler.addTask(new Task("T1", 0, 8, Priority.MEDIUM));
        scheduler.addTask(new Task("T2", 1, 4, Priority.LOW));
        scheduler.addTask(new Task("T3", 2, 4, Priority.MEDIUM));
        scheduler.addTask(new Task("T4", 3, 4, Priority.LOW));
        scheduler.addTask(new Task("T5", 4, 9, Priority.HIGH));

        // Execute all tasks adaptively
        scheduler.executeAll();

        System.out.println("Adaptive Task Scheduler demonstration completed successfully.");
    }
}
