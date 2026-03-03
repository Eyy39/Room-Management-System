package user;
import controller.Hotel;


public class ReceptionistUser extends Staff {
    public ReceptionistUser(Staff s, float salary) {
        super(s, "Receptionist", salary);
    }

    public ReceptionistUser(String staffId, String name, String password) {
        super(staffId, name, "Receptionist", '?', password);
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
