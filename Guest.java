public class Guest {
    private String guestName;
    private String guestID;
    private String phoneNumber;
    private String email;
    private static int guestCounter = 0;

    public Guest(String guestName, String phoneNumber, String email){
        this.setGuestName(guestName);
        this.guestID = generateGuestID();
        this.setPhoneNumber(phoneNumber);
        this.setEmail(email);
    }
    public String Regex(){
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(email.matches(emailRegex)){
            return "Valid email address.";
        }else{
            return "Invalid email address.";
        }
    }
    private String generateGuestID(){
        return "G" + (++guestCounter);
    }
    public String getGuestName() {
        return guestName;
    }
    public void setGuestName(String guestName) {
        if (guestName == null || guestName.trim().isEmpty()) {
            System.out.println("Invalid guest name. Name not updated.");
            return;
        }
        this.guestName = guestName.trim();
    }
    public String getGuestID() {
        return guestID;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            System.out.println("Invalid phone number. Phone not updated.");
            return;
        }
        this.phoneNumber = phoneNumber.trim();
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        if (!isEmailValid(email)) {
            System.out.println("Invalid email address. Email not updated.");
            return;
        }
        this.email = email;
    }
    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
    public static int getGuestCounter() {
        return guestCounter;
    }
    @Override
    public String toString() {
        return "Guest Name: " + guestName + "\nGuest ID: " + guestID
        + "\nPhone Number: " + phoneNumber + "\nEmail: " + email + "\n";
    }
    
    @Override
    public boolean equals(Object obj) {
        Guest other = (Guest) obj;
        if (guestName == null) {
            if (other.guestName != null)
                return false;
        } else if (!guestName.equals(other.guestName))
            return false;
        if (guestID == null) {
            if (other.guestID != null)
                return false;
        } else if (!guestID.equals(other.guestID))
            return false;
        return true;
    }
    
}
