// Singleton Class
class DatabasePool {

    // The single instance
    private static DatabasePool instance;

    // Private constructor - prevents object creation using 'new'
    private DatabasePool() {
        System.out.println("Connecting to database...");
    }

    // Global access point
    public static synchronized DatabasePool getInstance() {

        if (instance == null) {
            instance = new DatabasePool(); // created only once
        }

        return instance;
    }

    public void query(String sql) {
        System.out.println("Running: " + sql);
    }
}

// Client
public class SingletonDemo {

    public static void main(String[] args) {

        DatabasePool pool1 = DatabasePool.getInstance();
        DatabasePool pool2 = DatabasePool.getInstance();

        System.out.println(pool1 == pool2); // true

        pool1.query("SELECT * FROM users;");
        pool2.query("SELECT * FROM products;");
    }
}
