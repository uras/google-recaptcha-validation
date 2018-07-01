package net.urassenol.recaptcha.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecaptchaResponse {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("challenge_ts")
    private String challengeTs;

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("error-codes")
    private String[] errorCodes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getChallengeTs() {
        return challengeTs;
    }

    public void setChallengeTs(String challengeTs) {
        this.challengeTs = challengeTs;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String[] getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(String[] errorCodes) {
        this.errorCodes = errorCodes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RecaptchaResponse{");
        sb.append("success=").append(success);
        sb.append(", challengeTs='").append(challengeTs).append('\'');
        sb.append(", hostname='").append(hostname).append('\'');
        sb.append(", errorCodes=").append(Arrays.toString(errorCodes));
        sb.append('}');
        return sb.toString();
    }
}
