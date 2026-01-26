public class Staff {
    int staffId;
    String name;
    String position;

    Staff(int staffId, String name, String position) {
        this.staffId = staffId;
        this.name = name;
        this.position = position;
    }
    void displayStaff(){
        System.out.println("Staff ID: " + staffId);
        System.out.println("Name: " + name);
        System.out.println("Position: " + position + "\n");
    }
}
