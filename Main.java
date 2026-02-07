import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel(10); // Initialize RoomManagement with max 10 rooms
        Guest guest1 = new Guest("Nou Sokleap", "086-456-7890", "nou.sokleap@gmail.com");
        Guest guest2 = new Guest("Lim Dara", "089-123-4567", "lim.dara@gmail.com");
        Staff staff1 = new Staff("Chan Sokha", "Manager", 'M');
        Staff staff2 = new Staff("Sok Aliza", "Receptionist", 'F');
        Booking booking1 = new Booking(guest1, hotel.rooms[0], "2024-10-01", 3, staff2,0);
        Booking booking2 = new Booking(guest1, hotel.rooms[1], "2024-10-05", 2, staff1,10.0);

        Scanner scanner = new Scanner(System.in); // Scanner for user input
        int choice;
        boolean exit = false;
        while(!exit){
            System.out.println("\n=========== Hotel Management System ===========\n");
            System.out.println("1. Display Room Details");
            System.out.println("2. Display Guest Information");
            System.out.println("3. Book a Room");
            System.out.println("4. Show Staff Information");
            System.out.println("5. Show Booking Schedule");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt(); // Read user input
            switch(choice){
                case 1:
                    System.out.println("======================================");
                    System.out.println("      ROOM DETAILS");
                    System.out.println("======================================");
                    hotel.displayAllRooms();
                    break;
            
                case 2:
                    System.out.println("======================================");
                    System.out.println("      GUEST INFORMATION");
                    System.out.println("======================================");
                    System.out.println(guest1.toString());
                    System.out.println(guest2.toString());
                    break;
                case 3:
                    System.out.println("======================================");
                    System.out.println("      BOOKING ROOM");
                    System.out.println("======================================");
                    scanner.nextLine(); // clear buffer
                    System.out.print("Enter room type to search: ");
                    String type = scanner.nextLine();
                    hotel.findRoomsByType(hotel.rooms, hotel.countRooms, type);
                    break; 
                case 4:
                    System.out.println("======================================");
                    System.out.println("      STAFF INFORMATION");
                    System.out.println("======================================");
                    System.out.println(staff1.toString());
                    System.out.println(staff2.toString());
                    break;
                case 5:
                    System.out.println("======================================");
                    System.out.println("      ROOM BOOKING SCHEDULE");
                    System.out.println("======================================");
                    BookingSchedule schedule = new BookingSchedule();
                    schedule.showBookingSchedule();
                    break;
                case 6:
                    exit = true; 
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                }
        }
    }
    
}
