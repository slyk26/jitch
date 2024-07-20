package at.slyk.common;

import at.slyk.server.AuthorizationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class User extends AuthorizationResponse {

    private String loginName;

    public User(String loginName, String token) {
        super();
        this.setAccessToken(token);
        this.loginName = loginName;
    }
}
