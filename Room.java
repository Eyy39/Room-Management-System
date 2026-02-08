import java.util.Objects;

public class Room {
    String roomNumber;
    String roomType;
    double pricePerNight;
    int roomID;
    static int roomCounter = 0;


    Room(String roomNumber, String roomType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        if(pricePerNight < 0){
            this.pricePerNight = 0.0;
        }else{
            this.pricePerNight = pricePerNight;
        }
        this.roomID = ++roomCounter;
    }
    
    @Override
    public String toString() {
        return "Room Number: " + roomNumber + "\nRoom Type: " + roomType 
        + "\nRoom ID: " + roomID + "\nPrice Per Night: $" + pricePerNight + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check if the objects are the same
        if (!(obj instanceof Room)) return false; // Check if the object is an instance of Room
        Room other = (Room) obj; 
        return Objects.equals(this.roomType, other.roomType);
    }

}
