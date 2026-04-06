package util;

import controller.Hotel;
import hotel.BookingStatus;
import hotel.CheckIn;
import hotel.Guest;
import hotel.Payment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import room.IRoom;
import room.NormalRoom;
import room.RoomStatus;
import room.VIPRoom;
import user.IStaff;
import user.ManagerUser;
import user.ReceptionistUser;

public class TextFileStorage {

	private String folderName = "data";
	private static String ROOMS_FILE = "rooms.txt";
	private static String STAFF_FILE = "staff.txt";
	private static String GUESTS_FILE = "guests.txt";
	private static String BOOKINGS_FILE = "bookings.txt";
	private static String PAYMENTS_FILE = "payments.txt";

	public boolean loadAll(Hotel hotel) {
		boolean loadedRooms = loadRooms(hotel);
		boolean loadedStaff = loadStaff(hotel);
		boolean loadedGuests = loadGuests(hotel);
		boolean loadedBookings = loadBookings(hotel);
		boolean loadedPayments = loadPayments(hotel);

		if (loadedBookings && !loadedPayments) {
			for (CheckIn booking : hotel.getBookingsList()) {
				hotel.createPaymentForBooking(booking);
			}
		}

		return loadedRooms || loadedStaff || loadedGuests || loadedBookings || loadedPayments;
	}

	public void saveAll(Hotel hotel) {
		saveRooms(hotel);
		saveStaff(hotel);
		saveGuests(hotel);
		saveBookings(hotel);
		savePayments(hotel);
	}

	private boolean loadRooms(Hotel hotel) {
		File file = new File(folderName + File.separator + ROOMS_FILE);
		if (!file.exists()) {
			return false;
		}

		boolean loadedAny = false;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty() || line.startsWith("ROOM NUMBER")) {
					continue;
				}

				String[] parts = line.split(",");
				if (parts.length < 4) {
					continue;
				}

				String roomNumber = parts[0].trim();
				String roomType = parts[1].trim();
				double price = parseDouble(parts[2].trim());
				RoomStatus status = parseRoomStatus(parts[3].trim());

				IRoom room;
				if (roomType.equalsIgnoreCase("VIP")) {
				// Subtract service fee (50.0) to get base price since saved price includes it
				double basePrice = Math.max(0, price - 50.0);
				room = new VIPRoom(roomNumber, basePrice);
			} else {
				room = new NormalRoom(roomNumber, price);
			}

