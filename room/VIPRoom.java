package room;

import java.math.BigDecimal;
import java.util.Objects;

public class VIPRoom extends Room {
    private static final BigDecimal DEFAULT_SERVICE_FEE = new BigDecimal("50.00");
    private BigDecimal serviceFee;
    private boolean freeBreakfast;

    public VIPRoom(String roomNumber, BigDecimal basePricePerNight) {
        this(roomNumber, basePricePerNight, DEFAULT_SERVICE_FEE);
    }

    public VIPRoom(String roomNumber, BigDecimal basePricePerNight, BigDecimal serviceFee) {
        super(roomNumber, basePricePerNight);
        this.freeBreakfast = true;
        if (serviceFee == null || serviceFee.compareTo(BigDecimal.ZERO) < 0) {
            this.serviceFee = BigDecimal.ZERO;
        } else {
            this.serviceFee = serviceFee;
        }
    }

    @Override
    public String getRoomType() {
        return "VIP";
    }

    @Override
    public BigDecimal getPricePerNight() {
        return getBasePricePerNight().add(serviceFee);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof VIPRoom)) return false;

        VIPRoom other = (VIPRoom) obj;
        return getPricePerNight().compareTo(other.getPricePerNight()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomId(), getPricePerNight());
    }

    @Override
    public String toString() {
        return super.toString() + "Free Breakfast: " + freeBreakfast + "\n";
    }
    
}
