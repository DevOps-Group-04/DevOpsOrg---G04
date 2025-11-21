package com.napier.sem;

/**
 * Handles population distribution reports (city vs non-city populations)
 */
public class PopulationReports {

    /**
     * Population of people living in cities vs not living in cities in each continent
     */
    public static String populationByContinent() {
        return """
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
    }

    /**
     * Population of people living in cities vs not living in cities in each region
     */
    public static String populationByRegion() {
        return """
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
    }

    /**
     * Population of people living in cities vs not living in cities in each country
     */
    public static String populationByCountry() {
        return """
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
    }
}
