package room;

public class VIPRoom extends Room {
    // private double DEFAULT_SERVICE_FEE = 50.00;
    private double serviceFee = 50.00;
    private boolean freeBreakfast;

    public VIPRoom(String roomNumber, double basePricePerNight) {
        this(roomNumber, basePricePerNight, 50.00);
    }

    public VIPRoom(String roomNumber, double basePricePerNight, double serviceFee) {
        super(roomNumber, basePricePerNight);
        this.freeBreakfast = true;
        if (serviceFee < 0) {
            this.serviceFee = 50.00;
        } else {
            this.serviceFee = serviceFee;
        }
    }

    @Override
    public String getRoomType() {
        return "VIP";
    }

    @Override
    public double getPricePerNight() {
        return getBasePricePerNight() + serviceFee;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString() + "Free Breakfast: " + freeBreakfast + "\n";
    }
    
}
