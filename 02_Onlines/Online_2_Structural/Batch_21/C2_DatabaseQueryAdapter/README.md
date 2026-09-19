# Batch 21 Online 2 (C2) - SQL / NoSQL Database Query (Adapter Pattern)

## Problem Statement
A legacy system uses an interface that only supports SQL queries:
```java
interface DatabaseQuery {
    void executeQuery(String query);
}

class SQLDatabase implements DatabaseQuery {
    @Override
    public void executeQuery(String sqlQuery) {
        System.out.println("Executing SQL query: " + sqlQuery);
    }
}
```
A new requirement necessitates the use of NoSQL databases for better scalability and flexibility:
```java
class NoSQLDatabase {
    public void runQuery(String noSQLQuery) {
        System.out.println("Executing NoSQL query: " + noSQLQuery);
    }
}
```
**Challenge**: Integrate the new NoSQL database **without modifying existing SQL-based code**. Provide a solution using an appropriate design pattern to seamlessly integrate `NoSQLDatabase`.

---

## Design Pattern Analysis

### Pattern Applied: **Adapter Pattern (Object Adapter)**

### Why Adapter?
- **Incompatible Method Names & Interfaces**: `DatabaseQuery` expects `executeQuery(String query)`, whereas `NoSQLDatabase` provides `runQuery(String query)`.
- **Preserving Client Code**: The legacy application code operates against `DatabaseQuery` collections.
- **The Adapter**: `NoSQLDatabaseAdapter` implements `DatabaseQuery`, wraps an instance of `NoSQLDatabase`, and translates `executeQuery(...)` into `noSQLDatabase.runQuery(...)`.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Target Interface** | `DatabaseQuery` | Expected interface (`executeQuery(String)`). |
| **Adaptee** | `NoSQLDatabase` | Class with incompatible method `runQuery(String)`. |
| **Adapter** | `NoSQLDatabaseAdapter` | Implements `DatabaseQuery`, wraps `NoSQLDatabase`, translates call. |
| **Client** | `C2` | Uses `DatabaseQuery` polymorphic reference to execute queries on both SQL and NoSQL databases uniformly. |

---

## Class Architecture

```
          <<interface>>
          DatabaseQuery
       +executeQuery(query)
          ^           ^
          |           |
    SQLDatabase   NoSQLDatabaseAdapter  ------>  NoSQLDatabase (Adaptee)
                  -noSqlDb: NoSQLDatabase        +runQuery(noSQLQuery)
                  +executeQuery(query)
```

---

## Solution Walkthrough

1. **Adapter Class**:
   ```java
   class NoSQLDatabaseAdapter implements DatabaseQuery {
       private NoSQLDatabase noSQLDatabase;

       public NoSQLDatabaseAdapter(NoSQLDatabase noSQLDatabase) {
           this.noSQLDatabase = noSQLDatabase;
       }

       @Override
       public void executeQuery(String query) {
           noSQLDatabase.runQuery(query);
       }
   }
   ```
2. **Polymorphic Execution in Driver**:
   ```java
   DatabaseQuery sqlDb = new SQLDatabase();
   DatabaseQuery noSqlDb = new NoSQLDatabaseAdapter(new NoSQLDatabase());

   DatabaseQuery[] databases = { sqlDb, noSqlDb };
   for (DatabaseQuery db : databases) {
       db.executeQuery("sample query");
   }
   ```

---

## How to Compile & Run

```bash
cd Batch_21/C2_DatabaseQueryAdapter
javac *.java
java C2
```

### Output
```
Executing SQL query: SELECT * FROM users
Executing NoSQL query: { find: 'users', filter: {} }
Executing SQL query: sample query
Executing NoSQL query: sample query
```
