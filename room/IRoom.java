package room;

import java.math.BigDecimal;

public interface IRoom {
    String getRoomNumber();
    String getRoomType();
    BigDecimal getPricePerNight();
    int getRoomId();
    RoomStatus getStatus();
    void setStatus(RoomStatus status);
    void book();
    void release();

    default boolean matchesType(String requestedType) {
        if (requestedType == null || requestedType.trim().isEmpty()) {
            return false;
        }
        return getRoomType().equalsIgnoreCase(requestedType.trim());
    }
}
