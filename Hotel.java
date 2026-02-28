import java.util.ArrayList;

public class Hotel {

    private String hotelName;
    private String hotelAddress;
    private String hotelContact;

    private ArrayList<Room> rooms;
    private ArrayList<Guest> guests;
    // private ArrayList<Staff> staffMembers;
    private ArrayList<CheckIn> bookings;
    private ArrayList<IStaff> users;             // login/permission users
    private ArrayList<Staff> staffMembers;        // actual hotel employees
    private IStaff loggedInUser;

    private int roomCount;
    private int guestCount;
    private int staffCount;
    private int bookingCount;

    public Hotel(String hotelName, String hotelAddress, String hotelContact, int maxRooms) {

        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.hotelContact = hotelContact;
        rooms = new ArrayList<>();
        guests = new ArrayList<>();
        bookings = new ArrayList<>();
        users = new ArrayList<>(); 
        staffMembers = new ArrayList<>();
        loggedInUser = null;

        roomCount = 0;
        guestCount = 0;
        staffCount = 0;
        bookingCount = 0;
    }

    public static final String CREATE_STAFF = "CREATE_STAFF";
    public static final String CREATE_BOOKING = "CREATE_BOOKING";
    public static final String VIEW_GUESTS = "VIEW_GUESTS";
    public static final String VIEW_STAFF = "VIEW_STAFF";
    public static final String VIEW_ROOMS = "VIEW_ROOMS";
    public static final String VIEW_BOOKING_SCHEDULE = "VIEW_BOOKING_SCHEDULE";
    public static final String UPDATE_ROOM_STATUS = "UPDATE_ROOM_STATUS";

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
        System.out.println("Total Rooms: " + roomCount);
        System.out.println("Total Guests: " + guestCount);
        System.out.println("Total Staff: " + staffCount);
        System.out.println("Total Bookings: " + bookingCount);
    }

    public void addUser(IStaff user) {
        users.add(user);
    }

    public boolean login(String username, String password) {
        for (IStaff user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                System.out.println("Login success as " + user.getRole());
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
            System.out.println("Access Denied: Role " + loggedInUser.getRole() + " cannot perform " + action);
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
        if (bookingCount == 0) {
            System.out.println("No booking schedule available.");
            return;
        }
        bookings.get(0).showBookingSchedule();
    }

    public String currentUserRole() {
        if (loggedInUser == null) {
            return "None";
        }
        return loggedInUser.getRole();
    }
    // ROOM METHODS
    public void addRoom(Room room) {
        rooms.add(room);
        roomCount++;
    }

    public void deleteRoom(int roomId) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomId() == roomId) {
                rooms.remove(i);
                roomCount--;
                System.out.println("Room deleted.");
                return;
            }
        }
        System.out.println("Room not found.");
    }
        
    

    public void displayAllRooms() {
        if (roomCount == 0) {
            System.out.println("No rooms available.");
            return;
        }
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    public Room getRoomByIndex(int index) {
        if (index >= 0 && index < roomCount) {
            return rooms.get(index);
        }
        return null;
    }
    public void findRoomsByType(String type) {
        boolean found = false;
        for (int i = 0; i < roomCount; i++) {
            if (rooms.get(i).getRoomType().equals(type)) {
                System.out.println(rooms.get(i));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms of type " + type + " found.");
        }
    }

    // GUEST METHODS
    public void addGuest(Guest guest) {
        guests.add(guest);
        guestCount++;
    }

    public void deleteGuest(String guestId) {
        for (int i = 0; i < guestCount; i++) {
            if (guests.get(i).getGuestID().equals(guestId)) { 
                guests.remove(i);
                guestCount--;
                System.out.println("Guest deleted.");
                return;
            }
        }
        System.out.println("Guest not found.");
    }

    public void showGuests() {
        if (guestCount == 0) {
            System.out.println("No guests available.");
            return;
        }
        for (Guest guest : guests) {
            System.out.println(guest);
        }
    }

    public Guest getGuestByIndex(int index) {
        if (index >= 0 && index < guestCount) {
            return guests.get(index);
        }
        return null;
    }

    // STAFF METHODS 
    public void addStaff(Staff staff) {
        staffMembers.add(staff);
        staffCount++;
    }

    public void deleteStaff(String staffId) {
        for (int i = 0; i < staffCount; i++) {
            if (staffMembers.get(i).getStaffId().equals(staffId)) {
                staffMembers.remove(i);
                staffCount--;
                System.out.println("Staff deleted.");
                return;
            }
        }
        System.out.println("Staff not found.");
    }

    public void showStaff() {
        if (staffCount == 0) {
            System.out.println("No staff available.");
            return;
        }
        for (Staff staff : staffMembers) {
            System.out.println(staff);
        }
    }

    public Staff getStaffByIndex(int index) {
        if (index >= 0 && index < staffCount) {
            return staffMembers.get(index);
        }
        return null;
    }

    //  BOOKING METHODS
    public void bookRoom(int guestIndex, int roomIndex, int nights,
                         int staffIndex, double discount) {

        Guest guest = getGuestByIndex(guestIndex);
        Room room = getRoomByIndex(roomIndex);
        Staff staff = getStaffByIndex(staffIndex);

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
        bookingCount++;
        System.out.println("Booking successful.");
        System.out.println(booking);
    }

    public void addBooking(CheckIn booking) {
        bookings.add(booking);
        bookingCount++;
    }
    public void deleteBooking(int bookingId) {
        for (int i = 0; i < bookingCount; i++) {
            if (bookings.get(i).getBookingID() == bookingId) {
                bookings.remove(i);
                bookingCount--;
                bookings.set(bookingCount, null);
                System.out.println("Booking deleted.");
                return;
            }
        }
        System.out.println("Booking not found.");
    }

    public void showBookings() {
        for (int i = 0; i < bookingCount; i++) {
            System.out.println(bookings.get(i));
        }
    }
    
}

    