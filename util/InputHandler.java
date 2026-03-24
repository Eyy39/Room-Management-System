package util;

import exception.InputMismatchException;
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
                System.out.println("Input should be integer. Please try again!");
            }
        }
    }

    public static String readScheduleChoice(Scanner scanner) throws InputMismatchException {
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim();
        if (!choice.equals("1") && !choice.equals("2")) {
            throw new InputMismatchException("Invalid choice. Please enter 1 or 2.");
        }
        return choice;
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

    public static String readRequiredText(Scanner scanner, String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return parseRequiredText(input, fieldName);
            } catch (InputMismatchException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
