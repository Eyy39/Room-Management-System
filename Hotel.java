import java.util.Arrays;

public class Hotel {
    private Room[] rooms;
    private int countRooms;

    public Hotel(int maxRooms) {
        rooms = new Room[maxRooms]; // Initialize the array with maximum capacity
        countRooms = 0;

        // Initialize default rooms
        addRoom(new Room("A101", "Single", 100.0));
        addRoom(new Room("A102", "Double", 150.0));
        addRoom(new Room("B201", "Single", 100.0));
        addRoom(new Room("B202", "Single", 100.0));
        addRoom(new Room("C301", "Suite", 250.0));
        addRoom(new Room("C302", "Double", 150.0));
        addRoom(new Room("D401", "Suite", 250.0));
        addRoom(new Room("D402", "Double", 150.0));
        addRoom(new Room("E501", "Single", 100.0));
        addRoom(new Room("E502", "Suite", 250.0));
    }
    public void addRoom(Room room) {
        if (countRooms < rooms.length) {
            rooms[countRooms++] = room;
        } else {
            System.out.println("Cannot add more rooms. Maximum capacity reached.");
        }
    }
    public void findRoomsByType(String type){
        boolean found = false;
        if (type == null) {
            System.out.println("Room type cannot be null.");
            return;
        }

        for (int i = 0; i < countRooms; i++) {
            if (rooms[i].getRoomType().equals(type)) {
                System.out.println(rooms[i].toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms available for this type.");
        }
    }
    public void displayAllRooms(){
        for (int i = 0; i < countRooms; i++) {
            System.out.println(rooms[i].toString());
        }
    }

    public Room getRoom(int index) {
        if (index < 0 || index >= countRooms) {
            return null;
        }
        return rooms[index];
    }

    public int getRoomCount() {
        return countRooms;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass()) 
            return false;
        Hotel other = (Hotel) obj;
        if (!Arrays.equals(rooms, other.rooms))
            return false;
        return true;
    }
    
}
