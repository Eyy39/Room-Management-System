package room;

import user.ManagerUser;

public class VIPRoom extends Room {
    private static final double SERVICE_FEE = 100.0;
    double basePricePerNight;
    public VIPRoom(Room room, double basePricePerNight) {
        super(room.getRoomId(), room.getRoomNumber());
        this.basePricePerNight = basePricePerNight;
    }

    @Override
    public String getRoomType() {
        return "VIP";
    }

    @Override
    public double getPricePerNight() {
        return super.getPricePerNight() + SERVICE_FEE;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof ManagerUser)) return false;

        VIPRoom other = (VIPRoom) obj;
        return Double.compare(this.basePricePerNight, other.basePricePerNight) == 0;
    }

    @Override
    public String toString() {
        return super.toString() + "Room Type: VIP \nPricePerNight: $" + basePricePerNight + "\n";
    }
    
}
