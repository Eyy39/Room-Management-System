package hotel;

import common.BaseEntity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import room.IRoom;
import user.IStaff;

public class CheckIn extends BaseEntity {
    private static final int LIMIT_DAYS = 10;

    private IRoom room;
    private String checkInDate;
    private int night;
    private Guest guest;
    private IStaff staff;

    private BookingStatus status;
    private double originalPrice;
    private double discountPrice;

    public CheckIn(Guest guest, IRoom room, String checkInDate, int night, IStaff staff, double discountPercent) {
        super("B");
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.night = night;
        this.staff = staff;
        this.status = BookingStatus.RESERVED;
        recalculatePrice(discountPercent);
    }

    public int getBookingID() {
        return getNumericId();
    }

    public String getBookingCode() {
        return getId();
    }

    public String getCheckIn() {
        return checkInDate;
    }

    public int getNight() {
        return night;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public double getDiscountPrice() {
        return discountPrice * getNight();
    }

    public Guest getGuest() {
        return guest;
    }

    public IRoom getRoom() {
        return room;
    }

    public IStaff getStaff() {
        return staff;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void checkIn() {
        this.status = BookingStatus.CHECKED_IN;
    }

    public void checkOut() {
        this.status = BookingStatus.CHECKED_OUT;
        room.release();
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
        room.release();
    }

    public double getTotal() {
        double subtotal = originalPrice * getNight();
        double totalDiscount = discountPrice * getNight();
        return subtotal - totalDiscount;
    }

    public boolean isBookingDateValid(LocalDate bookingDate) {
        LocalDate today = LocalDate.now();
        LocalDate maxBookingDate = today.plusDays(LIMIT_DAYS);
        return !bookingDate.isAfter(maxBookingDate);
    }

    public ArrayList<String> bookingSchedule() {
        ArrayList<String> schedule = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

        for (int i = 0; i < LIMIT_DAYS; i++) {
            LocalDate date = today.plusDays(i);
            schedule.add(String.format("%-3d | %s | Available", (i + 1), date.format(formatter)));
        }
        return schedule;
    }

    private void recalculatePrice(double discountPercent) {
        this.originalPrice = room.getPricePerNight();
        double safePercent = 0.0;
        if (discountPercent > 0) {
            safePercent = Math.min(discountPercent, 100.0);
        }
        this.discountPrice = originalPrice * safePercent / 100.0;
    }

    @Override
    public String toString() {
        String duration = night == 1 ? night + " night" : night + " nights";
        return "Customer Name: " + guest.getGuestName()
            + "\nPhone: " + guest.getPhoneNumber()
            + "\nEmail: " + guest.getEmail()
            + "\n-------- Room Booking Details --------"
            + "\nBooking ID: " + getBookingCode()
            + "\nRoom Type: " + room.getRoomType()
            + "\nCheckIn Date: " + checkInDate
            + "\nDuration: " + duration
            + "\nPrice per Night: $" + getOriginalPrice()
            + "\nDiscount: $" + getDiscountPrice()
            + "\nTotal: $" + getTotal()
            + "\nStatus: " + status
            + "\n======================================"
            + "\nStaff Assigned: " + staff.getSignature() + "\n";
    }
}
