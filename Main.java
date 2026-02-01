import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Room room1 = new Room("A101", "Single", 100.0);
        Room room2 = new Room("A102", "Double", 150.0);
        Guest guest1 = new Guest("Nou Sokleap", 1, "086-456-7890");
        Staff staff1 = new Staff("Chan Sokha", "Manager", 'M');
        Staff staff2 = new Staff("Sok Aliza", "Receptionist", 'F');
        Booking booking1 = new Booking(1, "2024-10-01", 3, guest1, room1, staff2);

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
                    room1.displayRoom();
                    room2.displayRoom();
                    break;
            
                case 2:
                    System.out.println("======================================");
                    System.out.println("      GUEST INFORMATION");
                    System.out.println("======================================");
                    booking1.showBooking();
                    break;
                case 3:
                    System.out.println("======================================");
                    System.out.println("      BOOKING ROOM");
                    System.out.println("======================================");
                    booking1.showBooking();;
                    
                    break; 
                case 4:
                    System.out.println("======================================");
                    System.out.println("      STAFF INFORMATION");
                    System.out.println("======================================");
                    staff1.displayStaff();
                    staff2.displayStaff();
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
        scanner.close(); // Close the scanner
    }
    
}
