package controller;

import exception.PermissionDeniedException;
import hotel.CheckIn;
import hotel.Guest;
import java.time.LocalDate;
import java.util.Scanner;
import room.IRoom;
import room.NormalRoom;
import room.VIPRoom;
import user.ManagerUser;
import user.ReceptionistUser;
import user.Staff;
import util.InputHandler;
import util.TextFileStorage;

public class Main {
    public static void main(String... args) {
        Hotel hotel = new Hotel("Sunrise Hotel", "Phnom Penh", "012 345 678", 10);
        TextFileStorage storage = new TextFileStorage();

        boolean loaded = storage.loadAll(hotel);
        if (!loaded) {
            seedSampleData(hotel);
            storage.saveAll(hotel);
        } else if (hotel.getBookingsList().isEmpty()) {
            // If base files exist but bookings are empty, create starter bookings once.
            seedStarterBookings(hotel);
            storage.saveAll(hotel);
        }

        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;
            boolean loggedIn = false;

            while (!exit) {
                if (!loggedIn) {
                    System.out.println("\n========================================");
                    System.out.println("   HOTEL MANAGEMENT SYSTEM - LOGIN");
                    System.out.println("========================================");
                    System.out.println("1. Login");
                    System.out.println("2. Exit");

                    int loginChoice = InputHandler.readIntChoice(scanner, "Enter your choice: ");
                    switch (loginChoice) {
                        case 1:
                            loggedIn = hotel.loginFlow(scanner);
                            break;
                        case 2:
                            exit = true;
                            System.out.println("Exiting the system. Goodbye!");
                            break;
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
                    System.out.println("4. Make Payment");
                    System.out.println("5. Show Booking Schedule");
                    System.out.println("6. Show Staff Information");
                    System.out.println("7. logout");
                    System.out.println("8. Exit");
                    int choice = InputHandler.readIntChoice(scanner, "Enter your choice: ");
                    try {
                        switch (choice) {
                            case 1:
                                System.out.println("\n======================================");
                                System.out.println("      ROOM DETAILS");
                                System.out.println("======================================");
                                for (IRoom room : hotel.viewRooms()) {
                                    System.out.println(room);
                                }
                                break;
                            case 2:
                                System.out.println("\n======================================");
                                System.out.println("      GUEST INFORMATION");
                                System.out.println("======================================");
                                for (String guestDetail : hotel.getGuestInfoWithPaymentStatus()) {
                                    System.out.println(guestDetail);
                                }
                                break;
                            case 3:
                                hotel.bookRoomFlow(scanner);
                                storage.saveAll(hotel);
                                break;
                            case 4:
                                hotel.paymentFlow(scanner);
                                storage.saveAll(hotel);
                                break;
                            case 5:
                                hotel.displayWeeklySchedule();
                                break;
                            case 6:
                                System.out.println("\n======================================");
                                System.out.println("      STAFF INFORMATION");
                                System.out.println("======================================");
                                user.StaffAction action = (staff) -> System.out.println(staff);
                                for (user.IStaff staff : hotel.viewStaff()) {
                                    action.execute(staff);
                                }
                                break;
                            case 7:
                                hotel.logout();
                                loggedIn = false;
                                break;
                            case 8:
                                storage.saveAll(hotel);
                                exit = true;
                                System.out.println("\nExiting the system. Goodbye!");
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    } catch (PermissionDeniedException ex) {
                        System.out.println(ex.getMessage());
                    } finally {
                        System.out.println("\nThank you for using the hotel management system.");
                    }
                }
            }
        }
    }

    private static void seedSampleData(Hotel hotel) {
        // 10 rooms total: 6 normal and 4 VIP.
        IRoom nRoom1 = new NormalRoom("A101", 70.00);
        IRoom nRoom2 = new NormalRoom("A102", 70.00);
        IRoom nRoom3 = new NormalRoom("A103", 70.00);
        IRoom nRoom4 = new NormalRoom("A104", 70.00);
        IRoom nRoom5 = new NormalRoom("A105", 70.00);
        IRoom nRoom6 = new NormalRoom("A106", 70.00);

        IRoom vRoom1 = new VIPRoom("B201", 150.00);
        IRoom vRoom2 = new VIPRoom("B202", 150.00);
        IRoom vRoom3 = new VIPRoom("B203", 150.00);
        IRoom vRoom4 = new VIPRoom("B204", 150.00);

        Staff staff1 = new ManagerUser("ST001", "Dara", 'M', "086 256 034", "pw123", 1200.00);
        Staff staff2 = new ReceptionistUser("ST002", "Sokha", 'F', "098 765 432", "pw456", 800.00, "12:00 PM - 12:00 AM");

        hotel.addUser(staff1);
        hotel.addUser(staff2);

        Guest guest1 = new Guest("Vanna", "098 777 666", "vanna@gamil.com");
        Guest guest2 = new Guest("Linda", "097 888 555", "linda@gmail.com");

        CheckIn booking1 = new CheckIn(guest1, nRoom1, "2026-04-07", 2, staff1, 10.0);
        CheckIn booking2 = new CheckIn(guest2, vRoom1, "2026-04-08", 1, staff2, 0.0);

        hotel.addRoom(nRoom1);
        hotel.addRoom(nRoom2);
        hotel.addRoom(nRoom3);
        hotel.addRoom(nRoom4);
        hotel.addRoom(nRoom5);
        hotel.addRoom(nRoom6);
        hotel.addRoom(vRoom1);
        hotel.addRoom(vRoom2);
        hotel.addRoom(vRoom3);
        hotel.addRoom(vRoom4);
        hotel.addGuest(guest1);
        hotel.addGuest(guest2);
        hotel.addBooking(booking1);
        hotel.addBooking(booking2);
    }

    private static void seedStarterBookings(Hotel hotel) {
        Guest guest1 = hotel.findGuestByIndex(0);
        Guest guest2 = hotel.findGuestByIndex(1);
        IRoom room1 = hotel.findRoomByIndex(0);
        IRoom room2 = hotel.findRoomByIndex(1);
        user.IStaff staff1 = hotel.findStaffByIndex(0);
        user.IStaff staff2 = hotel.findStaffByIndex(1);

        if (guest1 == null || room1 == null || staff1 == null) {
            return;
        }

        CheckIn booking1 = new CheckIn(
            guest1,
            room1,
            LocalDate.now().plusDays(0).toString(),
            2,
            staff1,
            10.0
        );
        hotel.addBooking(booking1);

        if (guest2 != null && room2 != null && staff2 != null) {
            CheckIn booking2 = new CheckIn(
                guest2,
                room2,
                LocalDate.now().plusDays(1).toString(),
                1,
                staff2,
                0.0
            );
            hotel.addBooking(booking2);
        }
    }
}

