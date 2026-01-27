public class Booking {
    int BookingID;
    String CheckIn;
    int days;
    Room price;
    Guest guest;
    Room  room;

    Booking(int BookingID, String CheckIn, int days, Room price, Guest guest, Room room){
         this.BookingID=BookingID;
        this.BookingID=BookingID;
        this.CheckIn = CheckIn;
        this.days= days;
        this.price =price;
        this.guest = guest;
        this.room = room;
    }
    public void showBooking(){
        System.out.println("Customer Name: " + guest.guestName);
        System.out.println("Booking ID: " + BookingID);
        System.out.println("Room Type: " + room.roomType);
        System.out.println("CheckIn Date: " + CheckIn);
        System.out.println("Duration: " + days);
        System.out.println("Total: " + Total());
    }
    public double Total () {
        return days* price.pricePerNight;

    }
}
