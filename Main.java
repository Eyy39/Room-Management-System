public class Main {
    public static void main(String[] args) {

        Room room1 = new Room(101, "Single", 100.0);
        Room room2 = new Room(102, "Double", 150.0);
        Guest guest1 = new Guest("John Doe", 1, "086-456-7890");
        System.out.println("Displaying room details:");
        room1.displayRoom();
        room2.displayRoom();
        System.out.println("Displaying guest details:");
        guest1.displayGuest();
    }
    
}
