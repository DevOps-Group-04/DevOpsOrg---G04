package com.napier.sem;
import java.util.Scanner;
import java.util.InputMismatchException;


// !!! - This should be reused, NOT just for country reports
// SQL queries are needed
// Scanner needs removed and an actual interface is needed once queries are working

public class countryReport {

    int scopeID = 0;
    String scopeString = "";
    int topN = 0;

    void getScope() { // To select the scope of the report
        Scanner input = new Scanner(System.in);
        boolean isValid = false;

        while (!isValid) { // 1. Initial scope selection
            try {
                System.out.print("Enter scope of report - 'world', 'region', or 'region' (1, 2, 3): ");
                scopeID = input.nextInt();
                if (scopeID == 1 || scopeID == 2 || scopeID == 3) {
                    isValid = true;
                } else {
                    System.out.println("Invalid input - please enter 1, 2, or 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input - please enter a number");
                input.nextLine();
            }
        }

        isValid = false;
        while (!isValid) { // 2. User enters top N
            try {
                System.out.print("Report is sorted by population, please enter N: ");
                topN = input.nextInt();
                if (topN > 0) {
                    isValid = true;
                } else {
                    System.out.println("Invalid input - please enter a number greater than 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input - please enter a number");
                input.nextLine();
            }

        // UNFINISHED AS DATABASE IS NEEDED TO DISPLAY TOP N OPTIONS
        switch(scopeID) { // 3. User is to select the continent/region for the report
            case 1:
                scopeString = "world";
                break;
            case 2:
                scopeString = "region";
                break;
            case 3:
                scopeString = "country";
                break;
        }

    }

    //static void query () { // SQL query builder

    }
}
