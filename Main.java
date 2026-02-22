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

        hotel.addUser(new ManagerUser("U001", "manager", "123"));
        hotel.addUser(new ReceptionistUser("U002", "reception", "123"));
        hotel.addUser(new AuditorUser("U003", "auditor", "123"));

        // Display hotel information
        hotel.showHotelInfo();

        Scanner scanner = new Scanner(System.in); // Scanner for user input
        int choice;
        boolean exit = false;
        while(!exit){
            System.out.println("\n=========== Hotel Management System ===========\n");
            System.out.println("Current role: " + hotel.currentUserRole());
            System.out.println("0. Login");
            System.out.println("1. Display Room Details");
            System.out.println("2. Display Guest Information");
            System.out.println("3. Book a Room");
            System.out.println("4. Show Staff Information");
            System.out.println("5. Show Booking Schedule");
            System.out.println("6. Logout");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt(); // Read user input
            switch(choice){
                case 0:
                    scanner.nextLine();
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    hotel.login(username, password);
                    break;
                case 1:
                    System.out.println("======================================");
                    System.out.println("      ROOM DETAILS");
                    System.out.println("======================================");
                    hotel.actionViewRooms();
                    break;
            
                case 2:
                    System.out.println("======================================");
                    System.out.println("      GUEST INFORMATION");
                    System.out.println("======================================");
                    hotel.actionViewGuests();
                    break;
                case 3:
                    System.out.println("======================================");
                    System.out.println("      BOOKING ROOM");
                    System.out.println("======================================");
                    scanner.nextLine(); // clear buffer
                    System.out.print("Enter room type to search: ");
                    String type = scanner.nextLine();
                    hotel.actionCreateBooking(type);
                    break; 
                case 4:
                    System.out.println("======================================");
                    System.out.println("      STAFF INFORMATION");
                    System.out.println("======================================");
                    hotel.actionViewStaff();
                    break;
                case 5:
                    System.out.println("======================================");
                    System.out.println("      ROOM BOOKING SCHEDULE");
                    System.out.println("======================================");
                    hotel.actionViewBookingSchedule();
                    break;
                case 6:
                    hotel.logout();
                    break;
                case 7:
                    exit = true; 
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                }
        }
    }
    
}
