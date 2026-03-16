package controller;
import hotel.CheckIn;
import hotel.Guest;
import java.math.BigDecimal;
import java.util.Scanner;
import room.IRoom;
import room.NormalRoom;
import room.VIPRoom;
import user.ManagerUser;
import user.ReceptionistUser;
import user.Staff;

public class Main {
    public static void main(String[] args) {
        
        Hotel hotel = new Hotel("Sunrise Hotel", "Phnom Penh", "012 345 678",10);

        IRoom nRoom1 = new NormalRoom("A101", new BigDecimal("70.00"));
        IRoom vRoom1 = new VIPRoom("B202", new BigDecimal("150.00"));

        Staff staff1 = new ManagerUser("ST001", "Dara", 'M', "086 256 034", "pw123", new BigDecimal("1200.00"));
        Staff staff2 = new ReceptionistUser("ST002", "Sokha", 'F', "098 765 432", "pw456", new BigDecimal("800.00"), "12:00 PM - 12:00 AM");

        hotel.addUser(staff1);
        hotel.addUser(staff2);

        Guest guest1 = new Guest( "Vanna", "098 777 666","vanna@gamil.com");
        Guest guest2 = new Guest("Linda", "097 888 555","linda@gmail.com");

        CheckIn booking1 = new CheckIn(guest1, nRoom1, "2024-07-01", 3, staff1, new BigDecimal("10"));
        CheckIn booking2 = new CheckIn(guest2, vRoom1, "2024-07-02", 2, staff2, new BigDecimal("15"));

        // Add rooms, staff, guests, and bookings to the hotel
        hotel.addRoom(nRoom1);
        hotel.addRoom(vRoom1);

        // hotel.addUser(staff1);
        // hotel.addUser(staff2);

        hotel.addGuest(guest1);
        hotel.addGuest(guest2);

        hotel.addBooking(booking1);
        hotel.addBooking(booking2);

        

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
                System.out.print("Enter your choice: ");
                    int loginChoice = scanner.nextInt();                    
                    switch (loginChoice) {
                    case 1 -> {
                            scanner.nextLine();
                            System.out.print("Username: ");
                            String username = scanner.nextLine();
                            System.out.print("Password: ");
                            String password = scanner.nextLine();
                            try {
                                if (hotel.login(username, password)) {
                                    loggedIn = true;
                                }
                            } catch (RuntimeException ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    case 2 -> {
                        exit = true;
                        System.out.println("Exiting the system. Goodbye!");
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
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
                    System.out.println("6. Logout");
                    System.out.println("7. Exit");
                    System.out.print("Enter your choice: ");

                    int choice = scanner.nextInt();
                    switch (choice) {
                    case 1 -> {
                        System.out.println("\n======================================");
                        System.out.println("      ROOM DETAILS");
                        System.out.println("======================================");
                        try {
                            for (IRoom room : hotel.actionViewRooms()) {
                                System.out.println(room);
                            }
                        } catch (RuntimeException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    case 2 -> {
                        System.out.println("\n======================================");
                        System.out.println("      GUEST INFORMATION");
                        System.out.println("======================================");
                        try {
                            for (Guest guest : hotel.actionViewGuests()) {
                                System.out.println(guest);
                            }
                        } catch (RuntimeException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    case 3 -> {
                        System.out.println("\n======================================");
                        System.out.println("      BOOK A ROOM");
                        System.out.println("======================================");
                        scanner.nextLine();
                        System.out.print("Enter room type to search: ");
                        String type = scanner.nextLine();
                        try {
                            for (IRoom room : hotel.actionCreateBooking(type)) {
                                System.out.println(room);
                            }
                        } catch (RuntimeException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    case 4 -> {
                        System.out.println("\n======================================");
                        System.out.println("      STAFF INFORMATION");
                        System.out.println("======================================");
                        try {
                            for (user.IStaff staff : hotel.actionViewStaff()) {
                                System.out.println(staff);
                            }
                        } catch (RuntimeException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    case 5 -> {
                        System.out.println("\n======================================");
                        System.out.println("      BOOKING SCHEDULE");
                        System.out.println("======================================");
                        try {
                            for (String line : hotel.actionViewBookingSchedule()) {
                                System.out.println(line);
                            }
                        } catch (RuntimeException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    case 6 -> {
                        hotel.logout();
                        loggedIn = false;
                    }
                    case 7 -> {
                        exit = true;
                        System.out.println("\nExiting the system. Goodbye!");
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }
        }
    }
}
    
