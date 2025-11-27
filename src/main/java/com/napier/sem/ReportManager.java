package com.napier.sem;

import java.util.Scanner;

/**
 * Main report manager that coordinates all report generation
 */
public class ReportManager {

    private static final int DEFAULT_LIMIT = 20;
    private DatabaseConnection db;

    public ReportManager(DatabaseConnection db) {
        this.db = db;
    }

    /**
     * Main entry point
     */
    public static void main(String[] args) {
        // Create database connection
        DatabaseConnection db = new DatabaseConnection();

        // Connect to database (true = local, false = Docker)
        // Change to false when running in Docker environment
        db.connect(false);

        // Create report manager
        ReportManager manager = new ReportManager(db);

        // Get user input and generate reports
        manager.generateAllReports();

        // Disconnect from database
        db.disconnect();
    }

    /**
     * Get user input and generate the appropriate report
     */
    public void generateReportFromUserInput() {
        Scanner input = new Scanner(System.in);
        boolean isValid = false;

        while (!isValid) {
            try {
                System.out.println("\n" + "=".repeat(80));
                System.out.println("WORLD DATABASE REPORTING SYSTEM");
                System.out.println("=".repeat(80));
                System.out.println("\nAvailable Reports:");
                System.out.println("  1-6:   Country Reports");
                System.out.println("  7-16:  City Reports");
                System.out.println("  17-22: Capital City Reports");
                System.out.println("  23-25: Population Distribution Reports");
                System.out.println("  26-31: Specific Population Queries");
                System.out.println("  32:    Language Speaker Statistics");
                System.out.println("\nOptions:");
                System.out.println("  - Press ENTER to generate all 32 reports");
                System.out.println("  - Enter a number (1-32) for a specific report");
                System.out.println("  - Enter 'number_limit' (e.g., '4_50') for top N reports");
                System.out.println("=".repeat(80));
                System.out.print("\nYour choice: ");

                String userInput = input.nextLine().trim();

                // Handle empty input - run all reports
                if (userInput.isEmpty()) {
                    System.out.println("\nGenerating all reports with limit of " + DEFAULT_LIMIT + "...");
                    generateAllReports();
                    return;
                }

                // Parse input
                int scopeID;
                int parameter = -1;

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
                    String sql = getReportSQL(scopeID, parameter);
                    db.executeAndDisplay(sql, scopeID);
                    isValid = true;
                } else {
                    System.out.println("Invalid input — scopeID must be between 1 and 32.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input — please enter a number or 'number_number' format.");
            }
        }
    }

    /**
     * Get SQL for a specific report
     */
    private String getReportSQL(int scopeID, int parameter) {
        int limit = (parameter > 0) ? parameter : DEFAULT_LIMIT;

        return switch (scopeID) {
            // COUNTRIES
            case 1 -> CountryReports.allCountriesWorld();
            case 2 -> CountryReports.allCountriesContinent();
            case 3 -> CountryReports.allCountriesRegion();
            case 4 -> CountryReports.topNCountriesWorld(limit);
            case 5 -> CountryReports.topNCountriesContinent(limit);
            case 6 -> CountryReports.topNCountriesRegion(limit);

            // CITIES
            case 7 -> CityReports.allCitiesWorld();
            case 8 -> CityReports.allCitiesContinent();
            case 9 -> CityReports.allCitiesRegion();
            case 10 -> CityReports.allCitiesCountry();
            case 11 -> CityReports.allCitiesDistrict();
            case 12 -> CityReports.topNCitiesWorld(limit);
            case 13 -> CityReports.topNCitiesContinent(limit);
            case 14 -> CityReports.topNCitiesRegion(limit);
            case 15 -> CityReports.topNCitiesCountry(limit);
            case 16 -> CityReports.topNCitiesDistrict(limit);

            // CAPITAL CITIES
            case 17 -> CapitalCityReports.allCapitalsWorld();
            case 18 -> CapitalCityReports.allCapitalsContinent();
            case 19 -> CapitalCityReports.allCapitalsRegion();
            case 20 -> CapitalCityReports.topNCapitalsWorld(limit);
            case 21 -> CapitalCityReports.topNCapitalsContinent(limit);
            case 22 -> CapitalCityReports.topNCapitalsRegion(limit);

            // POPULATION DISTRIBUTIONS
            case 23 -> PopulationReports.populationByContinent();
            case 24 -> PopulationReports.populationByRegion();
            case 25 -> PopulationReports.populationByCountry();

            // SPECIFIC POPULATIONS
            case 26 -> WorldReports.worldPopulation();
            case 27 -> WorldReports.continentPopulation();
            case 28 -> WorldReports.regionPopulation();
            case 29 -> WorldReports.countryPopulation();
            case 30 -> WorldReports.districtPopulation();
            case 31 -> WorldReports.cityPopulation();

            // LANGUAGES
            case 32 -> LanguageReports.languageSpeakers();

            default -> "Invalid scopeID. Please enter a valid report number (1-32).";
        };
    }

