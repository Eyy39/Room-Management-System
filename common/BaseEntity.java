package common;

public abstract class BaseEntity {
    private static int guestCounter = 0;
    private static int roomCounter = 0;
    private static int staffCounter = 0;
    private static int bookingCounter = 0;

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
    }

    public String getId() {
        return id;
    }

    public int getNumericId() {
        String digits = id.replaceAll("\\D", "");
        if (digits.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(digits);
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
        } else {
            next = 0;
        }
        return prefix + String.format("%03d", next);
    }
}
