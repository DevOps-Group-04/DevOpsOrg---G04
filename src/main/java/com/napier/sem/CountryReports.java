package com.napier.sem;

/**
 * Handles all country-related population reports
 */
public class CountryReports {

    private static final String DEFAULT_CONTINENT = "Asia";
    private static final String DEFAULT_REGION = "Eastern Asia";

    /**
     * All countries in the world organized by largest population to smallest
     */
    public static String allCountriesWorld() {
        return String.format("""
            SELECT RPAD(Code, 5, ' ')        AS Code,
                   RPAD(Name, 45, ' ')       AS Name,
                   RPAD(Continent, 15, ' ')  AS Continent,
                   RPAD(Region, 30, ' ')     AS Region,
                   LPAD(Population, 12, ' ') AS Population,
                   RPAD(Capital, 10, ' ')    AS Capital
            FROM   country
            ORDER BY Population DESC;
            """);
    }

    /**
     * All countries in a continent organized by largest population to smallest
     * Default continent: %s
     */
    public static String allCountriesContinent() {
        return String.format("""
            SELECT RPAD(Code, 5, ' ')        AS Code,
                   RPAD(Name, 45, ' ')       AS Name,
                   RPAD(Continent, 15, ' ')  AS Continent,
                   RPAD(Region, 30, ' ')     AS Region,
                   LPAD(Population, 12, ' ') AS Population,
                   RPAD(Capital, 10, ' ')    AS Capital
            FROM   country
            WHERE  Continent = '%s'
            ORDER BY Population DESC;
            """, DEFAULT_CONTINENT);
    }

    /**
     * All countries in a region organized by largest population to smallest
     * Default region: %s
     */
    public static String allCountriesRegion() {
        return String.format("""
            SELECT RPAD(Code, 5, ' ')        AS Code,
                   RPAD(Name, 45, ' ')       AS Name,
                   RPAD(Continent, 15, ' ')  AS Continent,
                   RPAD(Region, 30, ' ')     AS Region,
                   LPAD(Population, 12, ' ') AS Population,
                   RPAD(Capital, 10, ' ')    AS Capital
            FROM   country
            WHERE  Region = '%s'
            ORDER BY Population DESC;
            """, DEFAULT_REGION);
    }

    /**
     * Top N populated countries in the world
     */
    public static String topNCountriesWorld(int n) {
        return String.format("""
            SELECT RPAD(Code, 5, ' ')        AS Code,
                   RPAD(Name, 45, ' ')       AS Name,
                   RPAD(Continent, 15, ' ')  AS Continent,
                   RPAD(Region, 30, ' ')     AS Region,
                   LPAD(Population, 12, ' ') AS Population,
                   RPAD(Capital, 10, ' ')    AS Capital
            FROM   country
            ORDER BY Population DESC
            LIMIT  %d;
            """, n);
    }

    /**
     * Top N populated countries in a continent
     * Default continent: %s
     */
    public static String topNCountriesContinent(int n) {
        return String.format("""
            SELECT RPAD(Code, 5, ' ')        AS Code,
                   RPAD(Name, 45, ' ')       AS Name,
                   RPAD(Continent, 15, ' ')  AS Continent,
                   RPAD(Region, 30, ' ')     AS Region,
                   LPAD(Population, 12, ' ') AS Population,
                   RPAD(Capital, 10, ' ')    AS Capital
            FROM   country
            WHERE  Continent = '%s'
            ORDER BY Population DESC
            LIMIT  %d;
            """, DEFAULT_CONTINENT, n);
    }

    /**
     * Top N populated countries in a region
     * Default region: %s
     */
    public static String topNCountriesRegion(int n) {
        return String.format("""
            SELECT RPAD(Code, 5, ' ')        AS Code,
                   RPAD(Name, 45, ' ')       AS Name,
                   RPAD(Continent, 15, ' ')  AS Continent,
                   RPAD(Region, 30, ' ')     AS Region,
                   LPAD(Population, 12, ' ') AS Population,
                   RPAD(Capital, 10, ' ')    AS Capital
            FROM   country
            WHERE  Region = '%s'
            ORDER BY Population DESC
            LIMIT  %d;
            """, DEFAULT_REGION, n);
    }
}
