public class Booking {
    int BookingID;
    String CheckIn;
    int night;
    double price;
    Guest guest;
    Room  room;
    Staff staff;

    Booking(int BookingID, String CheckIn, int night, Guest guest, Room room, Staff staff){
         this.BookingID=BookingID;
        this.BookingID=BookingID;
        this.CheckIn = CheckIn;
        this.night= night;
        this.price =room.pricePerNight;
        this.guest = guest;
        this.room = room;
        this.staff = staff;
    }
    public void showBooking(){
        System.out.println("Customer Name: " + guest.guestName);
        System.out.println("Booking ID: " + BookingID);
        System.out.println("Room Type: " + room.roomType);
        System.out.println("CheckIn Date: " + CheckIn);
        if(night == 1){
            System.out.println("Duration: " + night + " night");
        }else{
            System.out.println("Duration: " + night + " nights");
        }
        System.out.println("Price per Night: $" + price);
        System.out.println("Total: " + Total());
        System.out.println( "======================================" );
        System.out.println("Staff Assigned: " + staff.name);
    }
    public double Total () {
        return night* price;
    }
}
