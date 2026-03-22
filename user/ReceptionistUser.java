package user;

import controller.Hotel;

public class ReceptionistUser extends Staff {
    private double salary;
    private String workHours;
    public ReceptionistUser(String staffId, String name, char gender, String phoneNumber, String password, double salary, String workHours) {
        super(staffId, name, gender, phoneNumber, password);
        this.setSalary(salary);
        this.setWorkHours(workHours);
    }

    // public ReceptionistUser(String staffId, String name, String password) {
    //     super(staffId, name, '?', password);
    // }
    @Override
    public boolean can(String action) {
        return action.equals(Hotel.CREATE_BOOKING)||
             action.equals(Hotel.VIEW_GUESTS)||
             action.equals(Hotel.VIEW_ROOMS)||
             action.equals(Hotel.VIEW_BOOKING_SCHEDULE)||
             action.equals(Hotel.UPDATE_ROOM_STATUS);
    }

    @Override
    public String getSignature() {
        return "Receptionist: " + getUsername();
    }
    public double getSalary() {
        return salary;
    }
    public String getWorkHours() {
        return workHours;
    }
    public void setWorkHours(String workHours) {
        this.workHours = WorkHours(workHours);
    }

    public void setSalary(double salary) {
        this.salary = salary;
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
        return this.salary == other.salary && super.equals(other);
    }

    private String WorkHours(String workHours) {
        if (workHours == null || workHours.trim().isEmpty()) {
            return "N/A";
        }
        return workHours.trim();
    }
}
