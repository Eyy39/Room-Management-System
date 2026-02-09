import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CheckIn {
    private Room room;
    private static int bookingCount = 0;
    private int BookingID;
    private String CheckIn;
    private int night;
    private double originalPrice;
    private double discountPrice;
    private Guest guest;
    private Staff staff;
    private String status;

    public CheckIn(Guest guest, Room room, String CheckIn, int night, Staff staff, double discountPercent){
        this.BookingID= ++bookingCount; // Increment booking count for unique ID
        this.CheckIn = CheckIn;
        this.night= night;
        this.originalPrice = room.getPricePerNight();
        this.discountPrice = originalPrice * (discountPercent / 100);
        this.guest = guest;
        this.room = room;        
        this.staff = staff;
        this.status = "Available";
    }

    // Login
    public CheckIn(Guest guest, String checkInDate){
        this.guest = guest;
        this.CheckIn = checkInDate;
        this.status = "Checked In";
    }
    public int getBookingID() {
        return BookingID;
    }
    public String getCheckIn() {
        return CheckIn;
    }
    public int getNight() {
        return night;
    }
    public double getOriginalPrice() {
        return originalPrice;
    }
    public double getDiscountPrice() {
        return discountPrice;
    }
    public Guest getGuest() {
        return guest;
    }
    public Room getRoom() {
        return room;
    }
    public Staff getStaff() {
        return staff;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(Staff staff, String status){
        if(staff.getPosition().equals("Manager") || staff.getPosition().equals("Receptionist")){
             this.status = status;
        }
    }
    public double Total () {
        return night* (originalPrice - discountPrice);
    }

        static final int LIMIT_DAYS = 10; // Maximum booking days allowed
        public static int getBookingCount() {
            return bookingCount;
        }
        
         public boolean isBookingDateValid(LocalDate bookingDate) {
            LocalDate today = LocalDate.now(); // Current date
            LocalDate maxBookingDate = today.plusDays(LIMIT_DAYS); // Maximum booking date allowed
            return !bookingDate.isAfter(maxBookingDate); // Check if booking date is within limit
        }
        public void showBookingSchedule(){
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy"); // Date format

            for (int i = 0; i < LIMIT_DAYS; i++) {
                LocalDate date = today.plusDays(i);
                System.out.printf("%-3d | %s | Available%n", (i + 1), date.format(formatter));
            }
            System.out.println("======================================");
        }

    @Override
    public String toString(){
        String duration;
        if(night == 1){
            duration = night + " night";
        }else{
            duration = night + " nights";
        }
         return "Customer Name: " + guest.getGuestName() +
           "\nBooking ID: " + BookingID +
           "\nRoom Type: " + room.getRoomType() +
           "\nCheckIn Date: " + CheckIn +
           "\nDuration: " + duration +
           "\nPrice per Night: $" + originalPrice +
           "\nDiscount: $" + discountPrice +
           "\nTotal: $" + Total() +
           "\n======================================" +
           "\nStaff Assigned: " + staff.getName() + "\n";
    }
    
}
