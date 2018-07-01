package net.urassenol.recaptcha.service;

import net.urassenol.recaptcha.exception.RecaptchaValidationException;
import net.urassenol.recaptcha.model.RecaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class RecaptchaService {

    private static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");
    private static final String HTTP_X_FORWARDED_FOR = "X-FORWARDED-FOR";
    private static final String G_RECAPTCHA_RESPONSE = "X-G-ReCaptcha-Response";
    private static final String INVALID_CAPTCHA_RESPONSE = "Invalid captcha response.";

    private final HttpServletRequest request;
    private final RecaptchaApi recaptchaApi;

    @Autowired
    public RecaptchaService(HttpServletRequest request,
                            RecaptchaApi recaptchaApi) {
        this.request = request;
        this.recaptchaApi = recaptchaApi;
    }

    public RecaptchaResponse getRecaptchaResponse() {
        String captchaResponse = getClientRecaptchaResponse();
        if (StringUtils.isEmpty(captchaResponse) || !RESPONSE_PATTERN.matcher(captchaResponse).matches()) {
            throw new RecaptchaValidationException(INVALID_CAPTCHA_RESPONSE);
        }

        return recaptchaApi.verify(captchaResponse, getUserIp());
    }

    private String getUserIp() {
        return Optional.ofNullable(request.getHeader(HTTP_X_FORWARDED_FOR))
                .orElse(request.getRemoteAddr());
    }

    private String getClientRecaptchaResponse() {
        return request.getHeader(G_RECAPTCHA_RESPONSE);
    }
}

