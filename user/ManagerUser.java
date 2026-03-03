package user;

public class ManagerUser extends Staff {
    private float salary;
    public ManagerUser(Staff s, float salary) {
        super(s.getName(), s.getStaffId(), s.getPassword());
        this.setSalary(salary);
    }

    // login constructor used in Main
    public ManagerUser(String staffId, String name, String password) {
        super(staffId, name, password);
    }
    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        if (salary < 0) {
            System.out.println("Invalid salary. Salary not updated.");
            return;
        }
        this.salary = salary;
    }

    @Override
    public boolean can(String action) {
        return true; // Manager has all permissions
    }

    @Override
    public String toString() {
        return super.toString() + "ManagerUser [\"Position: Manager Salary=" + salary + "]";
    }
    @Override
    public boolean equals(Object obj) {
        ManagerUser other = (ManagerUser) obj;
        if (!super.equals(other)){
            return false;
        }else{
            if (Float.floatToIntBits(salary) != Float.floatToIntBits(other.salary))
            return false;
        }
                
        return true;
    }
    

    
}
