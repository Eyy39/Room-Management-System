import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingSchedule {
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
