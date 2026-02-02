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
    
    @Override
    public String toString() {
        return "Room Number: " + roomNumber + "\nRoom Type: " + roomType
        + "\nPrice Per Night: $" + pricePerNight + "\nStatus: " + status + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        Room other = (Room) obj;
        if (!roomType.equals(other.roomType))
            return false;
        return true;
    }

}
