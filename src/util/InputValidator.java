package util;

import java.util.Scanner;
import java.util.InputMismatchException;

public class InputValidator {
    // Keep the scanner static but private
    private static final Scanner scan = new Scanner(System.in);

    /**
     * Reads a string from the console. 
     * We use this for names and descriptions.
     */
    public static String readString(String prompt) {
        System.out.print(prompt);
        return scan.nextLine();
    }

    /**
     * Reads an integer with built-in error handling.
     * This prevents the program from crashing if a user types text.
     */
    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scan.nextInt();
                scan.nextLine(); // Consume the leftover newline character
                return value;
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a whole number.");
                scan.nextLine(); // Clear the "trash" input from the scanner buffer
            }
        }
    }

    /**
     * Reads a double for currency/amounts with safety checks.
     */
    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = scan.nextDouble();
                scan.nextLine(); // Consume the leftover newline character
                return value;
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid amount. Please enter a decimal number (e.g., 10.50).");
                scan.nextLine(); // Clear the buffer
            }
        }
    }

    public static void closeScanner() {
    scan.close(); // Only call this when the app is SHUTTING DOWN
}
}