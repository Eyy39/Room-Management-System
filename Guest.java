public class Guest {
    String guestName;
    int guestID;
    String phoneNumber;
    int roomNumber;

    public Guest(String guestName, int guestID, String phoneNumber){
        this.guestName = guestName;
        this.guestID = guestID;
        this.phoneNumber = phoneNumber;
    }

    void  displayGuest(){
        System.out.println("Guest Name: " + guestName);
        System.out.println("Guest ID: " + guestID);
        System.out.println("Phone Number: " + phoneNumber);
    }

}
