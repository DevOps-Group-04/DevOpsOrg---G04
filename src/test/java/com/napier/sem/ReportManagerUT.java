package com.napier.sem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReportManagerUT {

    // Minimal fake to avoid real DB I/O in unit tests
    static class FakeDb extends DatabaseConnection {
        int calls = 0;
        @Override
        public void executeAndDisplay(String sql, int reportNumber) {
            calls++;
            assertNotNull(sql);
            assertTrue(reportNumber >= 1 && reportNumber <= 32);
        }
    }

    @Test
    void generateAllReports_executes32Times() {
        FakeDb db = new FakeDb();
        ReportManager manager = new ReportManager(db);
        manager.generateAllReports();
        assertEquals(32, db.calls);
    }
}