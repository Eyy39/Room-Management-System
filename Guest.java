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
    public String Regex(){
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(email.matches(emailRegex)){
            return "Valid email address.";
        }else{
            return "Invalid email address.";
        }
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
        
        return super.equals(obj);
    }
}
