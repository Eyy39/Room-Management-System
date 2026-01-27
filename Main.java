import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Room room1 = new Room(101, "Single", 100.0);
        Room room2 = new Room(102, "Double", 150.0);
        Guest guest1 = new Guest("Nou Sokleap", 1, "086-456-7890");
        Staff staff1 = new Staff(1, "Chan Sokha", "Manager");
        Staff staff2 = new Staff(2, "Sok Aliza", "Receptionist");
        Booking booking1 = new Booking(1005, "Try Dara", "Single", "2024-07-01", 3, 100.0);

        Scanner scanner = new Scanner(System.in); 
        int choice;
        boolean exit = false; 
        while(!exit){
            System.out.println("\n=========== Hotel Management System ===========\n");
            System.out.println("1. Display Room Details");
            System.out.println("2. Display Guest Information");
            System.out.println("3. Book a Room");
            System.out.println("4. Show Staff Information");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt(); // Read user input
            switch(choice){
                case 1:
                    System.out.println("\n--- Room Details ---\n");
                    room1.displayRoom();
                    room2.displayRoom();
                    break;
            
                case 2:
                    System.out.println("\n--- Guest Information ---\n");
                    guest1.displayGuest();
                    booking1.showBooking();
                    break;
                case 3:
                    System.out.println("\n--- Booking Room ---\n");
                    room1.displayRoom();
                    
                    break; 
                case 4:
                    System.out.println("\n--- Staff Information ---\n");
                    staff1.displayStaff();
                    staff2.displayStaff();
                    break;
                case 5:
                    exit = true; 
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                }
        }
        scanner.close();
    }
    
}