			room.setStatus(status);
			hotel.addRoom(room);
			loadedAny = true;
		}
		} catch (IOException ex) {
			System.out.println("Cannot load rooms from text file.");
		}
		return loadedAny;
	}

	private boolean loadBookings(Hotel hotel) {
		File file = new File(folderName + File.separator + BOOKINGS_FILE);
		if (!file.exists()) {
			return false;
		}

		boolean loadedAny = false;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty() || line.startsWith("BOOKING ID")) {
					continue;
				}

				String[] parts = line.split(",");
				if (parts.length < 8) {
					continue;
				}

				String bookingId = parts[0].trim();
				String guestId = parts[1].trim();
				String roomNumber = parts[2].trim();
				String checkInDate = parts[3].trim();
				int nights = parseInt(parts[4].trim());
				String staffId = parts[5].trim();
				double discountPercent = parseDouble(parts[6].trim());
				BookingStatus status = parseBookingStatus(parts[7].trim());

				Guest guest = findGuestById(hotel, guestId);
				IRoom room = findRoomByNumber(hotel, roomNumber);
				IStaff staff = findStaffById(hotel, staffId);

				if (guest == null || room == null || staff == null) {
					continue;
				}

				CheckIn booking = new CheckIn(bookingId, guest, room, checkInDate, nights, staff, discountPercent);
				booking.setStatus(status);
				hotel.addBookingWithoutPayment(booking);
				loadedAny = true;
			}
		} catch (IOException ex) {
			System.out.println("Cannot load bookings from text file.");
		}
		return loadedAny;
	}

	private boolean loadPayments(Hotel hotel) {
		File file = new File(folderName + File.separator + PAYMENTS_FILE);
		if (!file.exists()) {
			return false;
		}

		boolean loadedAny = false;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty() || line.startsWith("PAYMENT ID")) {
					continue;
				}

				String[] parts = line.split(",");
				if (parts.length < 5) {
					continue;
				}

				String paymentId = parts[0].trim();
				String bookingId = parts[1].trim();
				double amount = parseDouble(parts[2].trim());
				String method = parts[3].trim();
				String paidText = parts[4].trim();

				Payment payment = new Payment(paymentId, bookingId, amount, method);
				if ("Paid".equalsIgnoreCase(paidText) || "true".equalsIgnoreCase(paidText)) {
					payment.markAsPaid();
				}

				hotel.addPayment(payment);
				loadedAny = true;
			}
		} catch (IOException ex) {
			System.out.println("Cannot load payments from text file.");
		}
		return loadedAny;
	}

	private boolean loadStaff(Hotel hotel) {
		File file = new File(folderName + File.separator + STAFF_FILE);
		if (!file.exists()) {
			return false;
		}

		boolean loadedAny = false;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty() || line.startsWith("STAFF ID")) {
					continue;
				}

				String[] parts = line.split(",");
				if (parts.length < 7) {
					continue;
				}

				String staffId = parts[0].trim();
				String name = parts[1].trim();
				String role = parts[2].trim();
				char gender = parts[3].trim().isEmpty() ? 'U' : parts[3].trim().charAt(0);
				String phone = parts[4].trim();
				String password = parts.length >= 8 ? parts[5].trim() : "1234";
				double salary = parseDouble(parts.length >= 8 ? parts[6].trim() : parts[5].trim());
				String workHours = parts.length >= 8 ? parts[7].trim() : parts[6].trim();

				if (role.equalsIgnoreCase("Manager")) {
					hotel.addUser(new ManagerUser(staffId, name, gender, phone, password, salary));
				} else {
					hotel.addUser(new ReceptionistUser(staffId, name, gender, phone, password, salary, workHours));
				}

				loadedAny = true;
			}
		} catch (IOException ex) {
			System.out.println("Cannot load staff from text file.");
		}
		return loadedAny;
	}

	private boolean loadGuests(Hotel hotel) {
		File file = new File(folderName + File.separator + GUESTS_FILE);
		if (!file.exists()) {
			return false;
		}

		boolean loadedAny = false;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty() || line.startsWith("GUEST ID")) {
					continue;
				}

				String[] parts = line.split(",");
				if (parts.length < 4) {
					continue;
				}

				String guestId = parts[0].trim();
				String name = parts[1].trim();
				String phone = parts[2].trim();
				String email = parts[3].trim();

				hotel.addGuest(new Guest(guestId, name, phone, email));
				loadedAny = true;
			}
		} catch (IOException ex) {
			System.out.println("Cannot load guests from text file.");
		}
		return loadedAny;
	}

	private void saveRooms(Hotel hotel) {
		try {
			createFolderIfNeeded();
			BufferedWriter writer = new BufferedWriter(new FileWriter(folderName + File.separator + "rooms.txt"));
			writer.write("ROOM NUMBER,ROOM TYPE,PRICE,STATUS");
			writer.newLine();

			for (IRoom room : hotel.getAllRooms()) {
				writer.write(room.getRoomNumber() + "," + room.getRoomType() + "," + room.getPricePerNight() + "," + room.getStatus());
				writer.newLine();
			}

			writer.close();
		} catch (IOException ex) {
			System.out.println("Cannot save rooms to text file.");
		}
	}

	private void saveStaff(Hotel hotel) {
		try {
			createFolderIfNeeded();
			BufferedWriter writer = new BufferedWriter(new FileWriter(folderName + File.separator + "staff.txt"));
			writer.write("STAFF ID,NAME,ROLE,GENDER,PHONE,PASSWORD,SALARY,WORK HOURS");
			writer.newLine();

			for (IStaff staff : hotel.getStaffList()) {
				if (staff instanceof ManagerUser) {
					ManagerUser manager = (ManagerUser) staff;
					writer.write(manager.getId() + "," + manager.getName() + ",Manager," + manager.getGender() + "," + manager.getPhoneNumber() + "," + manager.getPassword() + "," + manager.getSalary() + ",N/A");
				} else if (staff instanceof ReceptionistUser) {
					ReceptionistUser receptionist = (ReceptionistUser) staff;
					writer.write(receptionist.getId() + "," + receptionist.getName() + ",Receptionist," + receptionist.getGender() + "," + receptionist.getPhoneNumber() + "," + receptionist.getPassword() + "," + receptionist.getSalary() + "," + receptionist.getWorkHours());
				}
				writer.newLine();
			}

			writer.close();
		} catch (IOException ex) {
			System.out.println("Cannot save staff to text file.");
		}
	}

	private void saveGuests(Hotel hotel) {
		try {
			createFolderIfNeeded();
			BufferedWriter writer = new BufferedWriter(new FileWriter(folderName + File.separator + "guests.txt"));
			writer.write("GUEST ID,NAME,PHONE,EMAIL");
			writer.newLine();

			for (Guest guest : hotel.getGuestsList()) {
				writer.write(guest.getGuestID() + "," + guest.getGuestName() + "," + guest.getPhoneNumber() + "," + guest.getEmail());
				writer.newLine();
			}

			writer.close();
		} catch (IOException ex) {
			System.out.println("Cannot save guests to text file.");
		}
	}

	private void saveBookings(Hotel hotel) {
		try {
			createFolderIfNeeded();
			BufferedWriter writer = new BufferedWriter(new FileWriter(folderName + File.separator + BOOKINGS_FILE));
			writer.write("BOOKING ID,GUEST ID,ROOM NUMBER,CHECKIN DATE,NIGHTS,STAFF ID,DISCOUNT PERCENT,STATUS");
			writer.newLine();

			for (CheckIn booking : hotel.getBookingsList()) {
				double discountPercent = 0.0;
				if (booking.getOriginalPrice() > 0 && booking.getNight() > 0) {
					double perNightDiscount = booking.getDiscountPrice() / booking.getNight();
					discountPercent = (perNightDiscount / booking.getOriginalPrice()) * 100.0;
				}

				writer.write(
					booking.getBookingCode() + ","
					+ booking.getGuest().getGuestID() + ","
					+ booking.getRoom().getRoomNumber() + ","
					+ booking.getCheckIn() + ","
					+ booking.getNight() + ","
					+ booking.getStaff().getId() + ","
					+ discountPercent + ","
					+ booking.getStatus()
				);
				writer.newLine();
			}

			writer.close();
		} catch (IOException ex) {
			System.out.println("Cannot save bookings to text file.");
		}
	}

	private void savePayments(Hotel hotel) {
		try {
			createFolderIfNeeded();
			BufferedWriter writer = new BufferedWriter(new FileWriter(folderName + File.separator + PAYMENTS_FILE));
			writer.write("PAYMENT ID,BOOKING ID,AMOUNT,METHOD,STATUS");
			writer.newLine();

			for (Payment payment : hotel.getPaymentsList()) {
				writer.write(
					payment.getPaymentId() + ","
					+ payment.getBookingId() + ","
					+ payment.getAmount() + ","
					+ payment.getMethod() + ","
					+ (payment.isPaid() ? "Paid" : "Pending")
				);
				writer.newLine();
			}

			writer.close();
		} catch (IOException ex) {
			System.out.println("Cannot save payments to text file.");
		}
	}

	private void createFolderIfNeeded() {
		File folder = new File(folderName);
		if (!folder.exists()) {
			folder.mkdir();
		}
	}

	private double parseDouble(String text) {
		try {
			return Double.parseDouble(text);
		} catch (NumberFormatException ex) {
			return 0.0;
		}
	}

	private RoomStatus parseRoomStatus(String text) {
		try {
			return RoomStatus.valueOf(text);
		} catch (IllegalArgumentException ex) {
			return RoomStatus.AVAILABLE;
		}
	}

	private BookingStatus parseBookingStatus(String text) {
		try {
			return BookingStatus.valueOf(text);
		} catch (IllegalArgumentException ex) {
			return BookingStatus.RESERVED;
		}
	}

	private int parseInt(String text) {
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException ex) {
			return 1;
		}
	}

	private Guest findGuestById(Hotel hotel, String guestId) {
		for (Guest guest : hotel.getGuestsList()) {
			if (guest.getGuestID().equals(guestId)) {
				return guest;
			}
		}
		return null;
	}

	private IRoom findRoomByNumber(Hotel hotel, String roomNumber) {
		for (IRoom room : hotel.getAllRooms()) {
			if (room.getRoomNumber().equals(roomNumber)) {
				return room;
			}
		}
		return null;
	}

	private IStaff findStaffById(Hotel hotel, String staffId) {
		for (IStaff staff : hotel.getStaffList()) {
			if (staff.getId().equals(staffId)) {
				return staff;
			}
		}
		return null;
	}
}
