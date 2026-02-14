import java.util.Arrays;

public class Hotel {

    private String hotelName;
    private String hotelAddress;
    private String hotelContact;

    private Room[] rooms;
    private Guest[] guests;
    private Staff[] staffMembers;
    private CheckIn[] bookings;

    private int roomCount;
    private int guestCount;
    private int staffCount;
    private int bookingCount;

    public Hotel(String hotelName, String hotelAddress, String hotelContact, int maxRooms) {

        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.hotelContact = hotelContact;

        rooms = new Room[maxRooms];
        guests = new Guest[50];
        staffMembers = new Staff[20];
        bookings = new CheckIn[100];

        roomCount = 0;
        guestCount = 0;
        staffCount = 0;
        bookingCount = 0;
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
    public void showHotelInfo() {
        System.out.println("Hotel Name: " + hotelName);
        System.out.println("Address: " + hotelAddress);
        System.out.println("Contact: " + hotelContact);
        System.out.println("Total Rooms: " + roomCount);
        System.out.println("Total Guests: " + guestCount);
        System.out.println("Total Staff: " + staffCount);
        System.out.println("Total Bookings: " + bookingCount);
    }
       // ================= ROOM METHODS =================
    public void addRoom(Room room) {
        if (roomCount < rooms.length) {
            rooms[roomCount++] = room;
        } else {
            System.out.println("Room list is full.");
        }
    }

    public void deleteRoom(int roomId) {
        for (int i = 0; i < roomCount; i++) {
            if (rooms[i].getRoomId() == roomId) {
                for (int j = i; j < roomCount - 1; j++) {
                    rooms[j] = rooms[j + 1];
                }
                rooms[--roomCount] = null;
                System.out.println("Room deleted.");
                return;
            }
        }
        System.out.println("Room not found.");
    }

    public void displayAllRooms() {
        // for (int i = 0; i < roomCount; i++) {
        //     System.out.println(rooms[i]);
        // }
        System.err.println(Arrays.toString(rooms));
    }

    public Room getRoomByIndex(int index) {
        if (index >= 0 && index < roomCount) {
            return rooms[index];
        }
        return null;
    }
    public void findRoomsByType(String type) {
        boolean found = false;
        for (int i = 0; i < roomCount; i++) {
            if (rooms[i].getRoomType().equals(type)) {
                System.out.println(rooms[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms of type " + type + " found.");
        }
    }

    // ================= GUEST METHODS =================
    public void addGuest(Guest guest) {
        guests[guestCount++] = guest;
    }

    public void deleteGuest(String guestId) {
        for (int i = 0; i < guestCount; i++) {
            if (guests[i].getGuestID().equals(guestId)) {
                for (int j = i; j < guestCount - 1; j++) {
                    guests[j] = guests[j + 1];
                }
                guests[--guestCount] = null;
                System.out.println("Guest deleted.");
                return;
            }
        }
        System.out.println("Guest not found.");
    }

    public void showGuests() {
        for (int i = 0; i < guestCount; i++) {
            System.out.println(guests[i]);
        }
    }

    public Guest getGuestByIndex(int index) {
        if (index >= 0 && index < guestCount) {
            return guests[index];
        }
        return null;
    }

    // ================= STAFF METHODS =================
    public void addStaff(Staff staff) {
        staffMembers[staffCount++] = staff;
    }

    public void deleteStaff(String staffId) {
        for (int i = 0; i < staffCount; i++) {
            if (staffMembers[i].getStaffId().equals(staffId)) {
                for (int j = i; j < staffCount - 1; j++) {
                    staffMembers[j] = staffMembers[j + 1];
                }
                staffMembers[--staffCount] = null;
                System.out.println("Staff deleted.");
                return;
            }
        }
        System.out.println("Staff not found.");
    }

    public void showStaff() {
        for (int i = 0; i < staffCount; i++) {
            System.out.println(staffMembers[i]);
        }
    }

    public Staff getStaffByIndex(int index) {
        if (index >= 0 && index < staffCount) {
            return staffMembers[index];
        }
        return null;
    }

    // ================= BOOKING METHODS =================
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

        bookings[bookingCount++] = booking;
        System.out.println("Booking successful.");
        System.out.println(booking);
    }

    public void addBooking(CheckIn booking) {
        if (bookingCount < bookings.length) {
            bookings[bookingCount++] = booking;
        } else {
            System.out.println("Booking list is full.");
        }
    }
    public void deleteBooking(int bookingId) {
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getBookingID() == bookingId) {
                for (int j = i; j < bookingCount - 1; j++) {
                    bookings[j] = bookings[j + 1];
                }
                bookings[--bookingCount] = null;
                System.out.println("Booking deleted.");
                return;
            }
        }
        System.out.println("Booking not found.");
    }

    public void showBookings() {
        for (int i = 0; i < bookingCount; i++) {
            System.out.println(bookings[i]);
        }
    }
    
}

    