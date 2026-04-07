package controller;

import exception.InputMismatchException;
import exception.PermissionDeniedException;
import hotel.BookingStatus;
import hotel.CheckIn;
import hotel.Guest;
import hotel.Payment;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import room.IRoom;
import room.RoomFilter;
import room.RoomStatus;
import user.IStaff;
import util.InputHandler;

public class Hotel {

    private String hotelName;
    private String hotelAddress;
    private String hotelContact;

    private ArrayList<IRoom> rooms;
    private ArrayList<Guest> guests;
    private ArrayList<CheckIn> bookings;
    private ArrayList<Payment> payments;
    private ArrayList<IStaff> users;
    private IStaff loggedInUser;

    public Hotel(String hotelName, String hotelAddress, String hotelContact, int maxRooms) {
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.hotelContact = hotelContact;
        rooms = new ArrayList<>();
        guests = new ArrayList<>();
        bookings = new ArrayList<>();
        payments = new ArrayList<>();
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
    public static final String PAY_BOOKING = "PAY_BOOKING";

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

    public boolean hasUsername(String username) {
        if (username == null) {
            return false;
        }
        for (IStaff user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
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

        LocalDate today = LocalDate.now();
        for (IRoom room : rooms) {
            if (room.getStatus() == RoomStatus.MAINTENANCE) {
                continue;
            }
            if (isRoomBookedOnDate(room, today)) {
                room.setStatus(RoomStatus.OCCUPIED);
            } else {
                room.setStatus(RoomStatus.AVAILABLE);
            }
        }

        return getAllRooms();
    }

    public ArrayList<Guest> viewGuests() throws PermissionDeniedException {
        if (!requirePermission(Hotel.VIEW_GUESTS)) {
            throw new PermissionDeniedException("No permission to view guests.");
        }
        return new ArrayList<>(getGuestsList());
    }

    public ArrayList<IRoom> findBookableRooms(String roomType) throws PermissionDeniedException {
        return findBookableRoomsByDate(roomType, LocalDate.now());
    }

    public ArrayList<IRoom> findBookableRoomsByDate(String roomType, LocalDate selectedDate) throws PermissionDeniedException {
        if (!requirePermission(Hotel.CREATE_BOOKING)) {
            throw new PermissionDeniedException("No permission to create booking.");
        }

        ArrayList<IRoom> typedRooms = searchRoomsByType(roomType);
        ArrayList<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : typedRooms) {
            // Date-based availability: allow rooms that are not under maintenance and not booked on selected date.
            if (room.getStatus() != RoomStatus.MAINTENANCE && !isRoomBookedOnDate(room, selectedDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public ArrayList<IStaff> viewStaff() throws PermissionDeniedException {
        if (!requirePermission(Hotel.VIEW_STAFF)) {
            throw new PermissionDeniedException("No permission to view staff.");
        }
        return new ArrayList<>(getStaffList());
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

            if (booking.getRoom() == null || room.getRoomNumber() == null) {
                continue;
            }

            if (!room.getRoomNumber().equalsIgnoreCase(booking.getRoom().getRoomNumber())) {
                continue;
            }

            LocalDate checkInDate;
            try {
                checkInDate = LocalDate.parse(booking.getCheckIn());
            } catch (DateTimeParseException ex) {
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

    public ArrayList<String> getGuestInfoWithPaymentStatus() {
        ArrayList<String> details = new ArrayList<>();
        for (Guest guest : guests) {
            ArrayList<CheckIn> guestBookings = findBookingsByGuestId(guest.getGuestID());

            if (guestBookings.isEmpty()) {
                details.add(guest.toString() + "\nPayment Status: Pending\n--------------------------------------");
                continue;
            }

            for (CheckIn booking : guestBookings) {
                String paymentStatus = getPaymentStatusByBookingCode(booking.getBookingCode());
                if ("No payment record".equals(paymentStatus)) {
                    paymentStatus = "Pending";
                }
                details.add(booking + "\nPayment Status: " + paymentStatus + "\n--------------------------------------");
            }
        }

        return details;
    }

    private ArrayList<CheckIn> findBookingsByGuestId(String guestId) {
        ArrayList<CheckIn> results = new ArrayList<>();
        if (guestId == null || guestId.trim().isEmpty()) {
            return results;
        }

        for (CheckIn booking : bookings) {
            if (booking.getGuest() == null) {
                continue;
            }
            if (guestId.equals(booking.getGuest().getGuestID())) {
                results.add(booking);
            }
        }
        return results;
    }

    public String getPaymentStatusByBookingCode(String bookingCode) {
        Payment payment = findPaymentByBookingCode(bookingCode);
        if (payment == null) {
            return "No payment record";
        }
        return payment.isPaid() ? "Paid" : "Pending";
    }

    public void validatePayableBookingCode(String bookingCode) throws InputMismatchException {
        if (bookingCode == null || bookingCode.trim().isEmpty()) {
            throw new InputMismatchException("Booking code cannot be empty.");
        }

        String normalizedCode = bookingCode.trim();
        if (!normalizedCode.matches("^B\\d{3}$")) {
            throw new InputMismatchException("Invalid booking code format.");
        }

        Payment payment = findPaymentByBookingCode(normalizedCode);
        if (payment == null) {
            throw new InputMismatchException("Booking code not found: " + normalizedCode);
        }

        if (payment.isPaid()) {
            throw new InputMismatchException("Booking " + normalizedCode + " is already paid.");
        }
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
        if (normalizedType.equals("all") || normalizedType.equals("room")) {
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

    public CheckIn bookRoomByNumber(String roomNumber, String guestName, LocalDate bookingDate, int numberOfNights) throws InputMismatchException {
        if (!requirePermission(Hotel.CREATE_BOOKING)) {
            return null;
        }

        validateBookingInputs(roomNumber, guestName, bookingDate, numberOfNights);

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

        // For future booking, block only maintenance rooms; date overlap is checked below.
        if (selectedRoom.getStatus() == RoomStatus.MAINTENANCE) {
            System.out.println("Room is under maintenance and cannot be booked.");
            return null;
        }

        if (isRoomBookedOnDate(selectedRoom, bookingDate)) {
            System.out.println("Room is already booked for " + bookingDate + ".");
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
            bookingGuest = new Guest(guestName.trim(), "N/A", "unknown@email.com");
            guests.add(bookingGuest);
        }

        selectedRoom.book();

        CheckIn booking = new CheckIn(
            bookingGuest,
            selectedRoom,
            bookingDate.toString(),
            numberOfNights,
            loggedInUser,
            0.0
        );

        bookings.add(booking);
        createPaymentForBooking(booking);
        return booking;
    }

    private void validateBookingInputs(String roomNumber, String guestName, LocalDate bookingDate, int numberOfNights) throws InputMismatchException {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new InputMismatchException("Room number cannot be empty.");
        }else if (guestName == null || guestName.trim().isEmpty()) {
            throw new InputMismatchException("Guest name cannot be empty.");
        }else if (guestName.trim().matches("^-?\\d+$")) {
            throw new InputMismatchException("Guest name cannot be integer.");
        }else if (bookingDate == null) {
            throw new InputMismatchException("Booking date cannot be empty.");
        }else if (bookingDate.isBefore(LocalDate.now())) {
            throw new InputMismatchException("Booking date cannot be in the past.");
        }else if (numberOfNights <= 0) {
            throw new InputMismatchException("Number of nights must be greater than 0.");
        }
    }

    public void addBooking(CheckIn booking) {
        bookings.add(booking);
        createPaymentForBooking(booking);
    }

    public void addBookingWithoutPayment(CheckIn booking) {
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

    public void addPayment(Payment payment) {
        if (payment != null) {
            payments.add(payment);
        }
    }

    public ArrayList<Payment> getPaymentsList() {
        return new ArrayList<>(payments);
    }

    public Payment createPaymentForBooking(CheckIn booking) {
        if (booking == null) {
            return null;
        }

        Payment existing = findPaymentByBookingCode(booking.getBookingCode());
        if (existing != null) {
            return existing;
        }

        Payment payment = new Payment(null, booking.getBookingCode(), booking.getTotal(), "Pending");
        payments.add(payment);
        return payment;
    }

    public Payment findPaymentByBookingCode(String bookingCode) {
        if (bookingCode == null) {
            return null;
        }

        for (Payment payment : payments) {
            if (payment.getBookingId().equals(bookingCode.trim())) {
                return payment;
            }
        }
        return null;
    }

    public ArrayList<Payment> getPendingPayments() {// return payments that are not paid yet
        if (!requirePermission(Hotel.PAY_BOOKING)) {
            return new ArrayList<>();
        }

        ArrayList<Payment> pending = new ArrayList<>();// filter payments to find pending ones
        for (Payment payment : payments) {
            if (!payment.isPaid()) {
                pending.add(payment);
            }
        }
        return pending;
    }

    public boolean payBooking(String bookingCode, int methodChoice) throws InputMismatchException {
        if (!requirePermission(Hotel.PAY_BOOKING)) {
            return false;
        }

        validatePayableBookingCode(bookingCode);

        String normalizedMethod = normalizePaymentMethod(methodChoice);
        if (normalizedMethod == null) {
            throw new InputMismatchException("Invalid payment method choice. Please enter 1 for Cash or 2 for Card.");
        }

        Payment payment = findPaymentByBookingCode(bookingCode);
        if (payment == null) {
            throw new InputMismatchException("Booking code not found: " + bookingCode.trim());
        }

        if (payment.isPaid()) {
            throw new InputMismatchException("Booking " + bookingCode.trim() + " is already paid.");
        }

        payment.setMethod(normalizedMethod);
        payment.markAsPaid();
        return true;
    }

    private String normalizePaymentMethod(int methodChoice) {
        if (methodChoice == 1) {
            return "Cash";
        }
        if (methodChoice == 2) {
            return "Card";
        }
        return null;
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

    
    public boolean loginFlow(Scanner scanner) {
        String username;
        while (true) {
            System.out.print("Username: ");
            username = scanner.nextLine();
            try {
                username = InputHandler.parseRequiredText(username, "Username");
                if (!hasUsername(username)) {
                    System.out.println("Username not found. Please enter correct username.");
                    continue;
                }
                break;
            } catch (InputMismatchException ex) {
                System.out.println(ex.getMessage());
            }
        }

        while (true) {
            String password = InputHandler.readPassword(scanner, "Password: ");
            try {
                password = InputHandler.parseRequiredText(password, "Password");
            } catch (InputMismatchException ex) {
                System.out.println("Password cannot be empty.");
                continue;
            }

            if (login(username, password)) {
                return true;
            }

            System.out.println("Invalid password. Please try again.");
        }
    }

    // Schedule display methods
    public void displayWeeklySchedule() {
        System.out.println("\n======================================");
        System.out.println("      BOOKING SCHEDULE");
        System.out.println("======================================");
        
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
            String dateHeader = date.format(headerFormatter);// e.g., "Mon 01-Jan"
            System.out.print(String.format("| %-10s ", dateHeader));
        }
        System.out.println("|");
        System.out.println("========================================================================================================");

        // Print each room's availability for 7 days
        for (IRoom room : allRooms) {
            System.out.print(String.format("%-12s", room.getRoomNumber()));
            for (int i = 0; i < 7; i++) {
                LocalDate date = today.plusDays(i);
                boolean isBooked = isRoomBookedOnDate(room, date);
                String status = isBooked ? "BOOKED" : "FREE";
                System.out.print(String.format("| %-10s ", status));
            }
            System.out.println("|");
        }
        System.out.println("========================================================================================================");
        System.out.println("Legend: FREE = Available for booking | BOOKED = Room is reserved");
    }

    
    public void bookRoomFlow(Scanner scanner) throws PermissionDeniedException {
        System.out.println("\n======================================");
        System.out.println("      BOOK A ROOM");
        System.out.println("======================================");

        ArrayList<String> availableTypes = new ArrayList<>();
        for (IRoom room : getAllRooms()) {
            String roomType = room.getRoomType();
            if (roomType != null && !roomType.trim().isEmpty() && !availableTypes.contains(roomType)) {
                availableTypes.add(roomType);
            }
        }

        if (availableTypes.isEmpty()) {
            System.out.println("No room types available in the system.");
            return;
        }

        System.out.println("Available room types:");
        for (int i = 0; i < availableTypes.size(); i++) {
            System.out.println((i + 1) + ". " + availableTypes.get(i));
        }

        int roomTypeChoice;
        while (true) {
            roomTypeChoice = InputHandler.readIntChoice(
                scanner,
                "Enter room type that you want to book (1-" + availableTypes.size() + "): "
            );
            if (roomTypeChoice >= 1 && roomTypeChoice <= availableTypes.size()) {
                break;
            }
            System.out.println("Invalid choice. Please enter a number from 1 to " + availableTypes.size() + ".");
        }

        String type = availableTypes.get(roomTypeChoice - 1);
        LocalDate selectedBookingDate;
        ArrayList<IRoom> bookableRooms;

        while (true) {
            try {
                System.out.print("Enter booking date (yyyy-MM-dd): ");
                String inputDate = scanner.nextLine();
                selectedBookingDate = InputHandler.parseDateInput(inputDate);
                if (selectedBookingDate.isBefore(LocalDate.now())) {
                    throw new InputMismatchException("Booking date cannot be in the past.");
                }

                bookableRooms = findBookableRoomsByDate(type, selectedBookingDate);
                if (bookableRooms.isEmpty()) {
                    System.out.println("No available rooms of type '" + type + "' on " + selectedBookingDate + ".");
                    continue;
                }

                System.out.println("Available rooms of type '" + type + "' on " + selectedBookingDate + " are: \n");
                for (IRoom room : bookableRooms) {
                    System.out.println(room);
                }
                break;
            } catch (InputMismatchException ex) {
                System.out.println(ex.getMessage());
            }
        }

        String roomNumber;
        while (true) {
            System.out.print("Enter room number to book: ");
            roomNumber = scanner.nextLine();
            try {
                roomNumber = InputHandler.parseRequiredText(roomNumber, "Room number");
            } catch (InputMismatchException ex) {
                System.out.println(ex.getMessage());
                continue;
            }

            boolean validRoomNumber = false;
            for (IRoom room : bookableRooms) {
                if (room.getRoomNumber().equals(roomNumber.trim())) {
                    validRoomNumber = true;
                    roomNumber = room.getRoomNumber();
                    break;
                }
            }

            if (validRoomNumber) {
                break;
            }

            System.out.println("Invalid room number. Please choose from the listed available rooms.");
        }

        int numberOfNights = InputHandler.readIntChoice(scanner, "Enter number of nights to stay: ");

        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();
        try{
            guestName = InputHandler.parseRequiredText(guestName, "Guest name");
            if (guestName.matches("^-?\\d+$")) {
                throw new InputMismatchException("Guest name cannot be integer.");
            }
        } catch (InputMismatchException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Booking failed.");
            return;
        }

        CheckIn booking;
        try {
            booking = bookRoomByNumber(roomNumber, guestName, selectedBookingDate, numberOfNights);
        } catch (InputMismatchException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Booking failed.");
            return;
        }

        if (booking == null) {
            System.out.println("Booking failed.");
            return;
        }

        System.out.println("\n======================================");
        System.out.println("      BOOKING CONFIRMATION");
        System.out.println("======================================");
        System.out.println(booking);
        String paymentStatus = getPaymentStatusByBookingCode(booking.getBookingCode());
        if ("No payment record".equals(paymentStatus)) {
            paymentStatus = "Pending";
        }
        System.out.println("Payment Status: " + paymentStatus);

        while (true) {
            int payNowChoice = InputHandler.readIntChoice(scanner, "Pay now? (1. Yes, 2. Later): ");
            if (payNowChoice == 1) {
                int methodChoice = promptPaymentMethod(scanner);
                try {
                    if (payBooking(booking.getBookingCode(), methodChoice)) {
                        System.out.println("Payment successful for booking " + booking.getBookingCode() + ".");
                    } else {
                        System.out.println("Payment not completed.");
                    }
                } catch (InputMismatchException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            }

            if (payNowChoice == 2) {
                System.out.println("Payment is pending.");
                break;
            }

            System.out.println("Invalid choice. Please enter 1 or 2.");
        }
    }

    public void paymentFlow(Scanner scanner) throws PermissionDeniedException {
        System.out.println("\n======================================");
        System.out.println("      PAYMENT CENTER");
        System.out.println("======================================");

        ArrayList<Payment> pendingPayments = getPendingPayments();
        if (pendingPayments.isEmpty()) {
            System.out.println("No pending payments.");
            return;
        }

        System.out.println("Pending payments:");
        for (Payment payment : pendingPayments) {
            System.out.println(payment);
        }

        while (true) {
            String bookingCode;
            while (true) {
                System.out.print("Enter booking id to pay: ");
                bookingCode = scanner.nextLine();
                try {
                    bookingCode = InputHandler.parseRequiredText(bookingCode, "Booking code");
                    validatePayableBookingCode(bookingCode);
                    break;
                } catch (InputMismatchException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            int methodChoice = promptPaymentMethod(scanner);

            try {
                if (payBooking(bookingCode, methodChoice)) {
                    System.out.println("Payment successful.");
                } else {
                    System.out.println("Payment failed.");
                }
                break;
            } catch (InputMismatchException ex) {
                System.out.println(ex.getMessage());
                System.out.println("Please try again.");
            }
        }
    }

    private int promptPaymentMethod(Scanner scanner) {
        while (true) {
            int methodChoice = InputHandler.readIntChoice(scanner, "Enter payment method (1. Cash, 2. Card): ");
            if (methodChoice == 1 || methodChoice == 2) {
                return methodChoice;
            }
            System.out.println("Invalid payment method choice. Please enter 1 or 2.");
        }
    }


}
