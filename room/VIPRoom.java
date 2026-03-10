package room;

public class VIPRoom extends Room {
    private static final double SERVICE_FEE = 50.0;
    private double basePricePerNight; // Base price without service fee
    public VIPRoom(Room room, double basePricePerNight) {
        super(room.getRoomId(), room.getRoomNumber());
        this.setBasePricePerNight(basePricePerNight);
    }

    @Override
    public String getRoomType() {
        return "VIP";
    }

    double getServiceFee() {
        return SERVICE_FEE;
    }
    @Override
    public double getPricePerNight() {
        return basePricePerNight + getServiceFee();
    }

    void setBasePricePerNight(double basePricePerNight) {
        if (basePricePerNight < 0) {
            this.basePricePerNight = 0.0;
        } else {
            this.basePricePerNight = basePricePerNight;
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof VIPRoom)) return false;

        VIPRoom other = (VIPRoom) obj;
        return Double.compare(this.basePricePerNight, other.basePricePerNight) == 0;
    }

    @Override
    public String toString() {
        return super.toString() + "Room Type: VIP \nPricePerNight: $" + getPricePerNight() + "\n";
    }
    
}
