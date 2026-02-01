import java.util.Random;

public class Staff {
    String staffId;
    String name;
    String position;
    char gender;

    Staff(String name, String position, char gender) {
        this.staffId = generateStaffId(); // Generate a unique staff ID
        this.name = name;
        this.position = position;
        this.gender = gender;
    }
    private String generateStaffId() {
        Random random = new Random(); // Random number generator
        int id = 1000 + random.nextInt(9000); // Generate a 4-digit ID
        return String.valueOf(id); // Convert to string and return
    }
    void displayStaff(){
        System.out.println("Staff ID: " + staffId);
        System.out.println("Name: " + name);
        System.out.println("Position: " + position);
        System.out.println("Gender: " + gender + "\n");
    }
}
