package user;

import java.math.BigDecimal;
import java.util.Objects;

public class ManagerUser extends Staff {
    private BigDecimal salary;

    public ManagerUser(String staffId, String name, char gender, String phoneNumber, String password, BigDecimal salary) {
        super(staffId, name, gender, phoneNumber, password);
        this.salary = sanitizeSalary(salary);
    }

    // login constructor used in Main
    // public ManagerUser(String staffId, String name, String password) {
    //     super(staffId, name, "Manager", '?', password);
    // }
    
    @Override
    public boolean can(Permission permission) {
        return true; // Manager has all permissions
    }

    @Override
    public String getSignature() { //
        return "Manager: " + getUsername();
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = sanitizeSalary(salary);
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
        return this.salary.compareTo(other.salary) == 0
            && super.equals(other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), salary);
    }

    private BigDecimal sanitizeSalary(BigDecimal salary) {
        if (salary == null || salary.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return salary;
    }
    
}
