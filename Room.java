public class Room {
    int roomNumber;
    String roomType;
    double price;
    boolean isAvailable;

    Room(int roomNumber, String roomType, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = true;
    }
    void displayRoom(){
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: $" + price);
        System.out.println("Available: " + isAvailable + "\n");
    }
}
