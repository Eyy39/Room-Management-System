package user;

import controller.Hotel;
import java.util.Objects;


public class ReceptionistUser extends Staff {
    private float salary;
    public ReceptionistUser(Staff s, float salary) {
        super(s.getStaffId(),s.getName(), s.getGender(), s.getPhoneNumber(), s.getPassword());
        if (salary < 0) {
            throw new IllegalArgumentException("Invalid salary");
        }
        this.salary = salary;
    }

    // public ReceptionistUser(String staffId, String name, String password) {
    //     super(staffId, name, '?', password);
    // }
    @Override
    public boolean can(String action) {
        return action.equals(Hotel.CREATE_BOOKING)
            || action.equals(Hotel.VIEW_GUESTS)
            || action.equals(Hotel.VIEW_ROOMS)
            || action.equals(Hotel.VIEW_BOOKING_SCHEDULE)
            || action.equals(Hotel.UPDATE_ROOM_STATUS);
    }

    @Override
    public String getSignature() {
        return "[Receptionist] " + getUsername();
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
        return super.toString() + "Position: Receptionist\nSalary: " + getSalary() + "\n";
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
        return Float.compare(this.salary, other.salary) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(salary);
    }
    
}
