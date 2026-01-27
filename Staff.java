public class Staff {
    int staffId;
    String name;
    String position;
    char gender;

    Staff(int staffId, String name, String position, char gender) {
        this.staffId = staffId;
        this.name = name;
        this.position = position;
        this.gender = gender;
    }
    void displayStaff(){
        System.out.println("Staff ID: " + staffId);
        System.out.println("Name: " + name);
        System.out.println("Position: " + position);
        System.out.println("Gender: " + gender + "\n");
    }
}
