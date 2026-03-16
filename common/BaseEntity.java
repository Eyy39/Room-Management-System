package common;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseEntity {
    private static final Map<String, Integer> COUNTERS = new HashMap<>();
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
        syncCounter(prefix, this.id);
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
        int next = COUNTERS.getOrDefault(prefix, 0) + 1;
        COUNTERS.put(prefix, next);
        return prefix + String.format("%03d", next);
    }

    private static synchronized void syncCounter(String prefix, String id) {
        String digits = id.replaceAll("\\D", "");
        if (digits.isEmpty()) {
            return;
        }

        int value = Integer.parseInt(digits);
        int current = COUNTERS.getOrDefault(prefix, 0);
        if (value > current) {
            COUNTERS.put(prefix, value);
        }
    }
}
