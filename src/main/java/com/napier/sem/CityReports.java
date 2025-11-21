package com.napier.sem;

/**
 * Handles all city-related population reports
 */
public class CityReports {

    private static final String DEFAULT_CONTINENT = "Asia";
    private static final String DEFAULT_REGION = "Eastern Asia";
    private static final String DEFAULT_COUNTRY = "China";
    private static final String DEFAULT_DISTRICT = "Shanghai";

    /**
     * All cities in the world organized by largest population to smallest
     */
    public static String allCitiesWorld() {
        return """
            SELECT RPAD(city.Name, 40, ' ')       AS City,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   RPAD(city.District, 25, ' ')   AS District,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.CountryCode = country.Code
            ORDER BY city.Population DESC;
            """;
    }

    /**
     * All cities in a continent organized by largest population to smallest
     * Default continent: Asia
     */
    public static String allCitiesContinent() {
        return String.format("""
            SELECT RPAD(city.Name, 40, ' ')       AS City,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   RPAD(city.District, 25, ' ')   AS District,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.CountryCode = country.Code
            WHERE  country.Continent = '%s'
            ORDER BY city.Population DESC;
            """, DEFAULT_CONTINENT);
    }

    /**
     * All cities in a region organized by largest population to smallest
     * Default region: Eastern Asia
     */
    public static String allCitiesRegion() {
        return String.format("""
            SELECT RPAD(city.Name, 40, ' ')       AS City,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   RPAD(city.District, 25, ' ')   AS District,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.CountryCode = country.Code
            WHERE  country.Region = '%s'
            ORDER BY city.Population DESC;
            """, DEFAULT_REGION);
    }

    /**
     * All cities in a country organized by largest population to smallest
     * Default country: China
     */
    public static String allCitiesCountry() {
        return String.format("""
            SELECT RPAD(city.Name, 40, ' ')       AS City,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   RPAD(city.District, 25, ' ')   AS District,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.CountryCode = country.Code
            WHERE  country.Name = '%s'
            ORDER BY city.Population DESC;
            """, DEFAULT_COUNTRY);
    }

    /**
     * All cities in a district organized by largest population to smallest
     * Default district: Shanghai
     */
    public static String allCitiesDistrict() {
        return String.format("""
            SELECT RPAD(city.Name, 40, ' ')       AS City,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   RPAD(city.District, 25, ' ')   AS District,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.CountryCode = country.Code
            WHERE  city.District = '%s'
            ORDER BY city.Population DESC;
            """, DEFAULT_DISTRICT);
    }

    /**
     * Top N populated cities in the world
     */
    public static String topNCitiesWorld(int n) {
        return String.format("""
            SELECT RPAD(city.Name, 40, ' ')       AS City,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   RPAD(city.District, 25, ' ')   AS District,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.CountryCode = country.Code
            ORDER BY city.Population DESC
            LIMIT  %d;
            """, n);
    }

    /**
     * Top N populated cities in a continent
     * Default continent: Asia
     */
    public static String topNCitiesContinent(int n) {
        return String.format("""
            SELECT RPAD(city.Name, 40, ' ')       AS City,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   RPAD(city.District, 25, ' ')   AS District,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.CountryCode = country.Code
            WHERE  country.Continent = '%s'
            ORDER BY city.Population DESC
            LIMIT  %d;
            """, DEFAULT_CONTINENT, n);
    }

    /**
     * Top N populated cities in a region
     * Default region: Eastern Asia
     */
    public static String topNCitiesRegion(int n) {
        return String.format("""
            SELECT RPAD(city.Name, 40, ' ')       AS City,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   RPAD(city.District, 25, ' ')   AS District,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.CountryCode = country.Code
            WHERE  country.Region = '%s'
            ORDER BY city.Population DESC
            LIMIT  %d;
            """, DEFAULT_REGION, n);
    }

    /**
     * Top N populated cities in a country
     * Default country: China
     */
    public static String topNCitiesCountry(int n) {
        return String.format("""
            SELECT RPAD(city.Name, 40, ' ')       AS City,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   RPAD(city.District, 25, ' ')   AS District,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.CountryCode = country.Code
            WHERE  country.Name = '%s'
            ORDER BY city.Population DESC
            LIMIT  %d;
            """, DEFAULT_COUNTRY, n);
    }

    /**
     * Top N populated cities in a district
     * Default district: Shanghai
     */
    public static String topNCitiesDistrict(int n) {
        return String.format("""
            SELECT RPAD(city.Name, 40, ' ')       AS City,
                   RPAD(country.Name, 45, ' ')    AS Country,
                   RPAD(city.District, 25, ' ')   AS District,
                   LPAD(city.Population, 12, ' ') AS Population
            FROM   city
            JOIN   country ON city.CountryCode = country.Code
            WHERE  city.District = '%s'
            ORDER BY city.Population DESC
            LIMIT  %d;
            """, DEFAULT_DISTRICT, n);
    }
}
