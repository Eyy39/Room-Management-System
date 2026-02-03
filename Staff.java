
public class Staff {
    String staffId;
    String name;
    String position;
    char gender;
    static int staffCounter = 1000;

    Staff(String name, String position, char gender) {
        this.staffId = generateStaffId(); // Generate a unique staff ID
        this.name = name;
        this.position = position;
        this.gender = gender;
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
