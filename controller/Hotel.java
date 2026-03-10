package controller;
import hotel.CheckIn;
import hotel.Guest;
import java.util.ArrayList;
import room.IRoom;
import user.IStaff;

public class Hotel {

    private String hotelName;
    private String hotelAddress;
    private String hotelContact;

    private ArrayList<IRoom> rooms;
    private ArrayList<Guest> guests;
    private ArrayList<CheckIn> bookings;
    private ArrayList<IStaff> users;
    private IStaff loggedInUser;

    public Hotel(String hotelName, String hotelAddress, String hotelContact, int maxRooms) {

        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.hotelContact = hotelContact;
        rooms = new ArrayList<>();
        guests = new ArrayList<>();
        bookings = new ArrayList<>();
        users = new ArrayList<>(); 
        loggedInUser = null;
    }

    public static final String CREATE_STAFF = "CREATE_STAFF";
    public static final String CREATE_BOOKING = "CREATE_BOOKING";
    public static final String VIEW_GUESTS = "VIEW_GUESTS";
    public static final String VIEW_STAFF = "VIEW_STAFF";
    public static final String VIEW_ROOMS = "VIEW_ROOMS";
    public static final String VIEW_BOOKING_SCHEDULE = "VIEW_BOOKING_SCHEDULE";
    public static final String UPDATE_ROOM_STATUS = "UPDATE_ROOM_STATUS";
    public static final String DELETE_STAFF = "DELETE_STAFF";

    public void setHotelName(String hotelName) {
        if (hotelName != null && !hotelName.trim().isEmpty()) {
            this.hotelName = hotelName;
        }
    }
    public String getHotelName() {
        return hotelName;
    }
    public void setHotelAddress(String hotelAddress) {  
        if (hotelAddress != null && !hotelAddress.trim().isEmpty()) {
            this.hotelAddress = hotelAddress;
        }
    }
    public String getHotelAddress() {
        return hotelAddress;
    }
    public void setHotelContact(String hotelContact) {
        if (hotelContact != null && !hotelContact.trim().isEmpty()) {
            this.hotelContact = hotelContact;
        }
    }
    public String getHotelContact() {
        return hotelContact;
    }
    public void showHotelInfo() {
        System.out.println("Hotel Name: " + hotelName);
        System.out.println("Address: " + hotelAddress);
        System.out.println("Contact: " + hotelContact);
        System.out.println("Total Rooms: " + rooms.size());
        System.out.println("Total Guests: " + guests.size());
        System.out.println("Total Staff: " + users.size());
        System.out.println("Total Bookings: " + bookings.size());
    }

    public void addUser(IStaff user) {
        users.add(user);
    }

