package controller;

import hotel.CheckIn;
import hotel.Guest;
import java.util.ArrayList;
import room.IRoom;
import room.RoomFilter;
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

    public String hotelInfo() {
        return "Hotel Name: " + hotelName
            + "\nAddress: " + hotelAddress
            + "\nContact: " + hotelContact
            + "\nTotal Rooms: " + rooms.size()
            + "\nTotal Guests: " + guests.size()
            + "\nTotal Staff: " + users.size()
            + "\nTotal Bookings: " + bookings.size();
    }

    public void addUser(IStaff user) {
        users.add(user);
    }

    // Login only checks credentials and stores who is using the system now.
    public boolean login(String username, String password) {
        for (IStaff user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                return true;
            }
        }
        return false;
    }

    public void logout() {
        loggedInUser = null;
    }

    // Every protected action goes through this method first.
    private boolean requirePermission(String action) {
        if (loggedInUser == null) {
            System.out.println("Access denied: login required.");
            return false;
        }
        if (!loggedInUser.can( action)) {
            System.out.println(
                "Access denied: " + loggedInUser.getSignature() + " cannot perform " + action);
                 return false;
        }
        return true;
    }

    public ArrayList<IRoom> viewRooms() {
        requirePermission(Hotel.VIEW_ROOMS);
        return getAllRooms();
    }

    public ArrayList<Guest> viewGuests() {
        requirePermission(Hotel.VIEW_GUESTS);
        return getGuestsList();
    }

    public ArrayList<IRoom> findBookableRooms(String roomType) {
        requirePermission(Hotel.CREATE_BOOKING);
        return searchRoomsByType(roomType);
    }

    public ArrayList<IStaff> viewStaff() {
        requirePermission(Hotel.VIEW_STAFF);
        return getStaffList();
    }

    public ArrayList<String> viewBookingSchedule() {
        requirePermission(Hotel.VIEW_BOOKING_SCHEDULE);
        if (bookings.isEmpty()) {
            return new ArrayList<>();
        }
        return bookings.get(0).bookingSchedule();
    }

    public String currentUserSignature() {
        if (loggedInUser == null) {
            return "None";
        }
        return loggedInUser.getSignature();
    }

    public void addRoom(IRoom room) {
        rooms.add(room);
    }

    public void deleteRoom(int roomId) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomId() == roomId) {
                rooms.remove(i);
                return;
            }
        }
        System.out.println("Room not found.");
    }

    public ArrayList<IRoom> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    public IRoom findRoomByIndex(int index) {
        if (index >= 0 && index < rooms.size()) {
            return rooms.get(index);
        }
        return null;
    }

    public ArrayList<IRoom> searchRoomsByType(String type) {
        if (type == null || type.trim().isEmpty()) {
            System.out.println("Please enter a room type.");
            return new ArrayList<>();
        }

        String normalizedType = type.trim();
        if (normalizedType.equalsIgnoreCase("all") || normalizedType.equalsIgnoreCase("room")) {
            return getAllRooms();
        }

        ArrayList<IRoom> results = new ArrayList<>();
        for (IRoom room : rooms) {
            if (room.matchesType(normalizedType)) {
                results.add(room);
            }
        }

        if (results.isEmpty()) {
            System.out.println("No rooms of type " + normalizedType + " found.");
            return new ArrayList<>();
        }
        return results;
    }

    public void addGuest(Guest guest) {
        guests.add(guest);
    }

    public void deleteGuest(String guestId) {
        for (int i = 0; i < guests.size(); i++) {
            if (guests.get(i).getGuestID().equals(guestId)) {
                guests.remove(i);
                return;
            }
        }
        System.out.println("Guest not found.");
    }

    public ArrayList<Guest> getGuestsList() {
        return new ArrayList<>(guests);
    }

    public Guest findGuestByIndex(int index) {
        if (index >= 0 && index < guests.size()) {
            return guests.get(index);
        }
        return null;
    }

    public void deleteStaff(String staffId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(staffId)) {
                users.remove(i);
                return;
            }
        }
        System.out.println("Staff not found.");
    }

    public void deleteStaffByIndex(int index) {
        requirePermission(Hotel.DELETE_STAFF);
        if (index >= 0 && index < users.size()) {
            users.remove(index);
            return;
        }
        System.out.println("Invalid staff index.");
    }

    public ArrayList<IStaff> getStaffList() {
        return new ArrayList<>(users);
    }

    public IStaff findStaffByIndex(int index) {
        if (index >= 0 && index < users.size()) {
            return users.get(index);
        }
        return null;
    }

    public CheckIn bookRoom(int guestIndex, int roomIndex, int nights, int staffIndex, double discountPercent) {
        Guest guest = findGuestByIndex(guestIndex);
        IRoom room = findRoomByIndex(roomIndex);
        IStaff staff = findStaffByIndex(staffIndex);

        if (guest == null || room == null || staff == null) {
            System.out.println("Invalid booking information.");
            return null;
        }

        // Tell the room to update its own state.
        room.book();

        CheckIn booking = new CheckIn(
            guest,
            room,
            "2024-10-01",
            nights,
            staff,
            discountPercent
        );

        bookings.add(booking);
        return booking;
    }

    public void addBooking(CheckIn booking) {
        bookings.add(booking);
    }

    public void deleteBooking(int bookingId) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingID() == bookingId) {
                bookings.remove(i);
                return;
            }
        }
        System.out.println("Booking not found.");
    }

    public ArrayList<CheckIn> getBookingsList() {
        return new ArrayList<>(bookings);
    }

    // filterRooms - accepts a RoomFilter (lambda or anonymous class) and returns matching rooms.
    // This method doesn't care HOW the filter decides - it just calls filter.test() for each room.
    public ArrayList<IRoom> filterRooms(RoomFilter filter) {
        ArrayList<IRoom> results = new ArrayList<>();
        for (IRoom room : getAllRooms()) {
            if (filter.test(room)) {
                results.add(room);
            }
        }
        return results;
    }
}
