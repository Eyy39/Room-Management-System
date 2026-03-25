# Java Lessons Applied in This Project

This report lists the Java lessons/concepts used in the Room Management System, with concrete evidence from the codebase.

## 1) Package and import usage
- `package` declaration: `common/BaseEntity.java:1`
- `import` statements: `controller/Hotel.java:3`

## 2) Class declaration
- Concrete class: `controller/Hotel.java:18`
- Concrete class with `main`: `controller/Main.java:17`

## 3) Abstract class
- Abstract base class: `common/BaseEntity.java:3`
- Abstract domain classes: `room/Room.java:6`, `user/Staff.java:6`

## 4) Interface
- Standard interface: `room/IRoom.java:3`
- Standard interface: `user/IStaff.java:2`

## 5) Functional interface
- Annotation and single-method interface: `room/RoomFilter.java:9`
- Another functional interface: `user/StaffAction.java:3`

## 6) Enum
- Booking status enum: `hotel/BookingStatus.java:3`
- Room status enum: `room/RoomStatus.java:3`

## 7) Inheritance (`extends`)
- `CheckIn` extends `BaseEntity`: `hotel/CheckIn.java:10`
- `NormalRoom` extends `Room`: `room/NormalRoom.java:4`
- `VIPRoom` extends `Room`: `room/VIPRoom.java:5`
- `ManagerUser` extends `Staff`: `user/ManagerUser.java:3`

## 8) Interface implementation (`implements`)
- `Room` implements `IRoom`: `room/Room.java:6`
- `Staff` implements `IStaff`: `user/Staff.java:6`

## 9) Encapsulation (access modifiers)
- `private` fields: `hotel/Guest.java:7`
- `protected` constructor: `common/BaseEntity.java:10`
- `public` getter/setter API: `controller/Hotel.java:51`

## 10) Constructor and constructor overloading
- Overloaded constructors in `BaseEntity`: `common/BaseEntity.java:10`, `common/BaseEntity.java:14`
- Overloaded constructors in `Staff`: `user/Staff.java:11`, `user/Staff.java:19`
- Overloaded constructors in `VIPRoom`: `room/VIPRoom.java:10`, `room/VIPRoom.java:14`

## 11) Method overriding (`@Override`)
- Overridden room type method: `room/NormalRoom.java:9`
- Overridden pricing method: `room/VIPRoom.java:29`
- Overridden permission method: `user/ManagerUser.java:16`

## 12) Polymorphism
- Polymorphic contract through interface list: `controller/Hotel.java:22` (`ArrayList<IRoom>`)
- Interface-typed reference holding subclass objects: `controller/Main.java:22`, `controller/Main.java:23`

## 13) Abstraction
- Abstract method in abstract class: `user/Staff.java:82`
- Abstract behavior defined by interface: `room/IRoom.java:4`

## 14) Default method in interface
- `default boolean matchesType(...)`: `room/IRoom.java:13`

## 15) Lambda expression
- Lambda assigned to functional interface: `controller/Main.java:250`

## 16) Static members and constants
- Static counters: `common/BaseEntity.java:4`
- Static final constants: `controller/Hotel.java:41`
- Static DB connection field: `controller/MySQLConnection.java:12`

## 17) Synchronized method
- Thread-safe ID generation: `common/BaseEntity.java:36`

## 18) Collections and generics
- Generic `ArrayList` fields: `controller/Hotel.java:22`, `controller/Hotel.java:23`
- Generic local list: `controller/Main.java:146`

## 19) Control flow: `if` / `else if` / `else`
- Validation chain with `if` and `else if`: `controller/Hotel.java:441`
- Conditional branching in ID generation: `common/BaseEntity.java:38`

## 20) Control flow: `switch`
- Login menu switch: `controller/Main.java:66`
- Main menu switch: `controller/Main.java:120`

## 21) Loops: `for` and `while`
- Enhanced for-loop: `controller/Hotel.java:98`
- Index-based for-loop: `controller/Hotel.java:254`
- While loop for app lifecycle: `controller/Main.java:57`
- Input validation loop: `util/InputHandler.java:14`

## 22) Exception handling: `try/catch/finally`
- Try-with-resources scanner: `controller/Main.java:53`
- Multiple catch blocks: `controller/Main.java:292`, `controller/Main.java:294`
- Finally block: `controller/Main.java:297`
- DB exception handling: `controller/MySQLConnection.java:33`, `controller/MySQLConnection.java:36`

## 23) Custom exceptions
- Custom exception class: `exception/InputMismatchException.java:3`
- Custom exception class: `exception/PermissionDeniedException.java:3`
- Throw custom exception: `controller/Hotel.java:442`

## 24) `throws` in method signatures
- `throws PermissionDeniedException`: `controller/Hotel.java:142`
- `throws InputMismatchException`: `controller/Hotel.java:382`
- `throws InputMismatchException`: `util/InputHandler.java:31`

## 25) String validation and regex
- Regex for username numeric check: `controller/Main.java:72`
- Regex for guest-name integer check: `controller/Hotel.java:445`
- Email regex matching: `hotel/Guest.java:54`
- Replace non-digits: `common/BaseEntity.java:29`

## 26) Ternary operator
- Payment status text: `hotel/Payment.java:80`
- Password fallback: `user/Staff.java:78`
- Booking status text in schedule: `controller/Hotel.java:513`

## 27) `instanceof` and casting
- Type checking: `room/Room.java:85`
- Downcasting after check: `room/Room.java:87`
- Additional `instanceof` usage: `room/VIPRoom.java:38`, `hotel/Guest.java:68`

## 28) `equals`, `hashCode`, `toString`
- `equals` and `hashCode`: `room/Room.java:83`, `room/Room.java:93`
- `toString`: `room/Room.java:73`, `hotel/Guest.java:57`, `hotel/Payment.java:75`

## 29) Java Time API (`LocalDate`, formatter, parse)
- Imports and formatter usage: `controller/Hotel.java:8`, `controller/Hotel.java:489`
- Current date and date arithmetic: `controller/Hotel.java:487`, `controller/Hotel.java:499`
- Date parsing: `controller/Hotel.java:220`, `util/InputHandler.java:45`

## 30) JDBC (database connectivity)
- JDBC imports: `controller/MySQLConnection.java:3`, `controller/MySQLConnection.java:8`
- Connection creation with `DriverManager`: `controller/MySQLConnection.java:21`
- SQL query execution: `controller/MySQLConnection.java:35`
- SQL update execution: `controller/MySQLConnection.java:47`
- ResultSet iteration: `controller/MySQLConnection.java:89`

## 31) Environment variables
- DB URL from env var: `controller/MySQLConnection.java:13`
- DB username/password from env var: `controller/MySQLConnection.java:14`, `controller/MySQLConnection.java:15`

## 32) Utility class pattern
- Private constructor to prevent instantiation: `util/InputHandler.java:9`
- Static utility methods: `util/InputHandler.java:13`

## 33) Varargs parameter
- `main(String... args)`: `controller/Main.java:18`

## 34) Method return types and API design
- Primitive/object return types: `room/IRoom.java:6`, `controller/Hotel.java:263`
- Defensive copy return (`new ArrayList<>(...)`): `controller/Hotel.java:264`, `controller/Hotel.java:314`

## Summary
The project applies core Java lessons from OOP (abstraction, encapsulation, inheritance, polymorphism), interfaces and enums, exceptions, collections, control flow, date/time API, lambda/functional interfaces, and JDBC integration.