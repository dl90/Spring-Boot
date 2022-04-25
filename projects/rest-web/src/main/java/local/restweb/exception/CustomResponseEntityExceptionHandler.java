package local.restweb.exception;

import java.util.Date;

import local.restweb.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

        ExceptionResponseStructure exceptionResponseStructure = new ExceptionResponseStructure(
                new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponseStructure, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> userNotFoundExceptions(UserNotFoundException ex, WebRequest request) {

        ExceptionResponseStructure exceptionResponseStructure = new ExceptionResponseStructure(
                new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponseStructure, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();
        String fieldErrors = bindingResult.getFieldErrors().stream()
                .map(fieldError -> String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .toList().toString();

        ExceptionResponseStructure exceptionResponseStructure = new ExceptionResponseStructure(
                new Date(), "Validation failed", fieldErrors);

        return new ResponseEntity<>(exceptionResponseStructure, HttpStatus.BAD_REQUEST);
    }
}
