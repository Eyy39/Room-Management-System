import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class checkIn {
    Room room;
    static int bookingCount = 0;
    int BookingID;
    String CheckIn;
    int night;
    double originalPrice;
    double discountPrice;
    Guest guest;
    Staff staff;
    String status;

    checkIn(Guest guest, Room room, String CheckIn, int night, Staff staff, double discountPercent){
        this.BookingID= ++bookingCount; // Increment booking count for unique ID
        this.CheckIn = CheckIn;
        this.night= night;
        this.originalPrice =room.pricePerNight;
        this.discountPrice = originalPrice * (discountPercent / 100);
        this.guest = guest;
        this.room = room;
        this.staff = staff;
        this.status = "Available";
    }
    public double Total () {
        return night* (originalPrice - discountPrice);
    }

    
    public BookingSchedule getBookingSchedule() {
        return new BookingSchedule();
    } 
    static class BookingSchedule {
        static final int LIMIT_DAYS = 10; // Maximum booking days allowed
        
        boolean isAvailable(LocalDate bookingDate) {
            LocalDate today = LocalDate.now(); // Current date
            LocalDate maxBookingDate = today.plusDays(LIMIT_DAYS); // Maximum booking date allowed
            return !bookingDate.isAfter(maxBookingDate); // Check if booking date is within limit
        }
        void showBookingSchedule(){
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy"); // Date format

            for (int i = 0; i < LIMIT_DAYS; i++) {
                LocalDate date = today.plusDays(i);
                System.out.printf("%-3d | %s | Available%n", (i + 1), date.format(formatter));
            }
            System.out.println("======================================");
        }


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
