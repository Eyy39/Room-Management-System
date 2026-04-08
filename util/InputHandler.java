package util;

import exception.InputMismatchException;
import java.io.Console;
import java.time.LocalDate;
import java.util.Scanner;

public class InputHandler {
    private InputHandler() {
        // Utility class
    }

    public static int readIntChoice(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            
            if (input == null || input.trim().isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a number.");
                continue;
            }
            
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static LocalDate parseDateInput(String input) throws InputMismatchException {
        if (input == null || input.trim().isEmpty()) {
            throw new InputMismatchException("Date cannot be empty. Please use yyyy-MM-dd.");
        }
        try {
            return LocalDate.parse(input.trim());
        } catch (Exception ex) {
            throw new InputMismatchException("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    public static String parseRequiredText(String input, String fieldName) throws InputMismatchException {
        if (input == null || input.trim().isEmpty()) {
            throw new InputMismatchException(fieldName + " cannot be integer or empty. Please try again.");
        }
        return input.trim();
    }

    public static String readPassword(Scanner scanner, String prompt) {
        Console console = System.console();
        if (console != null) {
            return readPasswordFromConsole(console, prompt);
        }

        return readPasswordFallback(scanner, prompt);
    }

    private static String readPasswordFromConsole(Console console, String prompt) {
        char[] passwordChars = console.readPassword(prompt);// hide password input in console
        if (passwordChars == null) {
            return "";
        }
        return new String(passwordChars).trim();
    }

    private static String readPasswordFallback(Scanner scanner, String prompt) {
        // Fallback for IDE/debug consoles where System.console() is null.
        System.out.println("Hidden password input is not available in this console.");
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
