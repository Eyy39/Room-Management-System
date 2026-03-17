package room;

// A functional interface is an interface with EXACTLY ONE abstract method.
// The @FunctionalInterface annotation tells the compiler to enforce this rule.
//
// Because it has only one method, Java lets you use a LAMBDA EXPRESSION
// as a short-hand instead of writing a full anonymous inner class.
// This is the foundation of modern Java functional programming.
@FunctionalInterface
public interface RoomFilter {
    // The single abstract method - decides whether a room "passes" the filter.
    boolean test(IRoom room);
}
