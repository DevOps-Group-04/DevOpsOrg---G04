package com.napier.sem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CityReportsUT {

    // ---------- allCitiesWorld ----------

    @Test
    void allCitiesWorld_containsJoinAndOrder() {
        String sql = CityReports.allCitiesWorld();
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("from   city"), "Should select from city");
        assertTrue(lower.contains("join   country on city.countrycode = country.code"), "Should join country");
        assertTrue(lower.contains("order by city.population desc"), "Should order by population desc");
    }

    @Test
    void allCitiesWorld_hasExpectedColumns() {
        String sql = CityReports.allCitiesWorld();
        assertTrue(sql.contains("RPAD(city.Name, 40, ' ')"), "City column");
        assertTrue(sql.contains("RPAD(country.Name, 45, ' ')"), "Country column");
        assertTrue(sql.contains("RPAD(city.District, 25, ' ')"), "District column");
        assertTrue(sql.contains("LPAD(city.Population, 12, ' ')"), "Population column");
    }

    // ---------- allCitiesContinent ----------

    @Test
    void allCitiesContinent_filtersByContinent() {
        String sql = CityReports.allCitiesContinent();
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("where  country.continent ="), "Should filter by continent");
        assertTrue(lower.contains("order by city.population desc"));
    }

    @Test
    void allCitiesContinent_hasExpectedColumns() {
        String sql = CityReports.allCitiesContinent();
        assertTrue(sql.contains("RPAD(city.Name, 40, ' ')"));
        assertTrue(sql.contains("RPAD(country.Name, 45, ' ')"));
        assertTrue(sql.contains("RPAD(city.District, 25, ' ')"));
        assertTrue(sql.contains("LPAD(city.Population, 12, ' ')"));
    }

    // ---------- allCitiesRegion ----------

    @Test
    void allCitiesRegion_filtersByRegion() {
        String sql = CityReports.allCitiesRegion();
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("where  country.region ="), "Should filter by region");
        assertTrue(lower.contains("order by city.population desc"));
    }

    // ---------- allCitiesCountry ----------

    @Test
    void allCitiesCountry_filtersByCountry() {
        String sql = CityReports.allCitiesCountry();
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("where  country.name ="), "Should filter by country");
        assertTrue(lower.contains("order by city.population desc"));
    }

    // ---------- allCitiesDistrict ----------

    @Test
    void allCitiesDistrict_filtersByDistrict() {
        String sql = CityReports.allCitiesDistrict();
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("where  city.district ="), "Should filter by district");
        assertTrue(lower.contains("order by city.population desc"));
    }

    // ---------- topN world/continent/region/country/district ----------

    @Test
    void topNCitiesWorld_includesLimit() {
        int n = 9;
        String sql = CityReports.topNCitiesWorld(n);
        assertTrue(sql.contains("LIMIT  " + n + ";") || sql.contains("LIMIT " + n + ";"),
                "Should include LIMIT " + n);
    }

    @Test
    void topNCitiesContinent_includesLimitAndFilter() {
        int n = 6;
        String sql = CityReports.topNCitiesContinent(n);
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("where  country.continent ="), "Should filter by continent");
        assertTrue(sql.contains("LIMIT  " + n + ";") || sql.contains("LIMIT " + n + ";"),
                "Should include LIMIT " + n);
    }

    @Test
    void topNCitiesRegion_includesLimitAndFilter() {
        int n = 4;
        String sql = CityReports.topNCitiesRegion(n);
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("where  country.region ="), "Should filter by region");
        assertTrue(sql.contains("LIMIT  " + n + ";") || sql.contains("LIMIT " + n + ";"),
                "Should include LIMIT " + n);
    }

    @Test
    void topNCitiesCountry_includesLimitAndFilter() {
        int n = 7;
        String sql = CityReports.topNCitiesCountry(n);
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("where  country.name ="), "Should filter by country");
        assertTrue(sql.contains("LIMIT  " + n + ";") || sql.contains("LIMIT " + n + ";"),
                "Should include LIMIT " + n);
    }

    @Test
    void topNCitiesDistrict_includesLimitAndFilter() {
        int n = 2;
        String sql = CityReports.topNCitiesDistrict(n);
        String lower = sql.toLowerCase();
        assertTrue(lower.contains("where  city.district ="), "Should filter by district");
        assertTrue(sql.contains("LIMIT  " + n + ";") || sql.contains("LIMIT " + n + ";"),
                "Should include LIMIT " + n);
    }

    // ---------- general shape ----------

    @Test
    void queries_terminateWithSemicolon() {
        assertTrue(CityReports.allCitiesWorld().trim().endsWith(";"));
        assertTrue(CityReports.allCitiesContinent().trim().endsWith(";"));
        assertTrue(CityReports.allCitiesRegion().trim().endsWith(";"));
        assertTrue(CityReports.allCitiesCountry().trim().endsWith(";"));
        assertTrue(CityReports.allCitiesDistrict().trim().endsWith(";"));
        assertTrue(CityReports.topNCitiesWorld(1).trim().endsWith(";"));
        assertTrue(CityReports.topNCitiesContinent(1).trim().endsWith(";"));
        assertTrue(CityReports.topNCitiesRegion(1).trim().endsWith(";"));
        assertTrue(CityReports.topNCitiesCountry(1).trim().endsWith(";"));
        assertTrue(CityReports.topNCitiesDistrict(1).trim().endsWith(";"));
    }

    @Test
    void queries_useExpectedPaddingFunctions() {
        String sql = CityReports.allCitiesWorld();
        assertTrue(sql.contains("RPAD("));
        assertTrue(sql.contains("LPAD("));
    }
}
