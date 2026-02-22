public class ManagerUser implements IStaff {
    private String id;
    private String username;
    private String password;

    public ManagerUser(String id, String username, String password) {
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
        return "Manager";
    }

    @Override
    public boolean can(String action) {
        return true;
    }
}
