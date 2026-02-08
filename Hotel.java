import java.util.Arrays;

public class Hotel {
    Room[] rooms;
    int countRooms;

    Hotel(int maxRooms) {
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
    void addRoom(Room room) {
        if (countRooms < rooms.length) {
            rooms[countRooms++] = room;
        } else {
            System.out.println("Cannot add more rooms. Maximum capacity reached.");
        }
    }
    void findRoomsByType(Room[] rooms, int roomCount, String type){
        boolean found = false;

        for (int i = 0; i < roomCount; i++) {
            if (rooms[i].roomType.equals(type)) {
                System.out.println(rooms[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms available for this type.");
        }
    }
    void displayAllRooms(){
        for(int i = 0; i < countRooms; i++){
            System.out.println(rooms[i].toString());
        }
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
