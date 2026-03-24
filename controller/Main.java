package controller;
import exception.InputMismatchException;
import exception.PermissionDeniedException;
import hotel.CheckIn;
import hotel.Guest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import room.IRoom;
import room.NormalRoom;
import room.VIPRoom;
import util.InputHandler;
import user.ManagerUser;
import user.ReceptionistUser;
import user.Staff;

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
                            System.out.print("Username: ");
                            String username = scanner.nextLine();
                            try{
                                username = InputHandler.parseRequiredText(username, "Username");
                                if (username.matches("\\d+")) {
                                    throw new InputMismatchException("Username cannot be integer. Please try again.");
                                }
                            } catch (InputMismatchException ex) {
                                System.out.println(ex.getMessage());
                                System.out.println("Login failed.");
                                break;
                            }
                            System.out.print("Password: ");
                            String password = scanner.nextLine();
                            try{
                                password = InputHandler.parseRequiredText(password, "Password");
                            } catch (InputMismatchException ex) {
                                System.out.println("Password cannot be empty.");
                                System.out.println("Login failed.");
                                break;
                            }
                            if (hotel.login(username, password)) {
                                loggedIn = true;
                            }else {
                                System.out.println("Invalid username or password. Please try again.");
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
                    // System.out.println("   Role: " + hotel.currentUserRole());
                    System.out.println("========================================");
                    System.out.println("1. Display Room Details");
                    System.out.println("2. Display Guest Information");
                    System.out.println("3. Book a Room");
                    System.out.println("4. Show Staff Information");
                    System.out.println("5. Show Booking Schedule");
                    System.out.println("6. logout");
                    System.out.println("7. Exit");
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
                        for (CheckIn guest : hotel.GuestInfo()) {
                            System.out.println(guest);
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
                        for (String roomType : availableTypes) {
                            System.out.println("- " + roomType);
                        }

                        System.out.print("Enter room type that you want to book: ");
                        String type = scanner.nextLine();
                        LocalDate today = LocalDate.now();
                        ArrayList<IRoom> bookableRooms;

                        try{
                            type = InputHandler.parseRequiredText(type, "Room type");
                            if (type.matches("^-?\\d+$")) {
                                throw new InputMismatchException("Room type cannot be integer. Please try again.");
                            }

                            bookableRooms = hotel.findBookableRoomsByDate(type, today);
                            if(bookableRooms.isEmpty()) {
                                System.out.println("No available rooms of type '" + type + "' for today.");
                                break;
                            }else {
                                System.out.println("Available rooms of type '" + type + "' on " + today + " are: \n");
                                for (IRoom room : bookableRooms) {
                                    System.out.println(room);
                                }
                            }
                        }catch (InputMismatchException ex) {
                            System.out.println(ex.getMessage());
                            System.out.println("Booking failed.");
                            break;
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
                                if (room.getRoomNumber().equalsIgnoreCase(roomNumber.trim())) {
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
                            booking = hotel.bookRoomByNumber(roomNumber, guestName);
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
                        }
                        
                        break;
                    }
                    case 4: {
                        System.out.println("\n======================================");
                        System.out.println("      STAFF INFORMATION");
                        System.out.println("======================================");
                        user.StaffAction action = (staff) -> System.out.println(staff);
                        for (user.IStaff staff : hotel.viewStaff()) {
                            action.execute(staff);
                        }

                        break;
                    }
                    case 5: {
                        System.out.println("\n======================================");
                        System.out.println("      BOOKING SCHEDULE");
                        System.out.println("======================================");
                        System.out.println("1. View next 7 days schedule");
                        System.out.println("2. Search specific date");
                        String scheduleChoice = InputHandler.readScheduleChoice(scanner);

                        if (scheduleChoice.equals("1")) {
                            hotel.displayWeeklySchedule();
                        } else if (scheduleChoice.equals("2")) {
                            System.out.print("Enter date (yyyy-MM-dd): ");
                            String inputDate = scanner.nextLine();

                            LocalDate selectedDate = InputHandler.parseDateInput(inputDate);

                            hotel.displayDaySchedule(selectedDate);
                        } else {
                            System.out.println("Invalid choice.");
                        }
                        break;
                    }
                    case 6: {
                        hotel.logout();
                        loggedIn = false;
                        break;
                    }
                    case 7: {
                        exit = true;
                        System.out.println("\nExiting the system. Goodbye!");
                        break;
                    }
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                } catch (InputMismatchException ex) {
                    System.out.println(ex.getMessage());
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
    
