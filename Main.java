import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel("Sunrise Hotel", "Phnom Penh", "012 345 678",10);
        
        Room room1 = new Room("A101", "Single", 100);
        Room room2 = new Room("B202", "Double", 150);

        Staff staff1 = new Staff("Dara", "Manager", 'M');
        Staff staff2 = new Staff("Sokha", "Receptionist", 'F');

        Guest guest1 = new Guest( "Vanna", "098 777 666","vanna@gamil.com");
        Guest guest2 = new Guest("Linda", "097 888 555","linda@gmail.com");

        CheckIn booking1 = new CheckIn(guest1, room1, "2024-07-01", 3, staff1, 10);
        CheckIn booking2 = new CheckIn(guest2, room2, "2024-07-02", 2, staff2, 15);

        // Add rooms, staff, guests, and bookings to the hotel
        hotel.addRoom(room1);
        hotel.addRoom(room2);

        hotel.addStaff(staff1);
        hotel.addStaff(staff2);

        hotel.addGuest(guest1);
        hotel.addGuest(guest2);

        hotel.addBooking(booking1);
        hotel.addBooking(booking2);

        hotel.addUser(new ManagerUser("M001", "Dara", "password123"));
        hotel.addUser(new ReceptionistUser("S001", "Sokha", "password456"));

        Scanner scanner = new Scanner(System.in);
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
                    case 1:
                        scanner.nextLine();
                        System.out.print("Username: ");
                        String username = scanner.nextLine();
                        System.out.print("Password: ");
                        String password = scanner.nextLine();
                        if (hotel.login(username, password)) {
                            loggedIn = true;
                        }
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
                System.out.println("   Role: " + hotel.currentUserRole());
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
                    case 1:
                        System.out.println("\n======================================");
                        System.out.println("      ROOM DETAILS");
                        System.out.println("======================================");
                        hotel.actionViewRooms();
                        break;

                    case 2:
                        System.out.println("\n======================================");
                        System.out.println("      GUEST INFORMATION");
                        System.out.println("======================================");
                        hotel.actionViewGuests();
                        break;
                        
                    case 3:
                        System.out.println("\n======================================");
                        System.out.println("      BOOK A ROOM");
                        System.out.println("======================================");
                        scanner.nextLine();
                        System.out.print("Enter room type to search: ");
                        String type = scanner.nextLine();
                        hotel.actionCreateBooking(type);
                        break;

                    case 4:
                        System.out.println("\n======================================");
                        System.out.println("      STAFF INFORMATION");
                        System.out.println("======================================");
                        hotel.actionViewStaff();
                        break;

                    case 5:
                        System.out.println("\n======================================");
                        System.out.println("      BOOKING SCHEDULE");
                        System.out.println("======================================");
                        hotel.actionViewBookingSchedule();
                        break;

                    case 6:
                        hotel.logout();
                        loggedIn = false;
                        break;

                    case 7:
                        exit = true;
                        System.out.println("\nExiting the system. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
    
}
