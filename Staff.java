public class Staff implements IStaff {
    private String staffId;
    private String name;
    private String position;
    private char gender;
    private String password;
    private static int staffCounter = 0;

    public Staff(String name, String position, char gender) {
        this.staffId = generateStaffId(); // Generate a unique staff ID
        this.setName(name);
        this.setPosition(position);
        this.setGender(gender);
    }
    // login constructor
    public Staff(String staffId, String name, String password){
        this.staffId = staffId;
        this.name = name;
        this.password = password;
    }
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

    public String getPosition(){
        return position;
    }
    public void setPosition(String position) {
        if (position == null || position.trim().isEmpty()) {
            System.out.println("Invalid position. Position not updated.");
            return;
        }
        this.position = position.trim();
    }
    public char getGender() {
        return gender;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }

    private String generateStaffId() {
        return "ST" + (++staffCounter);
    }
    public static int getStaffCounter() {
        return staffCounter;
    }
    public void setPassword(String password) {
        if (password == null) {
            System.out.println("Invalid password.");
            return;
        }
        this.password = password;
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
        return position != null ? position : "";
    }

    @Override
    public boolean can(String action) {
        return false; // base staff has no special permissions
    }

    @Override
    public String toString() {
        return "Staff ID: " + staffId + "\nName: " + name + "\nPosition: " + position + 
        "\nGender: " + gender + "\n";
    }

    @Override
    public boolean equals(Object obj) {
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
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        return true;
    }

    
}
