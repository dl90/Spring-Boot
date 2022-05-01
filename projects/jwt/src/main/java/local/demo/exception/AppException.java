package local.demo.exception;


public class AppException extends RuntimeException {

    public AppException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public AppException(String exceptionMsg) {
        super(exceptionMsg);
    }
}
