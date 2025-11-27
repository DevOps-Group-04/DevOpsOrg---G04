package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LanguageReportsUT {

    @Test
    void languageSpeakers_returnsExpectedSQL() {
        String expected = """
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
        assertEquals(expected, LanguageReports.languageSpeakers());
    }
}