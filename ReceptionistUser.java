public class ReceptionistUser implements IStaff {
    private String id;
    private String username;
    private String password;

    public ReceptionistUser(String id, String username, String password) {
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
        return "Receptionist";
    }

    @Override
    public boolean can(String action) {
        if (action.equals("VIEW_ROOMS") ||
            action.equals("VIEW_GUESTS") ||
            action.equals("CREATE_BOOKING") ||
            action.equals("VIEW_BOOKING_SCHEDULE") ||
            action.equals("UPDATE_ROOMS_STATUS")) {
            return true;
        } else {
            return false;
        }
    }
}
