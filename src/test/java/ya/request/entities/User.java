package ya.request.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String email;
    private String name;
    private String password;

    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("refreshToken")
    private String refreshToken;

    public User() {}

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String name, String password,
                String accessToken, String refreshToken) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}