package local.demo.error;

import local.demo.controller.DepartmentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice(basePackageClasses = {DepartmentController.class})
@ResponseStatus
public class DepartmentExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(DepartmentExceptionHandler.class);

    @ExceptionHandler(DepartmentNotFound.class)
    public ResponseEntity<ErrorMessage> departmentNotFoundException(DepartmentNotFound ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .httpStatusCode(HttpStatus.NOT_FOUND.value())
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorMessage(ex.getMessage())
                .timestamp(Instant.now())
                .build();
        logger.warn("%s %s".formatted(request.getSessionId(), errorMessage));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}
