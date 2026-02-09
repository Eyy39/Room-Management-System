import java.util.Objects;

public class Room {
    private String roomNumber;
    private String roomType;
    private double pricePerNight;
    private int roomID;
    private static int roomCounter = 0;

    public Room(String roomNumber, String roomType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.roomID = ++roomCounter;
    }
    public String getRoomNumber() {
        return roomNumber;
    }
    public String getRoomType() {
        return roomType;
    }
    public void setRoomType(String roomType){
        if (roomType != null && !roomType.trim().isEmpty()) {
            this.roomType = roomType;
        }
    }
    public double getPricePerNight(){
        return pricePerNight;
    }
    public void setPricePerNight(double pricePerNight) {
        if(pricePerNight < 0){
            this.pricePerNight = 0.0;
        }else{
            this.pricePerNight = pricePerNight;
        }
    }
    public int getRoomId() {
        return roomID;
    }
    public static int getRoomCounter() {
        return roomCounter;
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
