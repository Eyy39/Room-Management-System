package exception;

public class InputMismatchException extends Exception {
    public InputMismatchException(String message) {
        super(message);
    }

    public InputMismatchException(){
        super("Input should be integer. Please try again!");
    }

}
