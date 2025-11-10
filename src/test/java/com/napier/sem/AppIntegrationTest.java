package com.napier.sem;

import org.junit.jupiter.api.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppIntegrationTest {

    private Connection con;

    @BeforeAll
    void setupConnection() throws Exception {
        String url = "jdbc:mysql://localhost:33060/world?allowPublicKeyRetrieval=true&useSSL=false";
        String user = "root";
        String pass = "example";

        con = DriverManager.getConnection(url, user, pass);
        assertNotNull(con, "Database connection should be established");
    }

    @Test
    void testQueryWorldPopulation() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM country;");
        assertTrue(rs.next());
        int count = rs.getInt(1);
        System.out.println("Countries in DB: " + count);
        assertTrue(count > 0);
    }

    @AfterAll
    void closeConnection() throws SQLException {
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }
}
