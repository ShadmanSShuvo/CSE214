// B1: Banking Application - Singleton Pattern
// Task: Ensure only one Logger instance exists across the whole application.

import java.util.ArrayList;
import java.util.List;

// ---------- Singleton ----------
class Logger {
    // The single, shared instance
    private static Logger instance;

    private List<String> logRecords = new ArrayList<>();

    // Private constructor prevents external instantiation
    private Logger() {
        System.out.println("Logger instance created.");
    }

    // Global access point
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        logRecords.add(message);
        System.out.println("[LOG] " + message);
    }

    public void printAllLogs() {
        System.out.println("---- Full Audit Trail ----");
        for (String record : logRecords) {
            System.out.println(record);
        }
    }
}

// ---------- Client Modules ----------
class DepositModule {
    public void makeDeposit(double amount) {
        Logger logger = Logger.getInstance();
        logger.log("Deposit of $" + amount + " processed.");
    }
}

class WithdrawalModule {
    public void makeWithdrawal(double amount) {
        Logger logger = Logger.getInstance();
        logger.log("Withdrawal of $" + amount + " processed.");
    }
}

// ---------- Client ----------
public class B1_LoggerSingleton {
    public static void main(String[] args) {
        DepositModule deposit = new DepositModule();
        WithdrawalModule withdrawal = new WithdrawalModule();

        deposit.makeDeposit(500.0);
        withdrawal.makeWithdrawal(200.0);

        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        // Prove both modules and both references share the same instance
        System.out.println("logger1 == logger2: " + (logger1 == logger2));

        logger1.printAllLogs();
    }
}
