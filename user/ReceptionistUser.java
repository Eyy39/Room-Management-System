package user;
import controller.Hotel;


public class ReceptionistUser extends Staff {
    private float salary;

    public ReceptionistUser(Staff s, float salary) {
        super(s.getName(), s.getStaffId(), s.getPassword());
        this.setSalary(salary);
    }

    public void setSalary(float salary) {
        if (salary < 0) {
            System.out.println("Invalid salary. Salary not updated.");
            return;
        }
        this.salary = salary;
    }
    public float getSalary() {
        return salary;
    }
    @Override
    public boolean can(String action) {
        if (action.equals(Hotel.CREATE_BOOKING) ||
            action.equals(Hotel.VIEW_GUESTS) ||
            action.equals(Hotel.CREATE_BOOKING) ||
            action.equals(Hotel.VIEW_ROOMS) ||
            action.equals(Hotel.VIEW_BOOKING_SCHEDULE) ||
            action.equals(Hotel.UPDATE_ROOM_STATUS)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return super.toString() + "ReceptionistUser [salary=" + salary + "]";
    }
    
}
