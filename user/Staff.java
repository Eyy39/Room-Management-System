package user;

import common.BaseEntity;
import java.util.Objects;

public abstract class Staff extends BaseEntity implements IStaff {
    private String name;
    private char gender;
    private String phoneNubmer;
    private String password;

    public Staff(String name, char gender, String phoneNumber, String password) {
        super("ST");
        this.setName(name);
        this.setGender(gender);;
        this.setPhoneNumber(phoneNumber);
        this.setPassword(password);
    }

    public Staff(String id, String name, char gender, String phoneNumber, String password) {
        super("ST", id);
        this.setName(name);
        this.setGender(gender);
        this.setPhoneNumber(phoneNumber);
        this.setPassword(password);
    }

    public String getStaffId() {
        return getId();
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        if(name == null || name.trim().isEmpty()){
            System.out.println("Invalid name. Name not updated.");
            return;
        }
        this.name = name.trim();
    }
    public char getGender() {
        return gender;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Invalid password.");
            return;
        }
        this.password = password;
    }
    public String getPhoneNumber() {
        return phoneNubmer;
    }
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            System.out.println("Invalid phone number. Phone number not updated.");
            return;
        }
        this.phoneNubmer = phoneNumber.trim();
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public String getPassword() {
        return password != null ? password : "";
    }

    @Override
    public abstract boolean can(String action);

    @Override
    public String getSignature() {
        return "[Staff] " + name;
    }

    @Override
    public String toString() {
        return "Staff Id: " + getStaffId() + "\nName: " + getName() + "\nGender: " + getGender() + 
        "\nPhone Number: " + getPhoneNumber() + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Staff other = (Staff) obj;
        return gender == other.gender
            && Objects.equals(getStaffId(), other.getStaffId())
            && Objects.equals(name, other.name);
    }
 
}
