package hotel;

public class NormalRoom implements IRoom {
    private String roomNumber;
    private double pricePerNight;
    private final int roomId;
    private static int roomCounter = 0;

    public NormalRoom(String roomNumber, double pricePerNight) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be empty");
        }
        this.roomNumber = roomNumber.trim();
        this.pricePerNight = Math.max(0, pricePerNight);
        this.roomId = ++roomCounter;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public String getRoomType() {
        return "Normal";
    }

    @Override
    public double getPricePerNight() {
        return pricePerNight;
    }

    @Override
    public int getRoomId() {
        return roomId;
    }

    public void setRoomNumber(String roomNumber) {
        if (roomNumber != null && !roomNumber.trim().isEmpty()) {
            this.roomNumber = roomNumber.trim();
        }
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = Math.max(0, pricePerNight);
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber
            + "\nRoom Type: " + getRoomType()
            + "\nRoom ID: " + roomId
            + "\nPrice Per Night: $" + getPricePerNight() + "\n";
    }
}
