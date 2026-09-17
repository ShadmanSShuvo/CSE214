// singleton
class Logger {
    // Single static instance of the class
    private static Logger instance;

    // Private constructor to block instantation via 'new' from outside
    private Logger() {
        System.out.println("Logger initialized successfully.");
    }

    // Global access point to retrieve the single instance
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("[LOG ENTRY]: " + message);
    }
}

// Client validation
public class MainB1 {
    public static void main(String[] args) {
        // Simulating access from Part 1 of the application
        Logger loggerClient1 = Logger.getInstance();
        loggerClient1.log("User 'Shuvo' initiated a deposit of $500.");

        // Simulating access from Part 2 of the application
        Logger loggerClient2 = Logger.getInstance();
        loggerClient2.log("User 'Shuvo' successfully transferred $150.");

        // Verification check to prove they share the identical instance
        System.out.println("Are both loggers the same instance? -> " + (loggerClient1 == loggerClient2));
    }
}