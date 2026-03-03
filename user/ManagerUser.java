package user;

public class ManagerUser extends Staff {
    public ManagerUser(Staff s, float salary) {
        super(s, "Manager", salary);
    }

    // login constructor used in Main
    public ManagerUser(String staffId, String name, String password) {
        super(staffId, name, "Manager", '?', password);
    }

    @Override
    public boolean can(String action) {
        return true; // Manager has all permissions
    }
}
