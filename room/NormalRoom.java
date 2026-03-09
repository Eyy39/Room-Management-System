package room;

import user.ManagerUser;

public class NormalRoom extends Room {
    private double pricePerNight;
    public NormalRoom(Room room, double pricePerNight) {
        super(room.getRoomId(),room.getRoomNumber());
        this.setPrice(pricePerNight);
    }
    void setPrice(double pricePerNight) {
        if(pricePerNight < 0){
            this.pricePerNight = 0.0;
        }else{
            this.pricePerNight = pricePerNight;
        }
    }
    double getPrice() {
        return pricePerNight;
    }

    @Override
    public String toString() {
        return super.toString() + "Room Type: Normal \nPricePerNight: $" + pricePerNight + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof ManagerUser)) return false;

        NormalRoom other = (NormalRoom) obj;
        return Double.compare(this.pricePerNight, other.pricePerNight) == 0;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    
}
