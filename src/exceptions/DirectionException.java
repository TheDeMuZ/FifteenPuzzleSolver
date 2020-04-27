package exceptions;

public class DirectionException extends Exception {
    public DirectionException(char value) {
        super("Unknown direction type: " + value);
    }
}
