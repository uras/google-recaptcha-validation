package net.urassenol.recaptcha.aspect;

import net.urassenol.recaptcha.exception.RecaptchaValidationException;
import net.urassenol.recaptcha.model.RecaptchaResponse;
import net.urassenol.recaptcha.service.RecaptchaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecaptchaValidationAspectTest {

    @InjectMocks
    private RecaptchaValidationAspect recaptchaValidationAspect;

    @Mock
    private RecaptchaService recaptchaService;

    @Test
    public void it_should_not_throw_exception_when_verification_is_success() {
        //given
        RecaptchaResponse response = new RecaptchaResponse();
        response.setSuccess(true);

        when(recaptchaService.getRecaptchaResponse()).thenReturn(response);
        //when
        recaptchaValidationAspect.validate();

        //then
    }

    @Test
    public void it_should_throw_exception_when_verification_is_not_success() {
        //given
        RecaptchaResponse response = new RecaptchaResponse();
        response.setSuccess(false);
        response.setErrorCodes(new String[]{"error-keys"});

        when(recaptchaService.getRecaptchaResponse()).thenReturn(response);
        //when
        Throwable thrown = catchThrowable(() -> recaptchaValidationAspect.validate());

        //then
        assertThat(thrown).isInstanceOf(RecaptchaValidationException.class);
    }
}