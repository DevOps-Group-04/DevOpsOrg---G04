package com.napier.sem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class countryReportTest {

    @Test
    void testApplyLimitParameter_ReplacesPlaceholder() {
        String input = "SELECT * FROM city LIMIT ?;";
        String expected = "SELECT * FROM city LIMIT 10;";
        String result = callApplyLimitParameter(input, 10);
        assertEquals(expected, result);
    }

    @Test
    void testApplyLimitParameter_NoReplacementWhenZero() {
        String input = "SELECT * FROM city LIMIT ?;";
        String result = callApplyLimitParameter(input, 0);
        assertEquals(input, result);
    }

    @Test
    void testReportTopNCountriesByPopulationWorld() {
        String sql = countryReport.reportTopNCountriesByPopulationWorld(5);
        assertTrue(sql.contains("LIMIT 5;"));
        assertTrue(sql.contains("FROM country"));
    }

    @Test
    void testReportPopulationByContinentContainsPlaceholder() {
        String sql = countryReport.reportPopulationByContinent();
        assertTrue(sql.contains("WHERE Continent = ?"));
    }

    // Access private helper via reflection
    private String callApplyLimitParameter(String sql, int n) {
        try {
            var method = countryReport.class.getDeclaredMethod("applyLimitParameter", String.class, int.class);
            method.setAccessible(true);
            return (String) method.invoke(null, sql, n);
        } catch (Exception e) {
            fail("Reflection call failed: " + e.getMessage());
            return null;
        }
    }
}
