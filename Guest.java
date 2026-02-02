public class Guest {
    String guestName;
    int guestID;
    String phoneNumber;

    public Guest(String guestName, int guestID, String phoneNumber){
        this.guestName = guestName;
        this.guestID = guestID;
        this.phoneNumber = phoneNumber;
    }
    @Override
    public String toString() {
        return "Guest Name: " + guestName + "\nGuest ID: " + guestID
        + "\nPhone Number: " +phoneNumber + "\n";
    }
}
