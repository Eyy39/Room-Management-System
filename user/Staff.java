package user;
public class Staff implements IStaff {
    private String staffId;
    private String name;
    private char gender;
    private String phoneNubmer;
    // private float salary;
    private String password;
    private static int staffCounter = 0;

    // public Staff(String name, char gender) {
    //     this.staffId = generateStaffId(); // Generate a unique staff ID
    //     this.setName(name);
    //     this.setGender(gender);
    // }

    public Staff(String name, char gender, String phoneNumber, String password) {
        this.staffId = generateStaffId();
        this.setName(name);
        this.setGender(gender);
        this.setPhoneNumber(phoneNumber);
        this.setPassword(password);
    }

    // public Staff(Staff staff, String position, float salary) {
    //     this(staff.getStaffId(), staff.getName(), position, staff.getGender(), staff.getPassword());
    //     this.setSalary(salary);
    // }

    // login constructor
    // public Staff(String staffId, String name, String password){
    //     this(staffId, name, "Staff", '?', password); 
    // }
    public String getStaffId() {
        return staffId;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        if(name == null || name.trim().isEmpty()){
            System.out.println("Invalid name. Name not updated.");
            return;
        }
        this.name = name.trim();
    }
    public char getGender() {
        return gender;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }

    // public float getSalary() {
    //     return salary;
    // }

    // public void setSalary(float salary) {
    //     if (salary < 0) {
    //         System.out.println("Invalid salary. Salary not updated.");
    //         return;
    //     }
    //     this.salary = salary;
    // }

    private String generateStaffId() {
        return String.format("ST%03d", (++staffCounter));
    }
    public static int getStaffCounter() {
        return staffCounter;
    }
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Invalid password.");
            return;
        }
        this.password = password;
    }
    public String getPhoneNumber() {
        return phoneNubmer;
    }
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            System.out.println("Invalid phone number. Phone number not updated.");
            return;
        }
        this.phoneNubmer = phoneNumber.trim();
    }

    @Override
    public String getId() {
        return staffId;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public String getPassword() {
        return password != null ? password : "";
    }

    @Override
    public String getRole() {
        return "Staff";
    }

    @Override
    public boolean can(String action) {
        return false;
    }

    @Override
    public String toString() {
        return "Staff ID: " + staffId + "\nName: " + name + 
        "\nGender: " + gender + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Staff other = (Staff) obj;
        if (staffId == null) {
            if (other.staffId != null)
                return false;
        } else if (!staffId.equals(other.staffId))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (gender != other.gender)
            return false;
        return true;
    }

    
}
