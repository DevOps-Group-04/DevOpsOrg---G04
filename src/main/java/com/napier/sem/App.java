package com.napier.sem;

/**
 * Main application entry point
 * This class provides backward compatibility with the original App structure
 */
public class App {

    private DatabaseConnection db;

    public App() {
        this.db = new DatabaseConnection();
    }

    /**
     * Connect to the MySQL database
     * Uses local connection by default
     */
    public void connect() {
        connect(true);
    }

    /**
     * Connect to the MySQL database
     * @param useLocal true for local connection, false for Docker
     */
    public void connect(boolean useLocal) {
        db.connect(useLocal);
    }

    /**
     * Disconnect from the MySQL database
     */
    public void disconnect() {
        db.disconnect();
    }

    /**
     * Execute a query and display results
     */
    public void executeAndDisplay(String sql) {
        db.executeAndDisplay(sql);
    }

    /**
     * Get the database connection object
     */
    public DatabaseConnection getDatabase() {
        return db;
    }

    /**
     * Main method - runs the reporting system
     */
    public static void main(String[] args) {
        // Create application
        App app = new App();

        // Connect to database
        // Change to false when running in Docker: app.connect(false);
        app.connect(false);

        // Create report manager with the database connection
        ReportManager manager = new ReportManager(app.getDatabase());

//        // Generate reports based on user input
//        manager.generateReportFromUserInput();

        // Non-interactive: generate all reports, then disconnect and exit
        manager.generateAllReports();

        // Disconnect from database
        app.disconnect();
    }
}