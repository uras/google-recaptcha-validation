package net.urassenol.recaptcha.aspect;

import net.urassenol.recaptcha.exception.RecaptchaValidationException;
import net.urassenol.recaptcha.model.RecaptchaResponse;
import net.urassenol.recaptcha.service.RecaptchaService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class RecaptchaValidationAspect {

    public static final String DELIMITER = ",";
    public static final String PREFIX = "Recaptcha could not be verified due to following errors [";
    public static final String SUFFIX = "]";
    private final RecaptchaService recaptchaService;

    @Autowired
    public RecaptchaValidationAspect(RecaptchaService recaptchaService) {
        this.recaptchaService = recaptchaService;
    }

    @Before("@annotation(RecaptchaValidation)")
    public void validate() {
        RecaptchaResponse recaptchaResponse = recaptchaService.getRecaptchaResponse();

        if (!recaptchaResponse.isSuccess()) {
            String errorMessage = Arrays.stream(recaptchaResponse.getErrorCodes()).collect(
                    Collectors.joining(DELIMITER, PREFIX, SUFFIX)
            );
            throw new RecaptchaValidationException(errorMessage);
        }
    }
}