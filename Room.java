public class Room {
    String roomNumber;
    String roomType;
    double pricePerNight;
    String status;

    Room(String roomNumber, String roomType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        if(pricePerNight < 0){
            this.pricePerNight = 0.0;
        }else{
            this.pricePerNight = pricePerNight;
        }
        this.status = "Available";
    }
    void displayRoom(){
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: $" + pricePerNight);
        System.out.println("Status: " + status + "\n");
    }
}
