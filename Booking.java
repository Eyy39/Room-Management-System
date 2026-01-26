public class Booking {
    int BookingID;
    String customerName;
    String roomType;
    String CheckIn;
    int days;
    Double price;

    Booking(int BookingID, String customerName, String roomType, String CheckIn, int days, double price ){
        this.BookingID=BookingID;
        this.customerName =customerName;
        this.roomType= roomType;
        this.CheckIn = CheckIn;
        this.days= days;
        this.price = price;
    }
    public void showBooking(){
        System.out.println("Booking ID: " + BookingID);
        System.out.println("Room Type: " + roomType);
        System.out.println("CheckIn Date: " + CheckIn);
        System.out.println("Duration: " + days);
        System.out.println("Total: " + Total());
    }
    public double Total () {
        return days*price;

    }
}
