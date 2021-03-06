package domain.authentication;

import java.io.Serializable;
/**
 * @author Alex
 * User Login class voor inloggen
 **/
public class UserLogin implements Serializable {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
