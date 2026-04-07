package room;

public interface IRoom {
    String getRoomNumber();
    String getRoomType();
    double getPricePerNight();
    int getRoomId();
    RoomStatus getStatus();
    void setStatus(RoomStatus status);
    void book();
    void release(); // Method to release the room after use

    // Default method to check if the room matches a requested type
    default boolean matchesType(String requestedType) {
        if (requestedType == null || requestedType.trim().isEmpty()) {
            return false;
        }
        return getRoomType().equals(requestedType.trim());
    }
}
