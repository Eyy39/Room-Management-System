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
        Room other = (Room) obj;
        if (!roomType.equals(other.roomType))
            return false;
        return true;
    }

}
