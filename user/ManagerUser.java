package user;

public class ManagerUser extends Staff {
    private float salary;

    public ManagerUser(String staffId, String name, char gender, String phoneNumber, String password, float salary) {
        super(staffId, name, gender, phoneNumber, password);
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

    @Override
    public String getSignature() { //
        return "Manager: " + getUsername();
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        if(salary < 0) {
            System.out.println("Invalid salary. Salary not updated.");
        } else {
            this.salary = salary;
        }
    }
    @Override
    public String toString() {
        return super.toString() + "Position: Manager\n" +
               "Salary: $" + getSalary() + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ManagerUser)) {
            return false;
        }
        ManagerUser other = (ManagerUser) obj;
        return Float.compare(this.salary, other.salary) == 0
            && super.equals(other);
    }
    
}
