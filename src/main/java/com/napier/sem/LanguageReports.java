package com.napier.sem;

/**
 * Handles language-related population reports
 */
public class LanguageReports {

    /**
     * Number of people who speak Chinese, English, Hindi, Spanish, and Arabic
     * from greatest to smallest, including percentage of world population
     */
    public static String languageSpeakers() {
        return """
            SELECT RPAD(countrylanguage.Language, 15, ' ')          AS Language,
                   LPAD(ROUND(SUM(country.Population * 
                        (countrylanguage.Percentage / 100)), 0), 
                        15, ' ')                                    AS Speakers,
                   LPAD(ROUND((SUM(country.Population * 
                        (countrylanguage.Percentage / 100)) /
                        (SELECT SUM(Population) FROM country)) * 
                        100, 2), 10, ' ')                           AS WorldPercent
            FROM   countrylanguage
            JOIN   country ON country.Code = countrylanguage.CountryCode
            WHERE  countrylanguage.Language IN ('Chinese', 'English', 'Hindi', 
                                                 'Spanish', 'Arabic')
            GROUP BY countrylanguage.Language
            ORDER BY Speakers DESC;
            """;
    }
}