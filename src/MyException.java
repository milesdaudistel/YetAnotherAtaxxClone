/**
 * Created by miles on 5/30/17.
 */
public class MyException extends RuntimeException {
    MyException(String message) {
        super(message);
    }

    public static MyException error(String message) {
        return new MyException(message);
    }
}
