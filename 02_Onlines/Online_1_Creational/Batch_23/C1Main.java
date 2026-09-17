// ==========================================
// 1. THE SINGLETON AUDIT LOGGER CLASS
// ==========================================
class AuditLogger {
    // Private static variable to hold the single instance
    private static AuditLogger instance;

    // Private constructor to prevent direct instantiation from client classes
    private AuditLogger() {
        System.out.println("AuditLogger initialized for the first time.");
    }

    // Public static method to provide global access to the instance (Lazy Initialization)
    public static AuditLogger getInstance() {
        if (instance == null) {
            instance = new AuditLogger();
        }
        return instance;
    }

    // Example logging method
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}

// ==========================================
// 2. MODULES DEMONSTRATING ACCESS
// ==========================================
class StudentLoginModule {
    private AuditLogger logger;

    public StudentLoginModule() {
        // Requesting the logger instance
        this.logger = AuditLogger.getInstance();
    }

    public AuditLogger getLogger() {
        return this.logger;
    }

    public void login(String username) {
        logger.log("User '" + username + "' logged in successfully.");
    }
}

class QuestionManagementModule {
    private AuditLogger logger;

    public QuestionManagementModule() {
        // Requesting the logger instance
        this.logger = AuditLogger.getInstance();
    }

    public AuditLogger getLogger() {
        return this.logger;
    }

    public void addQuestion(String questionId) {
        logger.log("Question ID " + questionId + " was added to the exam.");
    }
}

// ==========================================
// 3. MAIN CLASS TO DEMONSTRATE VERIFICATION
// ==========================================
public class C1Main {
    public static void main(String[] args) {
        System.out.println("--- Starting Examination System ---");

        // Instantiate Module 1
        StudentLoginModule loginModule = new StudentLoginModule();
        loginModule.login("john_doe");

        System.out.println();

        // Instantiate Module 2
        QuestionManagementModule questionModule = new QuestionManagementModule();
        questionModule.addQuestion("Q101");

        System.out.println("\n--- Verifying Singleton Instance ---");
        
        // Retrieve the logger instances from both modules
        AuditLogger loggerFromLogin = loginModule.getLogger();
        AuditLogger loggerFromQuestions = questionModule.getLogger();

        // Verify if both references point to the exact same object
        if (loggerFromLogin == loggerFromQuestions) {
            System.out.println("SUCCESS: Both modules are using the EXACT SAME logger instance.");
            System.out.println("Module 1 Logger Hashcode: " + loggerFromLogin.hashCode());
            System.out.println("Module 2 Logger Hashcode: " + loggerFromQuestions.hashCode());
        } else {
            System.out.println("FAILURE: Different logger instances detected.");
        }
    }
}