package com.napier.sem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CapitalCityReportsUT {

    // ---------- allCapitalsWorld ----------

    @Test
    void allCapitalsWorld_containsJoinAndOrder() {
        String sql = CapitalCityReports.allCapitalsWorld();
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("from   city"), "Should select from city");
        assertTrue(lower.contains("join   country on city.id = country.capital"), "Should join country on capital id");
        assertTrue(lower.contains("order by city.population desc"), "Should order by population desc");
    }

    @Test
    void allCapitalsWorld_hasExpectedColumns() {
        String sql = CapitalCityReports.allCapitalsWorld();
        assertTrue(sql.contains("RPAD(city.Name, 40, ' ')"), "Capital column");
        assertTrue(sql.contains("RPAD(country.Name, 45, ' ')"), "Country column");
        assertTrue(sql.contains("LPAD(city.Population, 12, ' ')"), "Population column");
    }

    // ---------- allCapitalsContinent ----------

    @Test
    void allCapitalsContinent_filtersByContinent() {
        String sql = CapitalCityReports.allCapitalsContinent();
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("where  country.continent ="), "Should filter by continent");
        assertTrue(lower.contains("order by city.population desc"), "Should order by population desc");
    }

    @Test
    void allCapitalsContinent_hasExpectedColumns() {
        String sql = CapitalCityReports.allCapitalsContinent();
        assertTrue(sql.contains("RPAD(city.Name, 40, ' ')"));
        assertTrue(sql.contains("RPAD(country.Name, 45, ' ')"));
        assertTrue(sql.contains("LPAD(city.Population, 12, ' ')"));
    }

    // ---------- allCapitalsRegion ----------

    @Test
    void allCapitalsRegion_filtersByRegion() {
        String sql = CapitalCityReports.allCapitalsRegion();
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("where  country.region ="), "Should filter by region");
        assertTrue(lower.contains("order by city.population desc"), "Should order by population desc");
    }

    @Test
    void allCapitalsRegion_hasExpectedColumns() {
        String sql = CapitalCityReports.allCapitalsRegion();
        assertTrue(sql.contains("RPAD(city.Name, 40, ' ')"));
        assertTrue(sql.contains("RPAD(country.Name, 45, ' ')"));
        assertTrue(sql.contains("LPAD(city.Population, 12, ' ')"));
    }

    // ---------- topNCapitalsWorld ----------

    @Test
    void topNCapitalsWorld_includesLimitN() {
        int n = 8;
        String sql = CapitalCityReports.topNCapitalsWorld(n);
        assertTrue(sql.contains("LIMIT  " + n + ";") || sql.contains("LIMIT " + n + ";"),
                "Should include LIMIT " + n);
        assertTrue(sql.toLowerCase().contains("order by city.population desc"));
    }

    // ---------- topNCapitalsContinent ----------

    @Test
    void topNCapitalsContinent_includesLimitAndFilter() {
        int n = 5;
        String sql = CapitalCityReports.topNCapitalsContinent(n);
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("where  country.continent ="), "Should filter by continent");
        assertTrue(sql.contains("LIMIT  " + n + ";") || sql.contains("LIMIT " + n + ";"),
                "Should include LIMIT " + n);
    }

    // ---------- topNCapitalsRegion ----------

    @Test
    void topNCapitalsRegion_includesLimitAndFilter() {
        int n = 3;
        String sql = CapitalCityReports.topNCapitalsRegion(n);
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("where  country.region ="), "Should filter by region");
        assertTrue(sql.contains("LIMIT  " + n + ";") || sql.contains("LIMIT " + n + ";"),
                "Should include LIMIT " + n);
    }

    // ---------- general shape ----------

    @Test
    void queries_terminateWithSemicolon() {
        assertTrue(CapitalCityReports.allCapitalsWorld().trim().endsWith(";"));
        assertTrue(CapitalCityReports.allCapitalsContinent().trim().endsWith(";"));
        assertTrue(CapitalCityReports.allCapitalsRegion().trim().endsWith(";"));
        assertTrue(CapitalCityReports.topNCapitalsWorld(1).trim().endsWith(";"));
        assertTrue(CapitalCityReports.topNCapitalsContinent(1).trim().endsWith(";"));
        assertTrue(CapitalCityReports.topNCapitalsRegion(1).trim().endsWith(";"));
    }

    @Test
    void queries_useExpectedPaddingFunctions() {
        String sql = CapitalCityReports.allCapitalsWorld();
        assertTrue(sql.contains("RPAD("));
        assertTrue(sql.contains("LPAD("));
    }
}
