package at.slyk.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthorizationResponse {
    @JsonProperty("access_token")
    private String accessToken;
    private String scope;
    private String state;
    @JsonProperty("token_type")
    private String tokenType;
}
