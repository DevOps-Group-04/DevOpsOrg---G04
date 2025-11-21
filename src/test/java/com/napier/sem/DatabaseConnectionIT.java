package com.napier.sem;

import org.junit.jupiter.api.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatabaseConnectionIT {

    private DatabaseConnection db;

    @BeforeAll
    void init() {
        db = new DatabaseConnection();
        // Host-based integration test: connect to localhost:33060
        db.connect(true);
        assertTrue(db.isConnected(), "DB should be connected");
    }

    @Test
    void executeSimpleSelect() {
        db.executeAndDisplay("SELECT 1 AS ok;", -1);
        assertTrue(db.isConnected());
    }

    @AfterAll
    void done() {
        db.disconnect();
        assertFalse(db.isConnected());
    }
}
