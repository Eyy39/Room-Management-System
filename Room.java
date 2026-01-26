public class Room {
    int roomNumber;
    String roomType;
    double pricePerNight;
    boolean isAvailable;

    Room(int roomNumber, String roomType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        if(pricePerNight < 0){
            this.pricePerNight = 0.0;
        }else{
            this.pricePerNight = pricePerNight;
        }
        this.isAvailable = true;
    }
    void displayRoom(){
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: $" + pricePerNight);
        System.out.println("Available: " + isAvailable + "\n");
    }
}
