
public class Room {
    private String roomNumber;
    private String roomType;
    private double pricePerNight;
    private int roomID;
    private static int roomCounter = 0;

    public Room(String roomNumber, String roomType, double pricePerNight) {
        this.setRoomNumber(roomNumber);
        this.setRoomType(roomType);
        this.setPricePerNight(pricePerNight);
        this.setRoomId();
    }
    public String getRoomNumber() {
        return roomNumber;
    }
    public String getRoomType() {
        return roomType;
    }
    public void setRoomNumber(String roomNumber) {
        if (roomNumber != null && !roomNumber.trim().isEmpty()) {
            this.roomNumber = roomNumber;
        }
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
    public void setRoomId() {
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
        if (roomNumber == null) {
            if (other.roomNumber != null)
                return false;
        } else if (!roomNumber.equals(other.roomNumber))
            return false;
        if (roomType == null) {
            if (other.roomType != null)
                return false;
        } else if (!roomType.equals(other.roomType))
            return false;
        if (roomID != other.roomID)
            return false;
        return true;
    }
}
