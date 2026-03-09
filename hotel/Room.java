package hotel;

import java.util.Objects;

public class Room implements IRoom {
    private String roomNumber;
    private String roomType;
    private double pricePerNight;
    private int roomID;
    private static int roomCounter = 0;

    public Room(String roomNumber, String roomType, double pricePerNight) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be empty");
        }
        if (roomType == null || roomType.trim().isEmpty()) {
            throw new IllegalArgumentException("Room type cannot be empty");
        }

        this.roomNumber = roomNumber.trim();
        this.roomType = roomType.trim();
        this.pricePerNight = Math.max(0, pricePerNight);
        this.roomID = ++roomCounter;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
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
    @Override
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
    @Override
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
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Room)) {
            return false;
        }
        Room other = (Room) obj;
        return roomID == other.roomID
            && Objects.equals(roomNumber, other.roomNumber)
            && Objects.equals(roomType, other.roomType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, roomType, roomID);
    }
}
