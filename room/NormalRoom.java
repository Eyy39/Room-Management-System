package room;

import java.math.BigDecimal;
import java.util.Objects;

public class NormalRoom extends Room {
    public NormalRoom(String roomNumber, BigDecimal basePricePerNight) {
        super(roomNumber, basePricePerNight);
    }

    @Override
    public String getRoomType() {
        return "Normal";
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof NormalRoom)) return false;

        NormalRoom other = (NormalRoom) obj;
        return getPricePerNight().compareTo(other.getPricePerNight()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomId(), getPricePerNight());
    }
}
