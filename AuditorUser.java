public class AuditorUser implements IStaff {
    private String id;
    private String username;
    private String password;

    public AuditorUser(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getRole() {
        return "Auditor";
    }

    @Override
    public boolean can(String action) {
        if (action.equals("VIEW_ROOMS") ||
            action.equals("VIEW_GUESTS") ||
            action.equals("VIEW_STAFF") ||
            action.equals("VIEW_BOOKING_SCHEDULE")) {
            return true;
        } else {
            return false;
        }
    }
}
