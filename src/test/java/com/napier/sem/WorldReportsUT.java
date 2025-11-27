package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldReportsUT {

    @Test
    void worldPopulation_returnsExpectedSQL() {
        String expected = """
            SELECT LPAD(SUM(Population), 15, ' ') AS WorldPopulation
            FROM   country;
            """;
        assertEquals(expected, WorldReports.worldPopulation());
    }

    @Test
    void continentPopulation_returnsExpectedSQL() {
        String expected = """
            SELECT RPAD(Continent, 20, ' ')            AS Continent,
                   LPAD(SUM(Population), 15, ' ')      AS ContinentPopulation
            FROM   country
            WHERE  Continent = 'Asia'
            GROUP BY Continent;
            """;
        assertEquals(expected, WorldReports.continentPopulation());
    }

    @Test
    void regionPopulation_returnsExpectedSQL() {
        String expected = """
            SELECT RPAD(Region, 30, ' ')               AS Region,
                   LPAD(SUM(Population), 15, ' ')      AS RegionPopulation
            FROM   country
            WHERE  Region = 'Eastern Asia'
            GROUP BY Region;
            """;
        assertEquals(expected, WorldReports.regionPopulation());
    }

    @Test
    void countryPopulation_returnsExpectedSQL() {
        String expected = """
            SELECT RPAD(Name, 45, ' ')                 AS Country,
                   LPAD(Population, 15, ' ')           AS CountryPopulation
            FROM   country
            WHERE  Name = 'China';
            """;
        assertEquals(expected, WorldReports.countryPopulation());
    }

    @Test
    void districtPopulation_returnsExpectedSQL() {
        String expected = """
            SELECT RPAD(District, 30, ' ')             AS District,
                   LPAD(SUM(Population), 15, ' ')      AS DistrictPopulation
            FROM   city
            WHERE  District = 'Shanghai'
            GROUP BY District;
            """;
        assertEquals(expected, WorldReports.districtPopulation());
    }

    @Test
    void cityPopulation_returnsExpectedSQL() {
        String expected = """
            SELECT RPAD(Name, 40, ' ')                 AS City,
                   LPAD(Population, 15, ' ')           AS CityPopulation
            FROM   city
            WHERE  Name = 'Shanghai';
            """;
        assertEquals(expected, WorldReports.cityPopulation());
    }
}