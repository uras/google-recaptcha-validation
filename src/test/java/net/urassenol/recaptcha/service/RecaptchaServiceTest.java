package net.urassenol.recaptcha.service;

import net.urassenol.recaptcha.exception.RecaptchaValidationException;
import net.urassenol.recaptcha.model.RecaptchaResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RecaptchaServiceTest {

    @InjectMocks
    private RecaptchaService recaptchaService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private RecaptchaApi recaptchaApi;

    @Test
    public void it_should_return_true_when_verification_is_success() {
        //given
        RecaptchaResponse response = new RecaptchaResponse();
        response.setSuccess(true);
        String clientIp = "ip";
        String clientRecaptchaResponse = "client-response";

        when(request.getHeader("X-FORWARDED-FOR")).thenReturn(clientIp);
        when(request.getHeader("X-G-ReCaptcha-Response")).thenReturn(clientRecaptchaResponse);
        when(recaptchaApi.verify(clientRecaptchaResponse, clientIp)).thenReturn(response);
        //when
        RecaptchaResponse recaptchaResponse = recaptchaService.getRecaptchaResponse();

        //then
        assertThat(recaptchaResponse.isSuccess()).isTrue();
    }

    @Test
    public void it_should_return_false_when_verification_is_not_success() {
        //given
        RecaptchaResponse response = new RecaptchaResponse();
        response.setSuccess(false);
        String clientIp = "ip";
        String clientRecaptchaResponse = "client-response";

        when(request.getHeader("X-FORWARDED-FOR")).thenReturn(clientIp);
        when(request.getHeader("X-G-ReCaptcha-Response")).thenReturn(clientRecaptchaResponse);
        when(recaptchaApi.verify(clientRecaptchaResponse, clientIp)).thenReturn(response);
        //when
        RecaptchaResponse recaptchaResponse = recaptchaService.getRecaptchaResponse();

        //then
        assertThat(recaptchaResponse.isSuccess()).isFalse();
    }

    @Test
    public void it_should_throw_recaptcha_validation_exception_when_client_captcha_response_is_invalid() {
        //given
        String clientIp = "ip";
        String clientRecaptchaResponse = "aa    asf   435   ";
        when(request.getHeader("X-FORWARDED-FOR")).thenReturn(clientIp);
        when(request.getHeader("X-G-ReCaptcha-Response")).thenReturn(clientRecaptchaResponse);
        //when
        Throwable thrown = catchThrowable(() -> recaptchaService.getRecaptchaResponse());

        //then
        assertThat(thrown).isInstanceOf(RecaptchaValidationException.class);
    }
}