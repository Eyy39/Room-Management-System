package controller;

import hotel.BookingStatus;
import hotel.CheckIn;
import hotel.Guest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import room.IRoom;
import room.RoomFilter;
import room.RoomStatus;
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
        if (!loggedInUser.can(action)) {
            System.out.println(
                "Access denied: " + loggedInUser.getSignature() + " cannot perform " + action);
                 return false;
        }
        return true;
    }

    public ArrayList<IRoom> viewRooms() {
        if (!requirePermission(Hotel.VIEW_ROOMS)) {
            return new ArrayList<>();
        }
        return getAllRooms();
    }

    public ArrayList<Guest> viewGuests() {
        if (!requirePermission(Hotel.VIEW_GUESTS)) {
            throw new SecurityException("No permission to view guests.");
        }
        return new ArrayList<>(getGuestsList());
    }

    public ArrayList<IRoom> findBookableRooms(String roomType) {
        if (!requirePermission(Hotel.CREATE_BOOKING)) {
            throw new SecurityException("No permission to create booking.");
        }

        ArrayList<IRoom> typedRooms = searchRoomsByType(roomType);
        ArrayList<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : typedRooms) {
            if (room.getStatus() == RoomStatus.AVAILABLE) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public ArrayList<IStaff> viewStaff() {
        if (!requirePermission(Hotel.VIEW_STAFF)) {
            throw new SecurityException("No permission to view staff.");
        }
        return new ArrayList<>(getStaffList());
    }

    public ArrayList<String> viewBookingSchedule() {
        if (!requirePermission(Hotel.VIEW_BOOKING_SCHEDULE)) {
            return new ArrayList<>();
        }
        if (bookings.isEmpty()) {
            return new ArrayList<>();
        }
        return bookings.get(0).bookingSchedule();
    }

    public ArrayList<IRoom> getBookedRoomsByDate(LocalDate selectedDate) {
        if (!requirePermission(Hotel.VIEW_BOOKING_SCHEDULE)) {
            return new ArrayList<>();
        }

        ArrayList<IRoom> bookedRooms = new ArrayList<>();
        for (IRoom room : rooms) {
            if (isRoomBookedOnDate(room, selectedDate)) {
                bookedRooms.add(room);
            }
        }
        return bookedRooms;
    }

    public ArrayList<IRoom> getAvailableRoomsByDate(LocalDate selectedDate) {
        if (!requirePermission(Hotel.VIEW_BOOKING_SCHEDULE)) {
            return new ArrayList<>();
        }

        ArrayList<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : rooms) {
            if (!isRoomBookedOnDate(room, selectedDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    private boolean isRoomBookedOnDate(IRoom room, LocalDate selectedDate) {
        if (selectedDate == null) {
            return false;
        }

        for (CheckIn booking : bookings) {
            if (booking.getStatus() == BookingStatus.CANCELLED
                || booking.getStatus() == BookingStatus.CHECKED_OUT) {
                continue;
            }

            if (!booking.getRoom().equals(room)) {
                continue;
            }

            LocalDate checkInDate;
            try {
                checkInDate = LocalDate.parse(booking.getCheckIn());
            } catch (RuntimeException ex) {
                continue;
            }

            int bookedNights = booking.getNight();
            if (bookedNights <= 0) {
                bookedNights = 1;
            }

            LocalDate checkOutDate = checkInDate.plusDays(bookedNights);
            if (!selectedDate.isBefore(checkInDate) && selectedDate.isBefore(checkOutDate)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<CheckIn> GuestInfo(){
        return new ArrayList<>(bookings);
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
        if (normalizedType.equals("all") || normalizedType.equalsIgnoreCase("room")) {
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
        if (!requirePermission(Hotel.DELETE_STAFF)) {
            return;
        }
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

    public CheckIn bookRoomByNumber(String roomNumber, String guestName) {
        if (!requirePermission(Hotel.CREATE_BOOKING)) {
            return null;
        }

        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            System.out.println("Room number is required.");
            return null;
        }

        if (guestName == null || guestName.trim().isEmpty()) {
            System.out.println("Guest name is required.");
            return null;
        }

        IRoom selectedRoom = null;
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomNumber.trim())) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room number not found.");
            return null;
        }

        if (selectedRoom.getStatus() != RoomStatus.AVAILABLE) {
            System.out.println("Room is not available for booking.");
            return null;
        }

        Guest bookingGuest = null;
        for (Guest guest : guests) {
            if (guest.getGuestName().equals(guestName.trim())) {
                bookingGuest = guest;
                break;
            }
        }

        if (bookingGuest == null) {
            bookingGuest = new Guest(guestName.trim(), "N/A", guestName.trim());
            guests.add(bookingGuest);
        }

        selectedRoom.book();

        CheckIn booking = new CheckIn(
            bookingGuest,
            selectedRoom,
            LocalDate.now().toString(),
            1,
            loggedInUser,
            0.0
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

    // Schedule display methods
    public void displayWeeklySchedule() {
        System.out.println("\n=========================== WEEKLY ROOM AVAILABILITY SCHEDULE (Next 7 Days) ============================");
        LocalDate today = LocalDate.now();
        ArrayList<IRoom> allRooms = getAllRooms();
        DateTimeFormatter headerFormatter = DateTimeFormatter.ofPattern("EEE dd-MMM", Locale.ENGLISH);

        if (allRooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        // Print header with dates
        System.out.print(String.format("%-12s", "Room No"));
        for (int i = 0; i < 7; i++) {
            LocalDate date = today.plusDays(i);
            String dateHeader = date.format(headerFormatter);
            System.out.print(String.format("| %-10s ", dateHeader));
        }
        System.out.println("|");
        System.out.println("========================================================================================================");

        // Print each room's availability for 7 days
        for (IRoom room : allRooms) {
            System.out.print(String.format("%-12s", room.getRoomNumber()));
            for (int i = 0; i < 7; i++) {
                LocalDate date = today.plusDays(i);
                boolean isBooked = !getBookedRoomsByDate(date).isEmpty() &&
                                  getBookedRoomsByDate(date).contains(room);
                String status = isBooked ? "BOOKED" : "FREE";
                System.out.print(String.format("| %-10s ", status));
            }
            System.out.println("|");
        }
        System.out.println("========================================================================================================");
        System.out.println("Legend: FREE = Available for booking | BOOKED = Room is reserved");
    }

    public void displayDaySchedule(LocalDate date) {
        ArrayList<IRoom> bookedRooms = getBookedRoomsByDate(date);
        ArrayList<IRoom> availableRooms = getAvailableRoomsByDate(date);

        printRoomTable("Booked rooms on " + date, bookedRooms);
        printRoomTable("Available rooms on " + date, availableRooms);
    }

    private void printRoomTable(String title, ArrayList<IRoom> rooms) {
        System.out.println("\n" + title + ":");
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
            return;
        }

        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-4s %-12s %-12s %-12s %-12s%n", "No.", "Room No", "Type", "Price", "Status");
        System.out.println("----------------------------------------------------------------");

        for (int i = 0; i < rooms.size(); i++) {
            IRoom room = rooms.get(i);
            System.out.printf(
                "%-4d %-12s %-12s $%-11.2f %-12s%n",
                i + 1,
                room.getRoomNumber(),
                room.getRoomType(),
                room.getPricePerNight(),
                room.getStatus()
            );
        }
        System.out.println("----------------------------------------------------------------");
    }

}
