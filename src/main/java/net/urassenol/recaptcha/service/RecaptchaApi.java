package net.urassenol.recaptcha.service;

import net.urassenol.recaptcha.model.RecaptchaResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(value = "recaptcha-api", url = "${recaptcha.verification.url}")
public interface RecaptchaApi {

    @RequestMapping(
            method = GET,
            value = "/siteverify?secret=${recaptcha.verification.secret}&response={response}&remoteip={ip}"
    )
    RecaptchaResponse verify(@PathVariable("response") String response, @PathVariable("ip") String ip);
}
