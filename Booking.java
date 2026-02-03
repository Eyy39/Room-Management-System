public class Booking {
    Room room;
    static int bookingCount = 0;
    int BookingID;
    String CheckIn;
    int night;
    double originalPrice;
    double discountPrice;
    Guest guest;
    Staff staff;

    Booking(Guest guest, Room room, String CheckIn, int night, Staff staff, double discountPercent){
        this.BookingID= ++bookingCount; // Increment booking count for unique ID
        this.CheckIn = CheckIn;
        this.night= night;
        this.originalPrice =room.pricePerNight;
        this.discountPrice = originalPrice * (discountPercent / 100);
        this.guest = guest;
        this.room = room;
        this.staff = staff;
    }
    public double Total () {
        return night* (originalPrice - discountPrice);
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
           "\nPrice per Night: $" + originalPrice +
           "\nDiscount: $" + discountPrice +
           "\nTotal: $" + Total() +
           "\n======================================" +
           "\nStaff Assigned: " + staff.name + "\n";
    }
    
}
