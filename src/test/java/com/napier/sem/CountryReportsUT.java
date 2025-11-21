package com.napier.sem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CountryReportsUT {

    // ---------- allCountriesWorld ----------

    @Test
    void allCountriesWorld_containsSelectAndFrom() {
        String sql = CountryReports.allCountriesWorld();
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("select"), "Should contain SELECT");
        assertTrue(lower.contains("from   country"), "Should select from country");
    }

    @Test
    void allCountriesWorld_hasExpectedColumnsAndOrder() {
        String sql = CountryReports.allCountriesWorld();
        assertTrue(sql.contains("RPAD(Code, 5, ' ')"), "Should select Code");
        assertTrue(sql.contains("RPAD(Name, 45, ' ')"), "Should select Name");
        assertTrue(sql.contains("RPAD(Continent, 15, ' ')"), "Should select Continent");
        assertTrue(sql.contains("RPAD(Region, 30, ' ')"), "Should select Region");
        assertTrue(sql.contains("LPAD(Population, 12, ' ')"), "Should select Population");
        assertTrue(sql.contains("RPAD(Capital, 10, ' ')"), "Should select Capital");
        assertTrue(sql.contains("ORDER BY Population DESC"), "Should order by population desc");
    }

    // ---------- allCountriesContinent ----------

    @Test
    void allCountriesContinent_filtersByContinentAndOrders() {
        String sql = CountryReports.allCountriesContinent();
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("from   country"), "Should select from country");
        assertTrue(lower.contains("where  continent ="), "Should filter by continent");
        assertTrue(sql.contains("ORDER BY Population DESC"), "Should order by population desc");
    }

    @Test
    void allCountriesContinent_hasExpectedColumns() {
        String sql = CountryReports.allCountriesContinent();
        assertTrue(sql.contains("RPAD(Code, 5, ' ')"));
        assertTrue(sql.contains("RPAD(Name, 45, ' ')"));
        assertTrue(sql.contains("RPAD(Continent, 15, ' ')"));
        assertTrue(sql.contains("RPAD(Region, 30, ' ')"));
        assertTrue(sql.contains("LPAD(Population, 12, ' ')"));
        assertTrue(sql.contains("RPAD(Capital, 10, ' ')"));
    }

    // ---------- allCountriesRegion ----------

    @Test
    void allCountriesRegion_filtersByRegionAndOrders() {
        String sql = CountryReports.allCountriesRegion();
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("from   country"), "Should select from country");
        assertTrue(lower.contains("where  region ="), "Should filter by region");
        assertTrue(sql.contains("ORDER BY Population DESC"), "Should order by population desc");
    }

    @Test
    void allCountriesRegion_hasExpectedColumns() {
        String sql = CountryReports.allCountriesRegion();
        assertTrue(sql.contains("RPAD(Code, 5, ' ')"));
        assertTrue(sql.contains("RPAD(Name, 45, ' ')"));
        assertTrue(sql.contains("RPAD(Continent, 15, ' ')"));
        assertTrue(sql.contains("RPAD(Region, 30, ' ')"));
        assertTrue(sql.contains("LPAD(Population, 12, ' ')"));
        assertTrue(sql.contains("RPAD(Capital, 10, ' ')"));
    }

    // ---------- topNCountriesWorld ----------

    @Test
    void topNCountriesWorld_includesLimitN() {
        int n = 7;
        String sql = CountryReports.topNCountriesWorld(n);
        assertTrue(sql.contains("LIMIT  " + n + ";") || sql.contains("LIMIT " + n + ";"),
                "Should include LIMIT " + n);
        assertTrue(sql.contains("ORDER BY Population DESC"), "Should order by population desc");
        assertTrue(sql.toLowerCase().contains("from   country"), "Should select from country");
    }

    @Test
    void topNCountriesWorld_hasExpectedColumns() {
        String sql = CountryReports.topNCountriesWorld(1);
        assertTrue(sql.contains("RPAD(Code, 5, ' ')"));
        assertTrue(sql.contains("RPAD(Name, 45, ' ')"));
        assertTrue(sql.contains("RPAD(Continent, 15, ' ')"));
        assertTrue(sql.contains("RPAD(Region, 30, ' ')"));
        assertTrue(sql.contains("LPAD(Population, 12, ' ')"));
        assertTrue(sql.contains("RPAD(Capital, 10, ' ')"));
    }

    // ---------- topNCountriesContinent ----------

    @Test
    void topNCountriesContinent_includesLimitNAndWhereContinent() {
        int n = 10;
        String sql = CountryReports.topNCountriesContinent(n);
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("where  continent ="), "Should filter by continent");
        assertTrue(sql.contains("LIMIT  " + n + ";") || sql.contains("LIMIT " + n + ";"),
                "Should include LIMIT " + n);
        assertTrue(sql.contains("ORDER BY Population DESC"), "Should order by population desc");
    }

    @Test
    void topNCountriesContinent_hasExpectedColumns() {
        String sql = CountryReports.topNCountriesContinent(3);
        assertTrue(sql.contains("RPAD(Code, 5, ' ')"));
        assertTrue(sql.contains("RPAD(Name, 45, ' ')"));
        assertTrue(sql.contains("RPAD(Continent, 15, ' ')"));
        assertTrue(sql.contains("RPAD(Region, 30, ' ')"));
        assertTrue(sql.contains("LPAD(Population, 12, ' ')"));
        assertTrue(sql.contains("RPAD(Capital, 10, ' ')"));
    }

    // ---------- topNCountriesRegion ----------

    @Test
    void topNCountriesRegion_includesLimitNAndWhereRegion() {
        int n = 12;
        String sql = CountryReports.topNCountriesRegion(n);
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("where  region ="), "Should filter by region");
        assertTrue(sql.contains("LIMIT  " + n + ";") || sql.contains("LIMIT " + n + ";"),
                "Should include LIMIT " + n);
        assertTrue(sql.contains("ORDER BY Population DESC"), "Should order by population desc");
    }

    @Test
    void topNCountriesRegion_hasExpectedColumns() {
        String sql = CountryReports.topNCountriesRegion(2);
        assertTrue(sql.contains("RPAD(Code, 5, ' ')"));
        assertTrue(sql.contains("RPAD(Name, 45, ' ')"));
        assertTrue(sql.contains("RPAD(Continent, 15, ' ')"));
        assertTrue(sql.contains("RPAD(Region, 30, ' ')"));
        assertTrue(sql.contains("LPAD(Population, 12, ' ')"));
        assertTrue(sql.contains("RPAD(Capital, 10, ' ')"));
    }

    // ---------- general shape checks ----------

    @Test
    void queries_terminateWithSemicolon() {
        assertTrue(CountryReports.allCountriesWorld().trim().endsWith(";"));
        assertTrue(CountryReports.allCountriesContinent().trim().endsWith(";"));
        assertTrue(CountryReports.allCountriesRegion().trim().endsWith(";"));
        assertTrue(CountryReports.topNCountriesWorld(1).trim().endsWith(";"));
        assertTrue(CountryReports.topNCountriesContinent(1).trim().endsWith(";"));
        assertTrue(CountryReports.topNCountriesRegion(1).trim().endsWith(";"));
    }

    @Test
    void queries_useExpectedPaddingFunctions() {
        String world = CountryReports.allCountriesWorld();
        assertTrue(world.contains("RPAD("));
        assertTrue(world.contains("LPAD("));
    }
}
