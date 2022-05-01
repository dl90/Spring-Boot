package local.demo.module.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "auth failed")
public class AuthException extends RuntimeException {

    public AuthException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public AuthException(String exceptionMsg) {
        super(exceptionMsg);
    }
}
