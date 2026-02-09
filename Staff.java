
public class Staff {
    private String staffId;
    private String name;
    private String position;
    private char gender;
    private static int staffCounter = 0;

    Staff(String name, String position, char gender) {
        this.staffId = generateStaffId(); // Generate a unique staff ID
        this.name = name;
        this.position = position;
        this.gender = gender;
    }
    // login constructor
    Staff(String staffId, String name){
        this.staffId = staffId;
        this.name = name;
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

    String generateStaffId() {
        return "ST" + (++staffCounter);
    }
    @Override
    public String toString() {
        return "Staff ID: " + staffId + "\nName: " + name + "\nPosition: " + position + 
        "\nGender: " + gender + "\n";
    }

}
