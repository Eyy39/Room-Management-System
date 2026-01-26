public class Staff {
    private int staffId;
    private String name;
    private String position;

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
