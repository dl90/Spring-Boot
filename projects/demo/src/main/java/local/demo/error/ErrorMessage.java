package local.demo.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorMessage {

    private Integer httpStatusCode;
    private HttpStatus httpStatus;
    private String errorMessage;
    private Instant timestamp;
}
