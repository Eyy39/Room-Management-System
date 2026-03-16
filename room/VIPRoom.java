package room;

import java.math.BigDecimal;
import java.util.Objects;
import pricing.VipServiceFeePricingStrategy;

public class VIPRoom extends Room {
    private static final BigDecimal DEFAULT_SERVICE_FEE = new BigDecimal("50.00");
    private boolean freeBreakfast;

    public VIPRoom(String roomNumber, BigDecimal basePricePerNight) {
        this(roomNumber, basePricePerNight, DEFAULT_SERVICE_FEE);
    }

    public VIPRoom(String roomNumber, BigDecimal basePricePerNight, BigDecimal serviceFee) {
        super(roomNumber, basePricePerNight);
        this.freeBreakfast = true;
        setPricingStrategy(new VipServiceFeePricingStrategy(serviceFee));
    }

    @Override
    public String getRoomType() {
        return "VIP";
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
