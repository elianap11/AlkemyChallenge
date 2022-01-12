package JavaChallenge.demo.Security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String user;
    private String psw;
    private String token;

    public User() {
    }

    public User(String psw, String token) {
        this.user = user;
        this.psw = psw;
        this.token = token;
    }
}
