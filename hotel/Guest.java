package hotel;

import common.BaseEntity;
import java.util.Objects;

public class Guest extends BaseEntity {
    private String guestName;
    private String phoneNumber;
    private String email;

    public Guest(String guestName, String phoneNumber, String email){
        super("G");
        this.setGuestName(guestName);;
        this.setPhoneNumber(phoneNumber);;
        this.setEmail(email);;
    }
    public String Regex(){
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(email.matches(emailRegex)){
            return "Valid email address.";
        }else{
            return "Invalid email address.";
        }
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
        return getId();
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
    @Override
    public String toString() {
        return "Guest Name: " + guestName + "\nGuest ID: " + getGuestID()
        + "\nPhone Number: " + phoneNumber + "\nEmail: " + email + "\n";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Guest)) {
            return false;
        }
        Guest other = (Guest) obj;
        return Objects.equals(guestName, other.guestName)
            && Objects.equals(getGuestID(), other.getGuestID());
    }
}
