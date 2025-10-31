package com.napier.sem;
import java.util.Scanner;
import java.util.InputMismatchException;


// !!! - This should be reused, NOT just for country reports
// SQL queries are needed
// Scanner needs removed and an actual interface is needed once queries are working

public class countryReport {
    public static void getScope() { // To select the scope of the report
        int scopeID = 0;
        String scopeString = "";
        int topN = 0;
        int parameter = -1;
        Scanner input = new Scanner(System.in);
        boolean isValid = false;

        while (!isValid) {
            try {
                System.out.print("Enter a value between 1 and 32. If a specific parameter is needed, add it with '_': ");
                String userInput = input.next(); // Read as string to allow "6_5"

                if (userInput.contains("_")) {
                    // Split into two parts
                    String[] parts = userInput.split("_");

                    if (parts.length == 2) {
                        scopeID = Integer.parseInt(parts[0]);
                        parameter = Integer.parseInt(parts[1]);
                    } else {
                        System.out.println("Invalid format. Use something like '6_5'.");
                        continue;
                    }
                } else {
                    // Only scopeID provided
                    scopeID = Integer.parseInt(userInput);
                }

                // Validate scopeID range
                if (scopeID >= 1 && scopeID <= 32) {
                    isValid = true;
                } else {
                    System.out.println("Invalid input — scopeID must be between 1 and 32.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input — please enter a number or a valid 'number_number' format.");
            }
        }

        switch (scopeID) {

            // ===== COUNTRIES =====
            case 1:
                reportAllCountriesByPopulationWorld();
                break;
            case 2:
                reportAllCountriesByPopulationContinent();
                break;
            case 3:
                reportAllCountriesByPopulationRegion();
                break;
            case 4:
                reportTopNCountriesByPopulationWorld(parameter);
                break;
            case 5:
                reportTopNCountriesByPopulationContinent(parameter);
                break;
            case 6:
                reportTopNCountriesByPopulationRegion(parameter);
                break;

            // ===== CITIES =====
            case 7:
                reportAllCitiesByPopulationWorld();
                break;
            case 8:
                reportAllCitiesByPopulationContinent();
                break;
            case 9:
                reportAllCitiesByPopulationRegion();
                break;
            case 10:
                reportAllCitiesByPopulationCountry();
                break;
            case 11:
                reportAllCitiesByPopulationDistrict();
                break;
            case 12:
                reportTopNCitiesByPopulationWorld(parameter);
                break;
            case 13:
                reportTopNCitiesByPopulationContinent(parameter);
                break;
            case 14:
                reportTopNCitiesByPopulationRegion(parameter);
                break;
            case 15:
                reportTopNCitiesByPopulationCountry(parameter);
                break;
            case 16:
                reportTopNCitiesByPopulationDistrict(parameter);
                break;

            // ===== CAPITAL CITIES =====
            case 17:
                reportAllCapitalCitiesByPopulationWorld();
                break;
            case 18:
                reportAllCapitalCitiesByPopulationContinent();
                break;
            case 19:
                reportAllCapitalCitiesByPopulationRegion();
                break;
            case 20:
                reportTopNCapitalCitiesByPopulationWorld(parameter);
                break;
            case 21:
                reportTopNCapitalCitiesByPopulationContinent(parameter);
                break;
            case 22:
                reportTopNCapitalCitiesByPopulationRegion(parameter);
                break;

            // ===== POPULATION DISTRIBUTIONS =====
            case 23:
                reportPopulationInEachContinent();
                break;
            case 24:
                reportPopulationInEachRegion();
                break;
            case 25:
                reportPopulationInEachCountry();
                break;

            // ===== SPECIFIC POPULATIONS =====
            case 26:
                reportWorldPopulation();
                break;
            case 27:
                reportPopulationByContinent();
                break;
            case 28:
                reportPopulationByRegion();
                break;
            case 29:
                reportPopulationByCountry();
                break;
            case 30:
                reportPopulationByDistrict();
                break;
            case 31:
                reportPopulationByCity();
                break;

            // ===== LANGUAGE POPULATIONS =====
            case 32:
                reportLanguageSpeakers();  // covers Chinese, English, Hindi, Spanish, Arabic
                break;

            // ===== INVALID INPUT =====
            default:
                System.out.println("Invalid scopeID. Please enter a valid report number.");
                break;
        }
    }

    // ===== COUNTRIES =====

    // All the countries in the world organised by largest population to smallest
    public static void reportAllCountriesByPopulationWorld() {
        String sql = """
        SELECT Code, Name, Continent, Region, Population, Capital
        FROM country
        ORDER BY Population DESC;
    """;
    }

    // All the countries in a continent organised by largest population to smallest
    public static void reportAllCountriesByPopulationContinent() {
        String sql = """
        SELECT Code, Name, Continent, Region, Population, Capital
        FROM country
        WHERE Continent = ?
        ORDER BY Population DESC;
    """;
    }

    // All the countries in a region organised by largest population to smallest
    public static void reportAllCountriesByPopulationRegion() {
        String sql = """
        SELECT Code, Name, Continent, Region, Population, Capital
        FROM country
        WHERE Region = ?
        ORDER BY Population DESC;
    """;
    }

    // Top N populated countries in the world
    public static void reportTopNCountriesByPopulationWorld(int n) {
        String sql = """
        SELECT Code, Name, Continent, Region, Population, Capital
        FROM country
        ORDER BY Population DESC
        LIMIT ?;
    """;
    }

    // Top N populated countries in a continent
    public static void reportTopNCountriesByPopulationContinent(int n) {
        String sql = """
        SELECT Code, Name, Continent, Region, Population, Capital
        FROM country
        WHERE Continent = ?
        ORDER BY Population DESC
        LIMIT ?;
    """;
    }

    // Top N populated countries in a region
    public static void reportTopNCountriesByPopulationRegion(int n) {
        String sql = """
        SELECT Code, Name, Continent, Region, Population, Capital
        FROM country
        WHERE Region = ?
        ORDER BY Population DESC
        LIMIT ?;
    """;
    }


    // ===== CITIES =====

    // All cities in the world organised by largest population to smallest
    public static void reportAllCitiesByPopulationWorld() {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.District, city.Population
        FROM city
        JOIN country ON city.CountryCode = country.Code
        ORDER BY city.Population DESC;
    """;
    }

    // All cities in a continent organised by largest population to smallest
    public static void reportAllCitiesByPopulationContinent() {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.District, city.Population
        FROM city
        JOIN country ON city.CountryCode = country.Code
        WHERE country.Continent = ?
        ORDER BY city.Population DESC;
    """;
    }

    // All cities in a region organised by largest population to smallest
    public static void reportAllCitiesByPopulationRegion() {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.District, city.Population
        FROM city
        JOIN country ON city.CountryCode = country.Code
        WHERE country.Region = ?
        ORDER BY city.Population DESC;
    """;
    }

    // All cities in a country organised by largest population to smallest
    public static void reportAllCitiesByPopulationCountry() {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.District, city.Population
        FROM city
        JOIN country ON city.CountryCode = country.Code
        WHERE country.Name = ?
        ORDER BY city.Population DESC;
    """;
    }

    // All cities in a district organised by largest population to smallest
    public static void reportAllCitiesByPopulationDistrict() {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.District, city.Population
        FROM city
        JOIN country ON city.CountryCode = country.Code
        WHERE city.District = ?
        ORDER BY city.Population DESC;
    """;
    }

    // Top N populated cities in the world
    public static void reportTopNCitiesByPopulationWorld(int n) {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.District, city.Population
        FROM city
        JOIN country ON city.CountryCode = country.Code
        ORDER BY city.Population DESC
        LIMIT ?;
    """;
    }

    // Top N populated cities in a continent
    public static void reportTopNCitiesByPopulationContinent(int n) {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.District, city.Population
        FROM city
        JOIN country ON city.CountryCode = country.Code
        WHERE country.Continent = ?
        ORDER BY city.Population DESC
        LIMIT ?;
    """;
    }

    // Top N populated cities in a region
    public static void reportTopNCitiesByPopulationRegion(int n) {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.District, city.Population
        FROM city
        JOIN country ON city.CountryCode = country.Code
        WHERE country.Region = ?
        ORDER BY city.Population DESC
        LIMIT ?;
    """;
    }

    // Top N populated cities in a country
    public static void reportTopNCitiesByPopulationCountry(int n) {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.District, city.Population
        FROM city
        JOIN country ON city.CountryCode = country.Code
        WHERE country.Name = ?
        ORDER BY city.Population DESC
        LIMIT ?;
    """;
    }

    // Top N populated cities in a district
    public static void reportTopNCitiesByPopulationDistrict(int n) {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.District, city.Population
        FROM city
        JOIN country ON city.CountryCode = country.Code
        WHERE city.District = ?
        ORDER BY city.Population DESC
        LIMIT ?;
    """;
    }


    // ===== CAPITAL CITIES =====

    // All capital cities in the world organised by population
    public static void reportAllCapitalCitiesByPopulationWorld() {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.Population
        FROM city
        JOIN country ON city.ID = country.Capital
        ORDER BY city.Population DESC;
    """;
    }

    // All capital cities in a continent organised by population
    public static void reportAllCapitalCitiesByPopulationContinent() {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.Population
        FROM city
        JOIN country ON city.ID = country.Capital
        WHERE country.Continent = ?
        ORDER BY city.Population DESC;
    """;
    }

    // All capital cities in a region organised by population
    public static void reportAllCapitalCitiesByPopulationRegion() {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.Population
        FROM city
        JOIN country ON city.ID = country.Capital
        WHERE country.Region = ?
        ORDER BY city.Population DESC;
    """;
    }

    // Top N populated capital cities in the world
    public static void reportTopNCapitalCitiesByPopulationWorld(int n) {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.Population
        FROM city
        JOIN country ON city.ID = country.Capital
        ORDER BY city.Population DESC
        LIMIT ?;
    """;
    }

    // Top N populated capital cities in a continent
    public static void reportTopNCapitalCitiesByPopulationContinent(int n) {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.Population
        FROM city
        JOIN country ON city.ID = country.Capital
        WHERE country.Continent = ?
        ORDER BY city.Population DESC
        LIMIT ?;
    """;
    }

    // Top N populated capital cities in a region
    public static void reportTopNCapitalCitiesByPopulationRegion(int n) {
        String sql = """
        SELECT city.Name, country.Name AS Country, city.Population
        FROM city
        JOIN country ON city.ID = country.Capital
        WHERE country.Region = ?
        ORDER BY city.Population DESC
        LIMIT ?;
    """;
    }


    // ===== POPULATION DISTRIBUTIONS =====

    // Population in each continent (total, in cities, not in cities)
    public static void reportPopulationInEachContinent() {
        String sql = """
        SELECT 
            country.Continent AS Name,
            SUM(country.Population) AS TotalPopulation,
            SUM(city.Population) AS CityPopulation,
            (SUM(city.Population) / SUM(country.Population)) * 100 AS CityPercentage,
            (SUM(country.Population) - SUM(city.Population)) AS NonCityPopulation,
            ((SUM(country.Population) - SUM(city.Population)) / SUM(country.Population)) * 100 AS NonCityPercentage
        FROM country
        LEFT JOIN city ON country.Code = city.CountryCode
        GROUP BY country.Continent;
    """;
    }

    // Population in each region
    public static void reportPopulationInEachRegion() {
        String sql = """
        SELECT 
            country.Region AS Name,
            SUM(country.Population) AS TotalPopulation,
            SUM(city.Population) AS CityPopulation,
            (SUM(city.Population) / SUM(country.Population)) * 100 AS CityPercentage,
            (SUM(country.Population) - SUM(city.Population)) AS NonCityPopulation,
            ((SUM(country.Population) - SUM(city.Population)) / SUM(country.Population)) * 100 AS NonCityPercentage
        FROM country
        LEFT JOIN city ON country.Code = city.CountryCode
        GROUP BY country.Region;
    """;
    }

    // Population in each country
    public static void reportPopulationInEachCountry() {
        String sql = """
        SELECT 
            country.Name AS Name,
            country.Population AS TotalPopulation,
            SUM(city.Population) AS CityPopulation,
            (SUM(city.Population) / country.Population) * 100 AS CityPercentage,
            (country.Population - SUM(city.Population)) AS NonCityPopulation,
            ((country.Population - SUM(city.Population)) / country.Population) * 100 AS NonCityPercentage
        FROM country
        LEFT JOIN city ON country.Code = city.CountryCode
        GROUP BY country.Name, country.Population;
    """;
    }

    // ===== SPECIFIC POPULATIONS =====

    // Population of the world
    public static void reportWorldPopulation() {
        String sql = """
        SELECT SUM(Population) AS WorldPopulation
        FROM country;
    """;
    }

    // Population of a continent
    public static void reportPopulationByContinent() {
        String sql = """
        SELECT Continent, SUM(Population) AS ContinentPopulation
        FROM country
        WHERE Continent = ?
        GROUP BY Continent;
    """;
    }

    // Population of a region
    public static void reportPopulationByRegion() {
        String sql = """
        SELECT Region, SUM(Population) AS RegionPopulation
        FROM country
        WHERE Region = ?
        GROUP BY Region;
    """;
    }

    // Population of a country
    public static void reportPopulationByCountry() {
        String sql = """
        SELECT Name AS Country, Population AS CountryPopulation
        FROM country
        WHERE Name = ?;
    """;
    }

    // Population of a district
    public static void reportPopulationByDistrict() {
        String sql = """
        SELECT District, SUM(Population) AS DistrictPopulation
        FROM city
        WHERE District = ?
        GROUP BY District;
    """;
    }

    // Population of a city
    public static void reportPopulationByCity() {
        String sql = """
        SELECT Name AS City, Population AS CityPopulation
        FROM city
        WHERE Name = ?;
    """;
    }


    // ===== LANGUAGES =====

    // People who speak specific languages, with percentage of world population
    public static void reportLanguageSpeakers() {
        String sql = """
        SELECT 
            countrylanguage.Language,
            SUM(country.Population * (countrylanguage.Percentage / 100)) AS Speakers,
            (SUM(country.Population * (countrylanguage.Percentage / 100)) / 
                (SELECT SUM(Population) FROM country)) * 100 AS WorldPercentage
        FROM countrylanguage
        JOIN country ON country.Code = countrylanguage.CountryCode
        WHERE countrylanguage.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic')
        GROUP BY countrylanguage.Language
        ORDER BY Speakers DESC;
    """;
    }

    //maybe I can change the voids from each of the queries into string returns so It can return the query
    //static void query () { // SQL query builder
}

