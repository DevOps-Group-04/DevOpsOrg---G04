package com.napier.sem;
import java.util.Scanner;

public class countryReport {

    // ===== Helper to replace LIMIT ? when n > 0 =====
    private static String applyLimitParameter(String sql, int n) {
        if (n > 0) {
            // Replace only 'LIMIT ?' (not other ? placeholders)
            return sql.replaceAll("(?i)LIMIT\\s*\\?", "LIMIT " + n);
        }
        return sql;
    }

    // ===== Interface for user input =====
    public static String getScope() {
        int scopeID = 0;
        int parameter = -1;
        Scanner input = new Scanner(System.in);
        boolean isValid = false;

        while (!isValid) {
            try {
                System.out.print("Enter a value between 1 and 32. If a specific parameter is needed, add it with '_': ");
                String userInput = input.next();

                if (userInput.contains("_")) {
                    String[] parts = userInput.split("_");
                    if (parts.length == 2) {
                        scopeID = Integer.parseInt(parts[0]);
                        parameter = Integer.parseInt(parts[1]);
                    } else {
                        System.out.println("Invalid format. Use something like '6_5'.");
                        continue;
                    }
                } else {
                    scopeID = Integer.parseInt(userInput);
                }

                if (scopeID >= 1 && scopeID <= 32) {
                    isValid = true;
                } else {
                    System.out.println("Invalid input — scopeID must be between 1 and 32.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input — please enter a number or a valid 'number_number' format.");
            }
        }

        // Generate SQL based on selected scope
        String sql = switch (scopeID) {
            // ===== COUNTRIES =====
            case 1 -> reportAllCountriesByPopulationWorld();
            case 2 -> reportAllCountriesByPopulationContinent();
            case 3 -> reportAllCountriesByPopulationRegion();
            case 4 -> reportTopNCountriesByPopulationWorld(parameter);
            case 5 -> reportTopNCountriesByPopulationContinent(parameter);
            case 6 -> reportTopNCountriesByPopulationRegion(parameter);

            // ===== CITIES =====
            case 7 -> reportAllCitiesByPopulationWorld();
            case 8 -> reportAllCitiesByPopulationContinent();
            case 9 -> reportAllCitiesByPopulationRegion();
            case 10 -> reportAllCitiesByPopulationCountry();
            case 11 -> reportAllCitiesByPopulationDistrict();
            case 12 -> reportTopNCitiesByPopulationWorld(parameter);
            case 13 -> reportTopNCitiesByPopulationContinent(parameter);
            case 14 -> reportTopNCitiesByPopulationRegion(parameter);
            case 15 -> reportTopNCitiesByPopulationCountry(parameter);
            case 16 -> reportTopNCitiesByPopulationDistrict(parameter);

            // ===== CAPITAL CITIES =====
            case 17 -> reportAllCapitalCitiesByPopulationWorld();
            case 18 -> reportAllCapitalCitiesByPopulationContinent();
            case 19 -> reportAllCapitalCitiesByPopulationRegion();
            case 20 -> reportTopNCapitalCitiesByPopulationWorld(parameter);
            case 21 -> reportTopNCapitalCitiesByPopulationContinent(parameter);
            case 22 -> reportTopNCapitalCitiesByPopulationRegion(parameter);

            // ===== POPULATION DISTRIBUTIONS =====
            case 23 -> reportPopulationInEachContinent();
            case 24 -> reportPopulationInEachRegion();
            case 25 -> reportPopulationInEachCountry();

            // ===== SPECIFIC POPULATIONS =====
            case 26 -> reportWorldPopulation();
            case 27 -> reportPopulationByContinent();
            case 28 -> reportPopulationByRegion();
            case 29 -> reportPopulationByCountry();
            case 30 -> reportPopulationByDistrict();
            case 31 -> reportPopulationByCity();

            // ===== LANGUAGE POPULATIONS =====
            case 32 -> reportLanguageSpeakers();

            default -> "Invalid scopeID. Please enter a valid report number.";
        };

        return sql;
    }

    // ===== COUNTRIES =====
    public static String reportAllCountriesByPopulationWorld() {
        return """
            SELECT Code, Name, Continent, Region, Population, Capital
            FROM country
            ORDER BY Population DESC;
        """;
    }

    public static String reportAllCountriesByPopulationContinent() {
        return """
            SELECT Code, Name, Continent, Region, Population, Capital
            FROM country
            WHERE Continent = ?
            ORDER BY Population DESC;
        """;
    }

    public static String reportAllCountriesByPopulationRegion() {
        return """
            SELECT Code, Name, Continent, Region, Population, Capital
            FROM country
            WHERE Region = ?
            ORDER BY Population DESC;
        """;
    }

    public static String reportTopNCountriesByPopulationWorld(int n) {
        String sql = """
            SELECT Code, Name, Continent, Region, Population, Capital
            FROM country
            ORDER BY Population DESC
            LIMIT ?;
        """;
        return applyLimitParameter(sql, n);
    }

    public static String reportTopNCountriesByPopulationContinent(int n) {
        String sql = """
            SELECT Code, Name, Continent, Region, Population, Capital
            FROM country
            WHERE Continent = ?
            ORDER BY Population DESC
            LIMIT ?;
        """;
        return applyLimitParameter(sql, n);
    }

    public static String reportTopNCountriesByPopulationRegion(int n) {
        String sql = """
            SELECT Code, Name, Continent, Region, Population, Capital
            FROM country
            WHERE Region = ?
            ORDER BY Population DESC
            LIMIT ?;
        """;
        return applyLimitParameter(sql, n);
    }

    // ===== CITIES =====
    public static String reportAllCitiesByPopulationWorld() {
        return """
            SELECT city.Name, country.Name AS Country, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            ORDER BY city.Population DESC;
        """;
    }

    public static String reportAllCitiesByPopulationContinent() {
        return """
            SELECT city.Name, country.Name AS Country, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Continent = ?
            ORDER BY city.Population DESC;
        """;
    }

    public static String reportAllCitiesByPopulationRegion() {
        return """
            SELECT city.Name, country.Name AS Country, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Region = ?
            ORDER BY city.Population DESC;
        """;
    }

    public static String reportAllCitiesByPopulationCountry() {
        return """
            SELECT city.Name, country.Name AS Country, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Name = ?
            ORDER BY city.Population DESC;
        """;
    }

    public static String reportAllCitiesByPopulationDistrict() {
        return """
            SELECT city.Name, country.Name AS Country, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE city.District = ?
            ORDER BY city.Population DESC;
        """;
    }

    public static String reportTopNCitiesByPopulationWorld(int n) {
        String sql = """
            SELECT city.Name, country.Name AS Country, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        return applyLimitParameter(sql, n);
    }

    public static String reportTopNCitiesByPopulationContinent(int n) {
        String sql = """
            SELECT city.Name, country.Name AS Country, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Continent = ?
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        return applyLimitParameter(sql, n);
    }

    public static String reportTopNCitiesByPopulationRegion(int n) {
        String sql = """
            SELECT city.Name, country.Name AS Country, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Region = ?
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        return applyLimitParameter(sql, n);
    }

    public static String reportTopNCitiesByPopulationCountry(int n) {
        String sql = """
            SELECT city.Name, country.Name AS Country, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE country.Name = ?
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        return applyLimitParameter(sql, n);
    }

    public static String reportTopNCitiesByPopulationDistrict(int n) {
        String sql = """
            SELECT city.Name, country.Name AS Country, city.District, city.Population
            FROM city
            JOIN country ON city.CountryCode = country.Code
            WHERE city.District = ?
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        return applyLimitParameter(sql, n);
    }

    // ===== CAPITAL CITIES =====
    public static String reportAllCapitalCitiesByPopulationWorld() {
        return """
            SELECT city.Name, country.Name AS Country, city.Population
            FROM city
            JOIN country ON city.ID = country.Capital
            ORDER BY city.Population DESC;
        """;
    }

    public static String reportAllCapitalCitiesByPopulationContinent() {
        return """
            SELECT city.Name, country.Name AS Country, city.Population
            FROM city
            JOIN country ON city.ID = country.Capital
            WHERE country.Continent = ?
            ORDER BY city.Population DESC;
        """;
    }

    public static String reportAllCapitalCitiesByPopulationRegion() {
        return """
            SELECT city.Name, country.Name AS Country, city.Population
            FROM city
            JOIN country ON city.ID = country.Capital
            WHERE country.Region = ?
            ORDER BY city.Population DESC;
        """;
    }

    public static String reportTopNCapitalCitiesByPopulationWorld(int n) {
        String sql = """
            SELECT city.Name, country.Name AS Country, city.Population
            FROM city
            JOIN country ON city.ID = country.Capital
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        return applyLimitParameter(sql, n);
    }

    public static String reportTopNCapitalCitiesByPopulationContinent(int n) {
        String sql = """
            SELECT city.Name, country.Name AS Country, city.Population
            FROM city
            JOIN country ON city.ID = country.Capital
            WHERE country.Continent = ?
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        return applyLimitParameter(sql, n);
    }

    public static String reportTopNCapitalCitiesByPopulationRegion(int n) {
        String sql = """
            SELECT city.Name, country.Name AS Country, city.Population
            FROM city
            JOIN country ON city.ID = country.Capital
            WHERE country.Region = ?
            ORDER BY city.Population DESC
            LIMIT ?;
        """;
        return applyLimitParameter(sql, n);
    }

    // ===== POPULATION REPORTS =====
    public static String reportPopulationInEachContinent() {
        return """
            SELECT country.Continent AS Name,
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

    public static String reportPopulationInEachRegion() {
        return """
            SELECT country.Region AS Name,
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

    public static String reportPopulationInEachCountry() {
        return """
            SELECT country.Name AS Name,
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
    public static String reportWorldPopulation() {
        return "SELECT SUM(Population) AS WorldPopulation FROM country;";
    }

    public static String reportPopulationByContinent() {
        return """
            SELECT Continent, SUM(Population) AS ContinentPopulation
            FROM country
            WHERE Continent = ?
            GROUP BY Continent;
        """;
    }

    public static String reportPopulationByRegion() {
        return """
            SELECT Region, SUM(Population) AS RegionPopulation
            FROM country
            WHERE Region = ?
            GROUP BY Region;
        """;
    }

    public static String reportPopulationByCountry() {
        return """
            SELECT Name AS Country, Population AS CountryPopulation
            FROM country
            WHERE Name = ?;
        """;
    }

    public static String reportPopulationByDistrict() {
        return """
            SELECT District, SUM(Population) AS DistrictPopulation
            FROM city
            WHERE District = ?
            GROUP BY District;
        """;
    }

    public static String reportPopulationByCity() {
        return """
            SELECT Name AS City, Population AS CityPopulation
            FROM city
            WHERE Name = ?;
        """;
    }

    // ===== LANGUAGES =====
    public static String reportLanguageSpeakers() {
        return """
            SELECT countrylanguage.Language,
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
}
