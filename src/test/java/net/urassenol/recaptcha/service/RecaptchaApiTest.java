package net.urassenol.recaptcha.service;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import net.urassenol.recaptcha.model.RecaptchaResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecaptchaApiTest {

    @Autowired
    private RecaptchaApi recaptchaApi;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

    @Test
    public void it_should_get_captcha_response() {
        //given
        RecaptchaResponse expectedResponse = new RecaptchaResponse();
        expectedResponse.setSuccess(false);
        expectedResponse.setErrorCodes(new String[]{"invalid-input-response", "invalid-input-secret"});

        wireMockRule.stubFor(
                get(urlPathEqualTo("/google-recaptca-url/path"))
                        .willReturn(aResponse()
                                .withStatus(200))
        );

        //when
        String response = "response";
        String ip = "ip";
        RecaptchaResponse actualResponse = recaptchaApi.verify(response, ip);

        //then
        assertThat(actualResponse.isSuccess()).isFalse();
        assertThat(actualResponse.getErrorCodes())
                .containsExactlyInAnyOrder("invalid-input-response", "invalid-input-secret");
    }
}