package com.napier.sem;

import java.sql.*;

/**
 * Manages database connections and query execution
 */
public class DatabaseConnection {

    private Connection con = null;

    // Database configuration defaults
    private static final String DB_LOCAL = "jdbc:mysql://localhost:33060/world?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DB_DOCKER = "jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DB_USER_DEFAULT = "root";
    private static final String DB_PASSWORD_DEFAULT = "example";

    private String buildUrl(boolean useLocal) {
        String host = System.getenv().getOrDefault("MYSQL_HOST", useLocal ? "localhost" : "db");
        String port = System.getenv().getOrDefault("MYSQL_PORT", useLocal ? "33060" : "3306");
        String db   = System.getenv().getOrDefault("MYSQL_DATABASE", "world");
        return "jdbc:mysql://" + host + ":" + port + "/" + db + "?allowPublicKeyRetrieval=true&useSSL=false";
    }

    private String dbUser() {
        return System.getenv().getOrDefault("MYSQL_USER", DB_USER_DEFAULT);
    }

    private String dbPassword() {
        return System.getenv().getOrDefault("MYSQL_PASSWORD", DB_PASSWORD_DEFAULT);
    }

    /**
     * Connect to the MySQL database
     * @param useLocal true for local connection, false for Docker
     */
    public void connect(boolean useLocal) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        String dbUrl = buildUrl(useLocal);
        int retries = 10;

        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database (attempt " + (i + 1) + "/" + retries + ")...");
            try {
                Thread.sleep(30000);

                con = DriverManager.getConnection(dbUrl, dbUser(), dbPassword());
                System.out.println("Successfully connected to database!");
                System.out.println("=".repeat(80));
                return;

            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + (i + 1));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }

        System.out.println("Failed to connect to database after " + retries + " attempts.");
        System.exit(-1);
    }

    /**
     * Disconnect from the MySQL database
     */
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
                System.out.println("\nDatabase connection closed successfully.");
            } catch (Exception e) {
                System.out.println("Error closing connection to database: " + e.getMessage());
            }
        }
    }

    /**
     * Check if connected to database
     */
    public boolean isConnected() {
        try {
            return con != null && !con.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Execute a query and print results to console
     * @param sql The SQL query to execute
     * @param reportNumber Optional report number for display
     */
    public void executeAndDisplay(String sql, int reportNumber) {
        if (!isConnected()) {
            System.out.println("No active database connection.");
            return;
        }

        try (Statement stmt = con.createStatement()) {
            boolean hasResultSet = stmt.execute(sql);

            if (hasResultSet) {
                ResultSet rs = stmt.getResultSet();
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();

                // Print report header if provided
                if (reportNumber > 0) {
                    System.out.println("\n" + "=".repeat(80));
                    System.out.println("REPORT #" + reportNumber);
                    System.out.println("=".repeat(80));
                }

                // Print column headers
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(meta.getColumnLabel(i) + "\t");
                }
                System.out.println("\n" + "-".repeat(80));

                // Print each row
                int rowCount = 0;
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        String value = rs.getString(i);
                        System.out.print((value != null ? value : "NULL") + "\t");
                    }
                    System.out.println();
                    rowCount++;
                }

                System.out.println("-".repeat(80));
                System.out.println("Total rows: " + rowCount);

                rs.close();
            } else {
                int updateCount = stmt.getUpdateCount();
                System.out.println("Query executed successfully. " + updateCount + " rows affected.");
            }

        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
            System.out.println("SQL: " + sql);
        }
    }

    /**
     * Execute a query and print results to console (without report number)
     */
    public void executeAndDisplay(String sql) {
        executeAndDisplay(sql, -1);
    }

    /**
     * Get the connection object (for advanced usage)
     */
    public Connection getConnection() {
        return con;
    }
}