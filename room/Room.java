package room;

import java.util.Objects;

public class Room implements IRoom {
    private String roomNumber;
    // private String roomType;
    // private double pricePerNight;
    private int roomID;
    private static int roomCounter = 0;

    public Room(String roomNumber) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be empty");
        }
        // if (roomType == null || roomType.trim().isEmpty()) {
        //     throw new IllegalArgumentException("Room type cannot be empty");
        // }

        this.setRoomNumber(roomNumber);
        // this.setRoomType(roomType);
        // this.setPricePerNight(pricePerNight);
        this.roomID = ++roomCounter;
    }
    public Room(int roomID, String roomNumber) {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public String getRoomType() {
        return "Room";
    }
    // Default price (can be overridden)
    @Override
    public double getPricePerNight() {
        return 0;
    }
    public void setRoomNumber(String roomNumber) {
        if (roomNumber != null && !roomNumber.trim().isEmpty()) {
            this.roomNumber = roomNumber;
        }
    }
    // public void setRoomType(String roomType){
    //     if (roomType != null && !roomType.trim().isEmpty()) {
    //         this.roomType = roomType;
    //     }
    // }
    // @Override
    // public double getPricePerNight(){
    //     return pricePerNight;
    // }
    // public void setPricePerNight(double pricePerNight) {
    //     if(pricePerNight < 0){
    //         this.pricePerNight = 0.0;
    //     }else{
    //         this.pricePerNight = pricePerNight;
    //     }
    // }
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
        return "Room Number: " + roomNumber + "\nRoom ID: " + roomID + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Room)) return false;

        Room other = (Room) obj;
        return roomID == other.roomID &&
               Objects.equals(roomNumber, other.roomNumber);
    }
}
