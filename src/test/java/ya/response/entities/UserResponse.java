package ya.response.entities;

import lombok.Getter;
import lombok.Setter;
import ya.request.entities.User;

@Getter
@Setter
public class UserResponse {
    private boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;
    private String message;

    public UserResponse() {}

    public UserResponse(boolean success, User user,
                        String accessToken, String refreshToken) {
        this.success = success;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public UserResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}