package ya.requestEntities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String email;

    private String password;

    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    public User(String email, String password) {
        this(email, password, null);
    }
}