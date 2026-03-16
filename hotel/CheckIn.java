package hotel;

import common.BaseEntity;
import pricing.DiscountStrategy;
import pricing.PercentageDiscountStrategy;
import room.IRoom;
import user.IStaff;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckIn extends BaseEntity {
    private static final int LIMIT_DAYS = 10;

    private final IRoom room;
    private final String checkInDate;
    private final int night;
    private final Guest guest;
    private final IStaff staff;
    private final DiscountStrategy discountStrategy;

    private BookingStatus status;
    private BigDecimal originalPrice;
    private BigDecimal discountPrice;

    public CheckIn(Guest guest, IRoom room, String checkInDate, int night, IStaff staff, BigDecimal discountPercent) {
        this(guest, room, checkInDate, night, staff, discountPercent, new PercentageDiscountStrategy());
    }

    public CheckIn(Guest guest, IRoom room, String checkInDate, int night, IStaff staff, BigDecimal discountPercent,
                   DiscountStrategy discountStrategy) {
        super("B");
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.night = night;
        this.staff = staff;
        this.discountStrategy = discountStrategy;
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

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
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

    public BigDecimal getTotal() {
        BigDecimal subtotal = originalPrice.multiply(BigDecimal.valueOf(night));
        BigDecimal totalDiscount = discountPrice.multiply(BigDecimal.valueOf(night));
        return subtotal.subtract(totalDiscount);
    }

    public boolean isBookingDateValid(LocalDate bookingDate) {
        LocalDate today = LocalDate.now();
        LocalDate maxBookingDate = today.plusDays(LIMIT_DAYS);
        return !bookingDate.isAfter(maxBookingDate);
    }

    public List<String> bookingSchedule() {
        List<String> schedule = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

        for (int i = 0; i < LIMIT_DAYS; i++) {
            LocalDate date = today.plusDays(i);
            schedule.add(String.format("%-3d | %s | Available", (i + 1), date.format(formatter)));
        }
        return schedule;
    }

    private void recalculatePrice(BigDecimal discountPercent) {
        this.originalPrice = room.getPricePerNight();
        this.discountPrice = discountStrategy.calculateDiscount(originalPrice, discountPercent);
    }

    @Override
    public String toString() {
        String duration = night == 1 ? night + " night" : night + " nights";
        return "Customer Name: " + guest.getGuestName()
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
