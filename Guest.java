public class Guest {
    String guestName;
    String guestID;
    String phoneNumber;
    String email;
    static int guestCounter = 0;

    public Guest(String guestName, String phoneNumber, String email){
        this.guestName = guestName;
        this.guestID = generateGuestID();
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    String generateGuestID(){
        return "G" + (++guestCounter);
    }
    @Override
    public String toString() {
        return "Guest Name: " + guestName + "\nGuest ID: " + guestID
        + "\nPhone Number: " +phoneNumber + "\nEmail: " + email + "\n";
    }
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }
}
