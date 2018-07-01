package net.urassenol.recaptcha.exception;

import net.urassenol.recaptcha.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RecaptchaValidationException.class)
    public ResponseEntity<ErrorResponse> handleRecaptchaError(RecaptchaValidationException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setException("RecaptchaValidationException");
        errorResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

}
