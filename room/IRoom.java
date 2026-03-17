package room;

public interface IRoom {
    String getRoomNumber();
    String getRoomType();
    double getPricePerNight();
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
