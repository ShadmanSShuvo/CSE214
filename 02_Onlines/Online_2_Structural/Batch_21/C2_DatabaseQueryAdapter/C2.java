interface DatabaseQuery {
    void executeQuery(String query);
}

class SQLDatabase implements DatabaseQuery {
    @Override
    public void executeQuery(String sqlQuery) {
        System.out.println("Executing SQL query: " + sqlQuery);
    }
}

// Adaptee: incompatible interface
class NoSQLDatabase {
    public void runQuery(String noSQLQuery) {
        System.out.println("Executing NoSQL query: " + noSQLQuery);
    }
}

// Adapter: makes NoSQLDatabase conform to DatabaseQuery
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

public class C2 {
    public static void main(String[] args) {
        DatabaseQuery sqlDb = new SQLDatabase();
        sqlDb.executeQuery("SELECT * FROM users");

        NoSQLDatabase noSQLDatabase = new NoSQLDatabase();
        DatabaseQuery noSqlAdapter = new NoSQLDatabaseAdapter(noSQLDatabase);
        noSqlAdapter.executeQuery("{ find: 'users', filter: {} }");

        // Legacy code can treat both uniformly through DatabaseQuery
        DatabaseQuery[] databases = { sqlDb, noSqlAdapter };
        for (DatabaseQuery db : databases) {
            db.executeQuery("sample query");
        }
    }
}
