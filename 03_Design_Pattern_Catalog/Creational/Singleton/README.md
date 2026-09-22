# Singleton Pattern

## 📌 Intent
Ensure that a class has only one instance, while providing a global access point to this instance.

---

## 🏗️ Architecture & Class Structure

```
                  +--------------------------------+
                  |         DatabasePool           |
                  +--------------------------------+
                  | - instance: DatabasePool       |
                  | - DatabasePool() [private]     |
                  | + getInstance(): DatabasePool  |
                  | + query(sql: String): void     |
                  +--------------------------------+
```

### Key Elements:
1. **Private Constructor:** Suppresses default public constructor, preventing direct instantiation via `new`.
2. **Static Field:** Holds the sole instance of the class.
3. **Static Accessor (`getInstance()`):** Provides global access and creates the instance on first access if not already created.

---

## 🛠️ Java Implementation Idioms

### 1. Lazy Initialization with Synchronization (Used in `SingletonDemo.java`)
```java
public class DatabasePool {
    private static DatabasePool instance;

    private DatabasePool() {}

    public static synchronized DatabasePool getInstance() {
        if (instance == null) {
            instance = new DatabasePool();
        }
        return instance;
    }
}
```

### 2. Double-Checked Locking (High Concurrency)
```java
public class DoubleCheckedSingleton {
    private static volatile DoubleCheckedSingleton instance;

    private DoubleCheckedSingleton() {}

    public static DoubleCheckedSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckedSingleton();
                }
            }
        }
        return instance;
    }
}
```

### 3. Bill Pugh Singleton (Thread-safe without synchronization overhead)
```java
public class BillPughSingleton {
    private BillPughSingleton() {}

    private static class Helper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return Helper.INSTANCE;
    }
}
```

### 4. Enum Singleton (Effective Java Best Practice)
```java
public enum EnumSingleton {
    INSTANCE;
    public void executeQuery(String sql) { /* ... */ }
}
```

---

## 🚀 How to Compile & Run
```bash
javac SingletonDemo.java
java SingletonDemo
```
