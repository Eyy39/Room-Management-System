package room;

import common.BaseEntity;
import java.math.BigDecimal;
import java.util.Objects;

public abstract class Room extends BaseEntity implements IRoom {
    private String roomNumber;
    private BigDecimal basePricePerNight;
    private RoomStatus status;

    protected Room(String roomNumber, BigDecimal basePricePerNight) {
        super("R");
        this.roomNumber = RoomNumber(roomNumber);
        this.basePricePerNight = Price(basePricePerNight);
        this.status = RoomStatus.AVAILABLE;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = RoomNumber(roomNumber);
    }

    @Override
    public int getRoomId() {
        return getNumericId();
    }

    protected BigDecimal getBasePricePerNight() {
        return basePricePerNight;
    }

    protected void setBasePricePerNight(BigDecimal basePricePerNight) {
        this.basePricePerNight = Price(basePricePerNight);
    }

    @Override
    public BigDecimal getPricePerNight() {
        return basePricePerNight;
    }

    @Override
    public RoomStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(RoomStatus status) {
        if (status != null) {
            this.status = status;
        }
    }

    @Override
    public void book() {
        if (status == RoomStatus.MAINTENANCE) {
            System.out.println("Room is under maintenance.");
        }
        if (status == RoomStatus.OCCUPIED) {
            System.out.println("Room is already occupied.");
        }
        status = RoomStatus.OCCUPIED;
    }

    @Override
    public void release() {
        status = RoomStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber
                + "\nRoom ID: " + getId()
                + "\nRoom Type: " + getRoomType()
                + "\nPricePerNight: $" + getPricePerNight()
                + "\nStatus: " + status + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Room)) return false;

        Room other = (Room) obj;
        return getRoomId() == other.getRoomId() &&
               Objects.equals(roomNumber, other.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomId(), roomNumber);
    }

    private String RoomNumber(String roomNumber) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            System.out.println("Room number cannot be empty");
            return "";
        }
        return roomNumber.trim();
    }

    private BigDecimal Price(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Invalid price. Setting to $0.00.");
            return BigDecimal.ZERO;
        }
        return value;
    }
}
