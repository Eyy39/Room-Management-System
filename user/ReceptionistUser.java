package user;

import java.math.BigDecimal;
import java.util.Objects;

public class ReceptionistUser extends Staff {
    private BigDecimal salary;
    private String workHours;
    public ReceptionistUser(String staffId, String name, char gender, String phoneNumber, String password, BigDecimal salary, String workHours) {
        super(staffId, name, gender, phoneNumber, password);
        this.salary = sanitizeSalary(salary);
        this.workHours = sanitizeWorkHours(workHours);
    }

    // public ReceptionistUser(String staffId, String name, String password) {
    //     super(staffId, name, '?', password);
    // }
    @Override
    public boolean can(Permission permission) {
        return permission == Permission.CREATE_BOOKING
            || permission == Permission.VIEW_GUESTS
            || permission == Permission.VIEW_ROOMS
            || permission == Permission.VIEW_BOOKING_SCHEDULE
            || permission == Permission.UPDATE_ROOM_STATUS;
    }

    @Override
    public String getSignature() {
        return "Receptionist: " + getUsername();
    }

    public BigDecimal getSalary() {
        return salary;
    }
    public String getWorkHours() {
        return workHours;
    }
    public void setWorkHours(String workHours) {
        this.workHours = sanitizeWorkHours(workHours);
    }

    public void setSalary(BigDecimal salary) {
        this.salary = sanitizeSalary(salary);
    }

    @Override
    public String toString() {
        return super.toString() + "Position: Receptionist\nSalary: $" + getSalary() + "\nworkHours: " + getWorkHours() + "\n";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ReceptionistUser)) {
            return false;
        }
        ReceptionistUser other = (ReceptionistUser) obj;
        return this.salary.compareTo(other.salary) == 0 && super.equals(other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), salary, workHours);
    }

    private BigDecimal sanitizeSalary(BigDecimal salary) {
        if (salary == null || salary.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return salary;
    }

    private String sanitizeWorkHours(String workHours) {
        if (workHours == null || workHours.trim().isEmpty()) {
            return "N/A";
        }
        return workHours.trim();
    }
}
