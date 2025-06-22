package ya.responseEntities;

import lombok.Getter;
import lombok.Setter;
import ya.requestEntities.User;

@Getter
@Setter
public class UserResponse {

    private String success;

    private User user;

    private String accessToken;

    private String refreshToken;

    private String message;

    public UserResponse() {
    }

    public UserResponse(String success, User user, String accessToken, String refreshToken) {
        this.success = success;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    public UserResponse(String success, String message) {
        this.success = success;
        this.message = message;
    }
}