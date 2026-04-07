package room;


public class NormalRoom extends Room {
    public NormalRoom(String roomNumber, double basePricePerNight) {
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
        return super.equals(obj);
    }

}
