package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PopulationReportsUT {

    @Test
    void populationByContinent_returnsExpectedSQL() {
        String expected = """
            SELECT RPAD(country.Continent, 20, ' ')                 AS Name,
                   LPAD(SUM(country.Population), 15, ' ')           AS TotalPopulation,
                   LPAD(SUM(city.Population), 15, ' ')              AS CityPopulation,
                   LPAD(ROUND((SUM(city.Population) /
                        SUM(country.Population)) * 100, 2), 10, ' ') AS CityPercent,
                   LPAD(SUM(country.Population) - 
                        SUM(city.Population), 15, ' ')              AS NonCityPopulation,
                   LPAD(ROUND(((SUM(country.Population) - 
                        SUM(city.Population)) / 
                        SUM(country.Population)) * 100, 2), 10, ' ') AS NonCityPercent
            FROM   country
            LEFT JOIN city ON country.Code = city.CountryCode
            GROUP BY country.Continent
            ORDER BY TotalPopulation DESC;
            """;
        assertEquals(expected, PopulationReports.populationByContinent());
    }

    @Test
    void populationByRegion_returnsExpectedSQL() {
        String expected = """
            SELECT RPAD(country.Region, 30, ' ')                    AS Name,
                   LPAD(SUM(country.Population), 15, ' ')           AS TotalPopulation,
                   LPAD(SUM(city.Population), 15, ' ')              AS CityPopulation,
                   LPAD(ROUND((SUM(city.Population) / 
                        SUM(country.Population)) * 100, 2), 10, ' ') AS CityPercent,
                   LPAD(SUM(country.Population) - 
                        SUM(city.Population), 15, ' ')              AS NonCityPopulation,
                   LPAD(ROUND(((SUM(country.Population) - 
                        SUM(city.Population)) / 
                        SUM(country.Population)) * 100, 2), 10, ' ') AS NonCityPercent
            FROM   country
            LEFT JOIN city ON country.Code = city.CountryCode
            GROUP BY country.Region
            ORDER BY TotalPopulation DESC;
            """;
        assertEquals(expected, PopulationReports.populationByRegion());
    }

    @Test
    void populationByCountry_returnsExpectedSQL() {
        String expected = """
            SELECT RPAD(country.Name, 45, ' ')                      AS Name,
                   LPAD(country.Population, 15, ' ')                AS TotalPopulation,
                   LPAD(SUM(city.Population), 15, ' ')              AS CityPopulation,
                   LPAD(ROUND((SUM(city.Population) /
                        country.Population) * 100, 2), 10, ' ')      AS CityPercent,
                   LPAD(country.Population - 
                        SUM(city.Population), 15, ' ')              AS NonCityPopulation,
                   LPAD(ROUND(((country.Population - 
                        SUM(city.Population)) /
                        country.Population) * 100, 2), 10, ' ')      AS NonCityPercent
            FROM   country
            LEFT JOIN city ON country.Code = city.CountryCode
            GROUP BY country.Name, country.Population
            ORDER BY TotalPopulation DESC;
            """;
        assertEquals(expected, PopulationReports.populationByCountry());
    }
}