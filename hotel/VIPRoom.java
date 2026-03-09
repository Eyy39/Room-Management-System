package hotel;

public class VIPRoom extends NormalRoom {
    private static final double SERVICE_FEE = 100.0;

    public VIPRoom(String roomNumber, double basePricePerNight) {
        super(roomNumber, basePricePerNight);
    }

    @Override
    public String getRoomType() {
        return "VIP";
    }

    @Override
    public double getPricePerNight() {
        return super.getPricePerNight() + SERVICE_FEE;
    }
}
