package controller;
import exception.InputMismatchException;
import exception.PermissionDeniedException;
import hotel.CheckIn;
import hotel.Guest;
import hotel.Payment;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import room.IRoom;
import room.NormalRoom;
import room.VIPRoom;
import user.ManagerUser;
import user.ReceptionistUser;
import user.Staff;
import util.InputHandler;

public class Main {
    public static void main(String... args) {
        
        // Build sample data so the app can run immediately.
        Hotel hotel = new Hotel("Sunrise Hotel", "Phnom Penh", "012 345 678",10);

        IRoom nRoom1 = new NormalRoom("A101", 70.00);
        IRoom vRoom1 = new VIPRoom("B202", 150.00);

        Staff staff1 = new ManagerUser("ST001", "Dara", 'M', "086 256 034", "pw123", 1200.00);
        Staff staff2 = new ReceptionistUser("ST002", "Sokha", 'F', "098 765 432", "pw456", 800.00, "12:00 PM - 12:00 AM");

        hotel.addUser(staff1);
        hotel.addUser(staff2);

        Guest guest1 = new Guest( "Vanna", "098 777 666","vanna@gamil.com");
        Guest guest2 = new Guest("Linda", "097 888 555","linda@gmail.com");

        CheckIn booking1 = new CheckIn(guest1, nRoom1, "2026-03-28", 3, staff1, 10.0);
        CheckIn booking2 = new CheckIn(guest2, vRoom1, "2026-03-25", 2, staff2, 15.0);

        // Add rooms, staff, guests, and bookings to the hotel
        hotel.addRoom(nRoom1);
        hotel.addRoom(vRoom1);

        // hotel.addUser(staff1);
        // hotel.addUser(staff2);

        hotel.addGuest(guest1);
        hotel.addGuest(guest2);

        hotel.addBooking(booking1);
        hotel.addBooking(booking2);


        // Main loop: login menu first, then system menu.
        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;
            boolean loggedIn = false;

            while (!exit) {
                if (!loggedIn) {
                // Login screen
                System.out.println("\n========================================");
                System.out.println("   HOTEL MANAGEMENT SYSTEM - LOGIN");
                System.out.println("========================================");
                System.out.println("1. Login");
                System.out.println("2. Exit");
                    int loginChoice = InputHandler.readIntChoice(scanner, "Enter your choice: ");
                    switch (loginChoice) {
                        case 1: {
                            String username;
                            while (true) {
                                System.out.print("Username: ");
                                username = scanner.nextLine();
                                try {
                                    username = InputHandler.parseRequiredText(username, "Username");
                                    if (!hotel.hasUsername(username)) {
                                        System.out.println("Username not found. Please enter correct username.");
                                        continue;
                                    }
                                    break;
                                } catch (InputMismatchException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            }

                            while (true) {
                                String password = InputHandler.readPassword(scanner, "Password: ");
                                try {
                                    password = InputHandler.parseRequiredText(password, "Password");
                                } catch (InputMismatchException ex) {
                                    System.out.println("Password cannot be empty.");
                                    continue;
                                }

                                if (hotel.login(username, password)) {
                                    loggedIn = true;
                                    break;
                                }

                                System.out.println("Invalid password. Please try again.");
                            }
                            
                            break;
                        }
                    case 2: {
                        exit = true;
                        System.out.println("Exiting the system. Goodbye!");
                        break;
                    }
                    default:
                        System.out.println("Invalid choice. Please try again.");
                    }
                } else {
                    System.out.println("\n========================================");
                    System.out.println("   HOTEL MANAGEMENT SYSTEM");
                    System.out.println("========================================");
                    System.out.println("1. Display Room Details");
                    System.out.println("2. Display Guest Information");
                    System.out.println("3. Book a Room");
                    System.out.println("4. Show Staff Information");
                    System.out.println("5. Show Booking Schedule");
                    System.out.println("6. Payment");
                    System.out.println("7. logout");
                    System.out.println("8. Exit");
                    int choice = InputHandler.readIntChoice(scanner, "Enter your choice: ");
                    try {
                    switch (choice) {
                    case 1: {
                        // View all rooms the current user is allowed to see.
                        System.out.println("\n======================================");
                        System.out.println("      ROOM DETAILS");
                        System.out.println("======================================");
                        for (IRoom room : hotel.viewRooms()) {
                            System.out.println(room);
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("\n======================================");
                        System.out.println("      GUEST INFORMATION");
                        System.out.println("======================================");
                        for (String guestDetail : hotel.getGuestInfoWithPaymentStatus()) {
                            System.out.println(guestDetail);
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("\n======================================");
                        System.out.println("      BOOK A ROOM");
                        System.out.println("======================================");

                        ArrayList<IRoom> allRooms = hotel.getAllRooms();
                        ArrayList<String> availableTypes = new ArrayList<>();
                        for (IRoom room : allRooms) {
                            String roomType = room.getRoomType();
                            if (roomType != null && !roomType.trim().isEmpty() && !availableTypes.contains(roomType)) {
                                availableTypes.add(roomType);
                            }
                        }

                        if (availableTypes.isEmpty()) {
                            System.out.println("No room types available in the system.");
                            break;
                        }

                        System.out.println("Available room types:");
                        for (int i = 0; i < availableTypes.size(); i++) {
                            System.out.println((i + 1) + ". " + availableTypes.get(i));
                        }

                        int roomTypeChoice;
                        while (true) {
                            roomTypeChoice = InputHandler.readIntChoice(
                                scanner,
                                "Enter room type that you want to book (1-" + availableTypes.size() + "): "
                            );
                            if (roomTypeChoice >= 1 && roomTypeChoice <= availableTypes.size()) {
                                break;
                            }
                            System.out.println("Invalid choice. Please enter a number from 1 to " + availableTypes.size() + ".");
                        }

                        String type = availableTypes.get(roomTypeChoice - 1);
                        LocalDate selectedBookingDate;
                        ArrayList<IRoom> bookableRooms;

                        while (true) {
                            try {
                                System.out.print("Enter booking date (yyyy-MM-dd): ");
                                String inputDate = scanner.nextLine();
                                selectedBookingDate = InputHandler.parseDateInput(inputDate);
                                if (selectedBookingDate.isBefore(LocalDate.now())) {
                                    throw new InputMismatchException("Booking date cannot be in the past.");
                                }

                                bookableRooms = hotel.findBookableRoomsByDate(type, selectedBookingDate);
                                if (bookableRooms.isEmpty()) {
                                    System.out.println("No available rooms of type '" + type + "' on " + selectedBookingDate + ".");
                                    continue;
                                }

                                System.out.println("Available rooms of type '" + type + "' on " + selectedBookingDate + " are: \n");
                                for (IRoom room : bookableRooms) {
                                    System.out.println(room);
                                }
                                break;
                            } catch (InputMismatchException ex) {
                                System.out.println(ex.getMessage());
                            }
                        }

                        String roomNumber;
                        while (true) {
                            System.out.print("Enter room number to book: ");
                            roomNumber = scanner.nextLine();
                            try {
                                roomNumber = InputHandler.parseRequiredText(roomNumber, "Room number");
                            } catch (InputMismatchException ex) {
                                System.out.println(ex.getMessage());
                                continue;
                            }

                            boolean validRoomNumber = false;
                            for (IRoom room : bookableRooms) {
                                if (room.getRoomNumber().equals(roomNumber.trim())) {
                                    validRoomNumber = true;
                                    roomNumber = room.getRoomNumber();
                                    break;
                                }
                            }

                            if (validRoomNumber) {
                                break;
                            }

                            System.out.println("Invalid room number. Please choose from the listed available rooms.");
                        }

                        System.out.print("Enter guest name: ");
                        String guestName = scanner.nextLine();

                        CheckIn booking;
                        try {
                            booking = hotel.bookRoomByNumber(roomNumber, guestName, selectedBookingDate);
                        } catch (InputMismatchException ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("Booking failed.");
                            break;
                        }

                        if (booking == null) {
                            System.out.println("Booking failed.");
                        } else {
                            System.out.println("Room " + booking.getRoom().getRoomNumber() + " is booked.");
                            System.out.println("Book is successful.");

                            while (true) {
                                int payNowChoice = InputHandler.readIntChoice(scanner, "Pay now? (1. Yes, 2. Later): ");
                                if (payNowChoice == 1) {
                                    int methodChoice;
                                    while (true) {
                                        methodChoice = InputHandler.readIntChoice(scanner, "Enter payment method (1. Cash, 2. Card): ");
                                        if (methodChoice == 1 || methodChoice == 2) {
                                            break;
                                        }
                                        System.out.println("Invalid payment method choice. Please enter 1 or 2.");
                                    }

                                    try {
                                        if (hotel.payBooking(booking.getBookingCode(), methodChoice)) {
                                            System.out.println("Payment successful for booking " + booking.getBookingCode() + ".");
                                        } else {
                                            System.out.println("Payment not completed.");
                                        }
                                    } catch (InputMismatchException ex) {
                                        System.out.println(ex.getMessage());
                                    }
                                    break;
                                }

                                if (payNowChoice == 2) {
                                    System.out.println("Payment is pending.");
                                    break;
                                }

                                System.out.println("Invalid choice. Please enter 1 or 2.");
                            }
                        }
                        
                        break;
                    }
                    case 4: {
                        System.out.println("\n======================================");
                        System.out.println("      STAFF INFORMATION");
                        System.out.println("======================================");
                        user.StaffAction action = (staff) -> System.out.println(staff);//lambda expression to print staff info
                        for (user.IStaff staff : hotel.viewStaff()) {
                            action.execute(staff);
                        }

                        break;
                    }
                    case 5: {
                        System.out.println("\n======================================");
                        System.out.println("      BOOKING SCHEDULE");
                        System.out.println("======================================");
                        hotel.displayWeeklySchedule();
                        break;
                    }
                    case 6: {
                        System.out.println("\n======================================");
                        System.out.println("      PAYMENT CENTER");
                        System.out.println("======================================");

                        ArrayList<Payment> pendingPayments = hotel.getPendingPayments();
                        if (pendingPayments.isEmpty()) {
                            System.out.println("No pending payments.");
                            break;
                        }

                        System.out.println("Pending payments:");
                        for (Payment payment : pendingPayments) {
                            System.out.println(payment);
                        }

                        while (true) {
                            String bookingCode;
                            while (true) {
                                System.out.print("Enter booking code to pay: ");
                                bookingCode = scanner.nextLine();
                                try {
                                    bookingCode = InputHandler.parseRequiredText(bookingCode, "Booking code");
                                    hotel.validatePayableBookingCode(bookingCode);
                                    break;
                                } catch (InputMismatchException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            }

                            int methodChoice;
                            while (true) {
                                methodChoice = InputHandler.readIntChoice(scanner, "Enter payment method (1. Cash, 2. Card): ");
                                if (methodChoice == 1 || methodChoice == 2) {
                                    break;
                                }
                                System.out.println("Invalid payment method choice. Please enter 1 or 2.");
                            }

                            try {
                                if (hotel.payBooking(bookingCode, methodChoice)) {
                                    System.out.println("Payment successful.");
                                } else {
                                    System.out.println("Payment failed.");
                                }
                                break;
                            } catch (InputMismatchException ex) {
                                System.out.println(ex.getMessage());
                                System.out.println("Please try again.");
                            }
                        }

                        break;
                    }
                    case 7: {
                        hotel.logout();
                        loggedIn = false;
                        break;
                    }
                    case 8: {
                        exit = true;
                        System.out.println("\nExiting the system. Goodbye!");
                        break;
                    }
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                } catch (PermissionDeniedException ex) {
                    System.out.println(ex.getMessage());
                }
                finally {
                    System.out.println("\nThank you for using the hotel management system.");
                }
            }
        }
        }
    }
}
    
