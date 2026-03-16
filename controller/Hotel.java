package controller;

import exception.AuthenticationException;
import exception.AuthorizationException;
import exception.EntityNotFoundException;
import exception.ValidationException;
import hotel.CheckIn;
import hotel.Guest;
import room.IRoom;
import user.IStaff;
import user.Permission;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String hotelName;
    private String hotelAddress;
    private String hotelContact;

    private final List<IRoom> rooms;
    private final List<Guest> guests;
    private final List<CheckIn> bookings;
    private final List<IStaff> users;
    private IStaff loggedInUser;

    public Hotel(String hotelName, String hotelAddress, String hotelContact, int maxRooms) {
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.hotelContact = hotelContact;
        this.rooms = new ArrayList<>();
        this.guests = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.users = new ArrayList<>();
        this.loggedInUser = null;
    }

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

    public boolean login(String username, String password) {
        for (IStaff user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                return true;
            }
        }
        throw new AuthenticationException("Invalid username or password.");
    }

    public void logout() {
        loggedInUser = null;
    }

    private void requirePermission(Permission permission) {
        if (loggedInUser == null) {
            throw new AuthorizationException("Access denied: login required.");
        }
        if (!loggedInUser.can(permission)) {
            throw new AuthorizationException(
                "Access denied: " + loggedInUser.getSignature() + " cannot perform " + permission
            );
        }
    }

    public List<IRoom> actionViewRooms() {
        requirePermission(Permission.VIEW_ROOMS);
        return displayAllRooms();
    }

    public List<Guest> actionViewGuests() {
        requirePermission(Permission.VIEW_GUESTS);
        return showGuests();
    }

    public List<IRoom> actionCreateBooking(String roomType) {
        requirePermission(Permission.CREATE_BOOKING);
        return findRoomsByType(roomType);
    }

    public List<IStaff> actionViewStaff() {
        requirePermission(Permission.VIEW_STAFF);
        return showStaff();
    }

    public List<String> actionViewBookingSchedule() {
        requirePermission(Permission.VIEW_BOOKING_SCHEDULE);
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
        throw new EntityNotFoundException("Room not found.");
    }

    public List<IRoom> displayAllRooms() {
        return new ArrayList<>(rooms);
    }

    public IRoom getRoomByIndex(int index) {
        if (index >= 0 && index < rooms.size()) {
            return rooms.get(index);
        }
        return null;
    }

    public List<IRoom> findRoomsByType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new ValidationException("Please enter a room type.");
        }

        String normalizedType = type.trim();
        if (normalizedType.equalsIgnoreCase("all") || normalizedType.equalsIgnoreCase("room")) {
            return displayAllRooms();
        }

        List<IRoom> results = new ArrayList<>();
        for (IRoom room : rooms) {
            if (room.matchesType(normalizedType)) {
                results.add(room);
            }
        }

        if (results.isEmpty()) {
            throw new EntityNotFoundException("No rooms of type " + normalizedType + " found.");
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
        throw new EntityNotFoundException("Guest not found.");
    }

    public List<Guest> showGuests() {
        return new ArrayList<>(guests);
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
                return;
            }
        }
        throw new EntityNotFoundException("Staff not found.");
    }

    public void actionDeleteStaff(int index) {
        requirePermission(Permission.DELETE_STAFF);
        if (index >= 0 && index < users.size()) {
            users.remove(index);
            return;
        }
        throw new ValidationException("Invalid staff index.");
    }

    public List<IStaff> showStaff() {
        return new ArrayList<>(users);
    }

    public IStaff getStaffByIndex(int index) {
        if (index >= 0 && index < users.size()) {
            return users.get(index);
        }
        return null;
    }

    public CheckIn bookRoom(int guestIndex, int roomIndex, int nights, int staffIndex, BigDecimal discountPercent) {
        Guest guest = getGuestByIndex(guestIndex);
        IRoom room = getRoomByIndex(roomIndex);
        IStaff staff = getStaffByIndex(staffIndex);

        if (guest == null || room == null || staff == null) {
            throw new ValidationException("Invalid booking information.");
        }

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
        throw new EntityNotFoundException("Booking not found.");
    }

    public List<CheckIn> showBookings() {
        return new ArrayList<>(bookings);
    }
}
