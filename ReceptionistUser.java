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
        switch (action) {
            case "VIEW_ROOMS":
            case "VIEW_GUESTS":
            case "CREATE_BOOKING":
            case "VIEW_BOOKING_SCHEDULE":
                return true;
            default:
                return false;
        }
    }
}
