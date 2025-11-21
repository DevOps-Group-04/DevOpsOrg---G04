package com.napier.sem;

/**
 * Handles world-level and specific geographic area population queries
 */
public class WorldReports {

    private static final String DEFAULT_CONTINENT = "Asia";
    private static final String DEFAULT_REGION = "Eastern Asia";
    private static final String DEFAULT_COUNTRY = "China";
    private static final String DEFAULT_DISTRICT = "Shanghai";
    private static final String DEFAULT_CITY = "Shanghai";

    /**
     * The population of the world
     */
    public static String worldPopulation() {
        return """
            SELECT LPAD(SUM(Population), 15, ' ') AS WorldPopulation
            FROM   country;
            """;
    }

    /**
     * The population of a continent
     * Default continent: Asia
     */
    public static String continentPopulation() {
        return String.format("""
            SELECT RPAD(Continent, 20, ' ')            AS Continent,
                   LPAD(SUM(Population), 15, ' ')      AS ContinentPopulation
            FROM   country
            WHERE  Continent = '%s'
            GROUP BY Continent;
            """, DEFAULT_CONTINENT);
    }

    /**
     * The population of a region
     * Default region: Eastern Asia
     */
    public static String regionPopulation() {
        return String.format("""
            SELECT RPAD(Region, 30, ' ')               AS Region,
                   LPAD(SUM(Population), 15, ' ')      AS RegionPopulation
            FROM   country
            WHERE  Region = '%s'
            GROUP BY Region;
            """, DEFAULT_REGION);
    }

    /**
     * The population of a country
     * Default country: China
     */
    public static String countryPopulation() {
        return String.format("""
            SELECT RPAD(Name, 45, ' ')                 AS Country,
                   LPAD(Population, 15, ' ')           AS CountryPopulation
            FROM   country
            WHERE  Name = '%s';
            """, DEFAULT_COUNTRY);
    }

    /**
     * The population of a district
     * Default district: Shanghai
     */
    public static String districtPopulation() {
        return String.format("""
            SELECT RPAD(District, 30, ' ')             AS District,
                   LPAD(SUM(Population), 15, ' ')      AS DistrictPopulation
            FROM   city
            WHERE  District = '%s'
            GROUP BY District;
            """, DEFAULT_DISTRICT);
    }

    /**
     * The population of a city
     * Default city: Shanghai
     */
    public static String cityPopulation() {
        return String.format("""
            SELECT RPAD(Name, 40, ' ')                 AS City,
                   LPAD(Population, 15, ' ')           AS CityPopulation
            FROM   city
            WHERE  Name = '%s';
            """, DEFAULT_CITY);
    }
}
