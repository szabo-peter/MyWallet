package hu.flowacademy.MyWallet.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public List<String> handleValidationException(
            ValidationException validationException
    ) {
        log.debug("ValidationException happened: ", validationException);
        return List.of(validationException.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MissingIDException.class)
    public List<String> handleMissingIDException(
            MissingIDException missingIDException
    ) {
        log.debug("MissingIDException happened: ", missingIDException);
        return List.of(missingIDException.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public List<String> handleEverything(
            Throwable throwable) {
        log.debug("Something went wrong : ", throwable);
        return List.of(throwable.getMessage());
    }
}
