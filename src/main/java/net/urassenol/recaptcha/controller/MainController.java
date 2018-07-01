package net.urassenol.recaptcha.controller;

import net.urassenol.recaptcha.aspect.RecaptchaValidation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/recaptcha")
public class MainController {

    @GetMapping(value = "validation")
    @RecaptchaValidation
    public String validate() {
        return "Validation is success";
    }

}
