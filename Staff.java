
public class Staff {
    private String staffId;
    private String name;
    private String position;
    private char gender;
    private static int staffCounter = 0;

    public Staff(String name, String position, char gender) {
        this.staffId = generateStaffId(); // Generate a unique staff ID
        this.name = name;
        this.position = position;
        this.gender = gender;
    }
    // login constructor
    public Staff(String staffId, String name){
        this.staffId = staffId;
        this.name = name;
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
    @Override
    public String toString() {
        return "Staff ID: " + staffId + "\nName: " + name + "\nPosition: " + position + 
        "\nGender: " + gender + "\n";
    }

}
