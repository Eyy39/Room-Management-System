package user;
import controller.Hotel;


public class ReceptionistUser extends Staff {
    private float salary;
    public ReceptionistUser(Staff s, float salary) {
        super(s.getStaffId(), s.getName(), s.getGender(), s.getPassword());
        this.setSalary(salary);
    }

    // public ReceptionistUser(String staffId, String name, String password) {
    //     super(staffId, name, '?', password);
    // }
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
        return super.toString() + "Position: Receptionist\nSalary: " + salary + "\n";
    }
    
}
