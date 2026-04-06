package common;

public abstract class BaseEntity {
    private static int guestCounter = 0;
    private static int roomCounter = 0;
    private static int staffCounter = 0;
    private static int bookingCounter = 0;
    private static int paymentCounter = 0;

    private final String id;

    protected BaseEntity(String prefix) {
        this.id = generateId(prefix);
    }

    protected BaseEntity(String prefix, String existingId) {
        if (existingId == null || existingId.trim().isEmpty()) {
            this.id = generateId(prefix);
            return;
        }

        this.id = existingId.trim();
        syncCounterWithExistingId(this.id);
    }

    public String getId() {
        return id;
    }

    public int getNumericId() {
        String digits = id.replaceAll("\\D", "");// Remove non-digit characters
        if (digits.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(digits);// Convert the remaining digits to an integer
    }

    private static synchronized String generateId(String prefix) {
        int next;
        if ("G".equals(prefix)) {
            next = ++guestCounter;
        } else if ("R".equals(prefix)) {
            next = ++roomCounter;
        } else if ("ST".equals(prefix)) {
            next = ++staffCounter;
        } else if ("B".equals(prefix)) {
            next = ++bookingCounter;
        } else if ("P".equals(prefix)) {
            next = ++paymentCounter;
        } else {
            next = 0;
        }
        return prefix + String.format("%03d", next);
    }

    private static synchronized void syncCounterWithExistingId(String existingId) {
        if (existingId == null || existingId.trim().isEmpty()) {
            return;
        }

        String normalized = existingId.trim();
        String digits = normalized.replaceAll("\\D", "");
        if (digits.isEmpty()) {
            return;
        }

        int value;
        try {
            value = Integer.parseInt(digits);
        } catch (NumberFormatException ex) {
            return;
        }

        if (normalized.startsWith("ST")) {
            staffCounter = Math.max(staffCounter, value);
        } else if (normalized.startsWith("G")) {
            guestCounter = Math.max(guestCounter, value);
        } else if (normalized.startsWith("R")) {
            roomCounter = Math.max(roomCounter, value);
        } else if (normalized.startsWith("B")) {
            bookingCounter = Math.max(bookingCounter, value);
        } else if (normalized.startsWith("P")) {
            paymentCounter = Math.max(paymentCounter, value);
        }
    }
}
