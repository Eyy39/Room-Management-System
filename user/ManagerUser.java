package user;

public class ManagerUser extends Staff {
    private float salary;
    public ManagerUser(Staff s, float salary) {
        super(s.getStaffId(), s.getName(), s.getGender(), s.getPassword());
        this.setSalary(salary);
    }

    // login constructor used in Main
    // public ManagerUser(String staffId, String name, String password) {
    //     super(staffId, name, "Manager", '?', password);
    // }
    

    @Override
    public boolean can(String action) {
        return true; // Manager has all permissions
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        if(salary < 0) {
            System.out.println("Invalid salary. Salary not updated.");
            return;
        }else{
            this.salary = salary;
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Position: Manager\nSalary: " + salary + "\n";
    }
    
}
