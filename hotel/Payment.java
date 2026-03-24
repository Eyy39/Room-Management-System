package hotel;

public class Payment {
    private String paymentId;
    private String bookingId;
    private double amount;
    private String method;
    private boolean paid;

    public Payment(String paymentId, String bookingId, double amount, String method) {
        this.setPaymentId(paymentId);
        this.setBookingId(bookingId);
        this.setAmount(amount);
        this.setMethod(method);
        this.paid = false;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        if (paymentId == null || paymentId.trim().isEmpty()) {
            System.out.println("Invalid payment ID. Payment ID not updated.");
            return;
        }
        this.paymentId = paymentId.trim();
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            System.out.println("Invalid booking ID. Booking ID not updated.");
            return;
        }
        this.bookingId = bookingId.trim();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount < 0) {
            System.out.println("Invalid amount. Amount not updated.");
            return;
        }
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        if (method == null || method.trim().isEmpty()) {
            System.out.println("Invalid payment method. Method not updated.");
            return;
        }
        this.method = method.trim();
    }

    public boolean isPaid() {
        return paid;
    }

    public void markAsPaid() {
        this.paid = true;
    }

    @Override
    public String toString() {
        return "Payment ID: " + paymentId
            + "\nBooking ID: " + bookingId
            + "\nAmount: " + amount
            + "\nMethod: " + method
            + "\nStatus: " + (paid ? "Paid" : "Pending") + "\n";
    }
}
