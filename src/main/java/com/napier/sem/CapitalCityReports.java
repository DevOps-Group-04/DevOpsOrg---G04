package com.napier.sem;

/**
 * Handles all capital city-related population reports
 */
public class CapitalCityReports {

    private static final String DEFAULT_CONTINENT = "Asia";
    private static final String DEFAULT_REGION = "Eastern Asia";

    /**
     * All capital cities in the world organized by largest population to smallest
     */
    public static String allCapitalsWorld() {
        return """
            SELECT RPAD(city.Name, 40, ' ')       AS Capital,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.ID = country.Capital
            ORDER BY city.Population DESC;
            """;
    }

    /**
     * All capital cities in a continent organized by largest population to smallest
     * Default continent: Asia
     */
    public static String allCapitalsContinent() {
        return String.format("""
            SELECT RPAD(city.Name, 40, ' ')       AS Capital,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.ID = country.Capital
            WHERE  country.Continent = '%s'
            ORDER BY city.Population DESC;
            """, DEFAULT_CONTINENT);
    }

    /**
     * All capital cities in a region organized by largest population to smallest
     * Default region: Eastern Asia
     */
    public static String allCapitalsRegion() {
        return String.format("""
            SELECT RPAD(city.Name, 40, ' ')       AS Capital,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.ID = country.Capital
            WHERE  country.Region = '%s'
            ORDER BY city.Population DESC;
            """, DEFAULT_REGION);
    }

    /**
     * Top N populated capital cities in the world
     */
    public static String topNCapitalsWorld(int n) {
        return String.format("""
            SELECT RPAD(city.Name, 40, ' ')       AS Capital,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.ID = country.Capital
            ORDER BY city.Population DESC
            LIMIT  %d;
            """, n);
    }

    /**
     * Top N populated capital cities in a continent
     * Default continent: Asia
     */
    public static String topNCapitalsContinent(int n) {
        return String.format("""
            SELECT RPAD(city.Name, 40, ' ')       AS Capital,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.ID = country.Capital
            WHERE  country.Continent = '%s'
            ORDER BY city.Population DESC
            LIMIT  %d;
            """, DEFAULT_CONTINENT, n);
    }

    /**
     * Top N populated capital cities in a region
     * Default region: Eastern Asia
     */
    public static String topNCapitalsRegion(int n) {
        return String.format("""
            SELECT RPAD(city.Name, 40, ' ')       AS Capital,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.ID = country.Capital
            WHERE  country.Region = '%s'
            ORDER BY city.Population DESC
            LIMIT  %d;
            """, DEFAULT_REGION, n);
    }
}