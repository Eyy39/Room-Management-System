public class Booking {
    int bookingCount = 1;
    int BookingID = ++bookingCount;
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
    public double Total () {
        return night* price;
    }
    @Override
    public String toString(){
        String duration;
        if(night == 1){
            duration = night + " night";
        }else{
            duration = night + " nights";
        }
         return "Customer Name: " + guest.guestName +
           "\nBooking ID: " + BookingID +
           "\nRoom Type: " + room.roomType +
           "\nCheckIn Date: " + CheckIn +
           "\nDuration: " + duration +
           "\nPrice per Night: $" + price +
           "\nTotal: $" + Total() +
           "\n======================================" +
           "\nStaff Assigned: " + staff.name;
    }
    
}