    /**
     * Generate all reports with default limit and execute them against the database
     */
    public void generateAllReports() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("GENERATING ALL REPORTS");
        System.out.println("=".repeat(80));

        for (int i = 1; i <= 32; i++) {
            String sql = getReportSQL(i, DEFAULT_LIMIT);

            // Display report description
            System.out.println("\n" + "=".repeat(80));
            System.out.println("REPORT #" + i + ": " + getReportDescription(i));
            System.out.println("=".repeat(80));

            // Execute the query
            db.executeAndDisplay(sql, i);

            // Small pause between reports for readability
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Ignore
            }
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALL REPORTS COMPLETED");
        System.out.println("=".repeat(80));
    }

    /**
     * Get a human-readable description for each report
     */
    private String getReportDescription(int reportId) {
        return switch (reportId) {
            case 1 -> "All Countries (World) by Population";
            case 2 -> "All Countries (Continent) by Population";
            case 3 -> "All Countries (Region) by Population";
            case 4 -> "Top N Countries (World) by Population";
            case 5 -> "Top N Countries (Continent) by Population";
            case 6 -> "Top N Countries (Region) by Population";
            case 7 -> "All Cities (World) by Population";
            case 8 -> "All Cities (Continent) by Population";
            case 9 -> "All Cities (Region) by Population";
            case 10 -> "All Cities (Country) by Population";
            case 11 -> "All Cities (District) by Population";
            case 12 -> "Top N Cities (World) by Population";
            case 13 -> "Top N Cities (Continent) by Population";
            case 14 -> "Top N Cities (Region) by Population";
            case 15 -> "Top N Cities (Country) by Population";
            case 16 -> "Top N Cities (District) by Population";
            case 17 -> "All Capital Cities (World) by Population";
            case 18 -> "All Capital Cities (Continent) by Population";
            case 19 -> "All Capital Cities (Region) by Population";
            case 20 -> "Top N Capital Cities (World) by Population";
            case 21 -> "Top N Capital Cities (Continent) by Population";
            case 22 -> "Top N Capital Cities (Region) by Population";
            case 23 -> "Population Distribution by Continent";
            case 24 -> "Population Distribution by Region";
            case 25 -> "Population Distribution by Country";
            case 26 -> "World Population";
            case 27 -> "Continent Population";
            case 28 -> "Region Population";
            case 29 -> "Country Population";
            case 30 -> "District Population";
            case 31 -> "City Population";
            case 32 -> "Language Speakers (Chinese, English, Hindi, Spanish, Arabic)";
            default -> "Unknown Report";
        };
    }
}
//
///**
//package com.napier.sem;
//
//import java.util.Scanner;
//
///**
// * Main report manager that coordinates all report generation
// */
//public class ReportManager {
//
//    private static final int DEFAULT_LIMIT = 200;
//
//    public static void main(String[] args) {
//        generateReportFromUserInput();
//    }
//
//    /**
//     * Get user input and generate the appropriate report
//     */
//    public static void generateReportFromUserInput() {
//        Scanner input = new Scanner(System.in);
//        boolean isValid = false;
//
//        while (!isValid) {
//            try {
//                System.out.print("Enter a value between 1 and 32 (or press Enter for all reports): ");
//                String userInput = input.nextLine().trim();
//
//                // Handle empty input - run all reports
//                if (userInput.isEmpty()) {
//                    System.out.println("Running all reports with limit of " + DEFAULT_LIMIT + "...\n");
//                    generateAllReports();
//                    return;
//                }
//
//                // Parse input
//                int scopeID;
//                int parameter = -1;
//
//                if (userInput.contains("_")) {
//                    String[] parts = userInput.split("_");
//                    if (parts.length == 2) {
//                        scopeID = Integer.parseInt(parts[0]);
//                        parameter = Integer.parseInt(parts[1]);
//                    } else {
//                        System.out.println("Invalid format. Use something like '6_5'.");
//                        continue;
//                    }
//                } else {
//                    scopeID = Integer.parseInt(userInput);
//                }
//
//                if (scopeID >= 1 && scopeID <= 32) {
//                    String sql = getReportSQL(scopeID, parameter);
//                    System.out.println("\nGenerated SQL:\n" + sql);
//                    isValid = true;
//                } else {
//                    System.out.println("Invalid input — scopeID must be between 1 and 32.");
//                }
//
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid input — please enter a number or 'number_number' format.");
//            }
//        }
//    }
//
//    /**
//     * Get SQL for a specific report
//     */
//    private static String getReportSQL(int scopeID, int parameter) {
//        int limit = (parameter > 0) ? parameter : DEFAULT_LIMIT;
//
//        return switch (scopeID) {
//            // COUNTRIES
//            case 1 -> CountryReports.allCountriesWorld();
//            case 2 -> CountryReports.allCountriesContinent();
//            case 3 -> CountryReports.allCountriesRegion();
//            case 4 -> CountryReports.topNCountriesWorld(limit);
//            case 5 -> CountryReports.topNCountriesContinent(limit);
//            case 6 -> CountryReports.topNCountriesRegion(limit);
//
//            // CITIES
//            case 7 -> CityReports.allCitiesWorld();
//            case 8 -> CityReports.allCitiesContinent();
//            case 9 -> CityReports.allCitiesRegion();
//            case 10 -> CityReports.allCitiesCountry();
//            case 11 -> CityReports.allCitiesDistrict();
//            case 12 -> CityReports.topNCitiesWorld(limit);
//            case 13 -> CityReports.topNCitiesContinent(limit);
//            case 14 -> CityReports.topNCitiesRegion(limit);
//            case 15 -> CityReports.topNCitiesCountry(limit);
//            case 16 -> CityReports.topNCitiesDistrict(limit);
//
//            // CAPITAL CITIES
//            case 17 -> CapitalCityReports.allCapitalsWorld();
//            case 18 -> CapitalCityReports.allCapitalsContinent();
//            case 19 -> CapitalCityReports.allCapitalsRegion();
//            case 20 -> CapitalCityReports.topNCapitalsWorld(limit);
//            case 21 -> CapitalCityReports.topNCapitalsContinent(limit);
//            case 22 -> CapitalCityReports.topNCapitalsRegion(limit);
//
//            // POPULATION DISTRIBUTIONS
//            case 23 -> PopulationReports.populationByContinent();
//            case 24 -> PopulationReports.populationByRegion();
//            case 25 -> PopulationReports.populationByCountry();
//
//            // SPECIFIC POPULATIONS
//            case 26 -> WorldReports.worldPopulation();
//            case 27 -> WorldReports.continentPopulation();
//            case 28 -> WorldReports.regionPopulation();
//            case 29 -> WorldReports.countryPopulation();
//            case 30 -> WorldReports.districtPopulation();
//            case 31 -> WorldReports.cityPopulation();
//
//            // LANGUAGES
//            case 32 -> LanguageReports.languageSpeakers();
//
//            default -> "Invalid scopeID. Please enter a valid report number (1-32).";
//        };
//    }
//
//    /**
//     * Generate all reports with default limit
//     */
//    private static void generateAllReports() {
//        for (int i = 1; i <= 32; i++) {
//            System.out.println("=".repeat(80));
//            System.out.println("Report #" + i);
//            System.out.println("=".repeat(80));
//            String sql = getReportSQL(i, DEFAULT_LIMIT);
//            System.out.println(sql);
//            System.out.println();
//        }
//    }
//}
//*/
//
// */
