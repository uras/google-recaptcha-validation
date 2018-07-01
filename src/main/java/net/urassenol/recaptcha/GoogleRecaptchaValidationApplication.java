package net.urassenol.recaptcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
public class GoogleRecaptchaValidationApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoogleRecaptchaValidationApplication.class, args);
    }
}
