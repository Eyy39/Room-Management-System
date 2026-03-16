package room;

import common.BaseEntity;
import exception.RoomNotAvailableException;
import java.math.BigDecimal;
import java.util.Objects;
import pricing.PricingStrategy;
import pricing.StandardPricingStrategy;

public abstract class Room extends BaseEntity implements IRoom {
    private String roomNumber;
    private BigDecimal basePricePerNight;
    private RoomStatus status;
    private PricingStrategy pricingStrategy;

    protected Room(String roomNumber, BigDecimal basePricePerNight) {
        super("R");
        this.roomNumber = sanitizeRoomNumber(roomNumber);
        this.basePricePerNight = sanitizePrice(basePricePerNight);
        this.status = RoomStatus.AVAILABLE;
        this.pricingStrategy = new StandardPricingStrategy();
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = sanitizeRoomNumber(roomNumber);
    }

    @Override
    public int getRoomId() {
        return getNumericId();
    }

    protected BigDecimal getBasePricePerNight() {
        return basePricePerNight;
    }

    protected void setBasePricePerNight(BigDecimal basePricePerNight) {
        this.basePricePerNight = sanitizePrice(basePricePerNight);
    }

    protected void setPricingStrategy(PricingStrategy pricingStrategy) {
        if (pricingStrategy != null) {
            this.pricingStrategy = pricingStrategy;
        }
    }

    @Override
    public BigDecimal getPricePerNight() {
        return pricingStrategy.apply(basePricePerNight);
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
            throw new RoomNotAvailableException("Room is under maintenance.");
        }
        if (status == RoomStatus.OCCUPIED) {
            throw new RoomNotAvailableException("Room is already occupied.");
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

    private String sanitizeRoomNumber(String roomNumber) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be empty");
        }
        return roomNumber.trim();
    }

    private BigDecimal sanitizePrice(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return value;
    }
}