    public boolean login(String username, String password) {
        for (IStaff user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                System.out.println("Login success: " + user.getSignature());
                return true;
            }
        }
        System.out.println("Invalid username or password.");
        return false;
    }

    public void logout() {
        loggedInUser = null;
        System.out.println("Logged out successfully.");
    }

    private boolean requirePermission(String action) {
        if (loggedInUser == null) {
            System.out.println("Access Denied: Login required");
            return false;
        }
        if (!loggedInUser.can(action)) {
            System.out.println("Access Denied: " + loggedInUser.getSignature() + " cannot perform " + action);
            return false;
        }
        return true;
    }

    public void actionViewRooms() {
        if (!requirePermission(VIEW_ROOMS)) {
            return;
        }
        displayAllRooms();
    }

    public void actionViewGuests() {
        if (!requirePermission(VIEW_GUESTS)) {
            return;
        }
        showGuests();
    }

    public void actionCreateBooking(String roomType) {
        if (!requirePermission(CREATE_BOOKING)) {
            return;
        }
        findRoomsByType(roomType);
    }

    public void actionViewStaff() {
        if (!requirePermission(VIEW_STAFF)) {
            return;
        }
        showStaff();
    }

    public void actionViewBookingSchedule() {
        if (!requirePermission(VIEW_BOOKING_SCHEDULE)) {
            return;
        }
        if (bookings.isEmpty()) {
            System.out.println("No booking schedule available.");
            return;
        }
        bookings.get(0).showBookingSchedule();
    }

    public String currentUserSignature() {
        if (loggedInUser == null) {
            return "None";
        }
        return loggedInUser.getSignature();
    }

    // ROOM METHODS
    public void addRoom(IRoom room) {
        rooms.add(room);
    }

    public void deleteRoom(int roomId) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomId() == roomId) {
                rooms.remove(i);
                System.out.println("Room deleted.");
                return;
            }
        }
        System.out.println("Room not found.");
    }
        
    public void displayAllRooms() {
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }
        for (IRoom room : rooms) {
            System.out.println(room);
        }
    }

    public IRoom getRoomByIndex(int index) {
        if (index >= 0 && index < rooms.size()) {
            return rooms.get(index);
        }
        return null;
    }

    public void findRoomsByType(String type) {
        if (type == null) {
            System.out.println("Please enter a room type.");
            return;
        }

        String normalizedType = type.trim();
        if (normalizedType.isEmpty()) {
            System.out.println("Please enter a room type.");
            return;
        }

        if (normalizedType.equalsIgnoreCase("all") || normalizedType.equalsIgnoreCase("room")) {
            displayAllRooms();
            return;
        }

        boolean found = false;
        for (IRoom room : rooms) {
            if (room.getRoomType().equalsIgnoreCase(normalizedType)) {
                System.out.println(room);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms of type " + normalizedType + " found.");
        }
    }

    // GUEST METHODS
    public void addGuest(Guest guest) {
        guests.add(guest);
    }

    public void deleteGuest(String guestId) {
        for (int i = 0; i < guests.size(); i++) {
            if (guests.get(i).getGuestID().equals(guestId)) { 
                guests.remove(i);
                System.out.println("Guest deleted.");
                return;
            }
        }
        System.out.println("Guest not found.");
    }

    public void showGuests() {
        if (guests.isEmpty()) {
            System.out.println("No guests available.");
            return;
        }
        for (Guest guest : guests) {
            System.out.println(guest);
        }
    }

    public Guest getGuestByIndex(int index) {
        if (index >= 0 && index < guests.size()) {
            return guests.get(index);
        }
        return null;
    }


    public void deleteStaff(String staffId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(staffId)) {
                users.remove(i);
                System.out.println("Staff deleted.");
                return;
            }
        }
        System.out.println("Staff not found.");
    }

    public void actionDeleteStaff(int index) {
        if (!requirePermission(DELETE_STAFF)) {
            return;
        }
        if (index >= 0 && index < users.size()) {
            users.remove(index);
            System.out.println("Staff deleted.");
            return;
        }
        System.out.println("Invalid staff index.");
    }

    public void showStaff() {
        if (users.isEmpty()) {
            System.out.println("No staff available.");
            return;
        }
        for (IStaff user : users) {
            System.out.println(user.toString()); 
        }
    }

    public IStaff getStaffByIndex(int index) {
        if (index >= 0 && index < users.size()) {
            return users.get(index);
        }
        return null;
    }

    //  BOOKING METHODS
    public void bookRoom(int guestIndex, int roomIndex, int nights,
                         int staffIndex, double discount) {

        Guest guest = getGuestByIndex(guestIndex);
        IRoom room = getRoomByIndex(roomIndex);
        IStaff staff = getStaffByIndex(staffIndex);

        if (guest == null || room == null || staff == null) {
            System.out.println("Invalid booking information.");
            return;
        }

        CheckIn booking = new CheckIn(
                guest,
                room,
                "2024-10-01",
                nights,
                staff,
                discount
        );

        bookings.add(booking);
        System.out.println("Booking successful.");
        System.out.println(booking);
    }

    public void addBooking(CheckIn booking) {
        bookings.add(booking);
    }

    public void deleteBooking(int bookingId) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingID() == bookingId) {
                bookings.remove(i);
                System.out.println("Booking deleted.");
                return;
            }
        }
        System.out.println("Booking not found.");
    }

    public void showBookings() {
        for (CheckIn booking : bookings) {
            System.out.println(booking);
        }
    }
    
}

    