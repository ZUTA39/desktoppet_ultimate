package local.desktoppet.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 用户实体类
 * 
 * @author 2313040108
 * @version 2.5
 */

public class User {
    private final StringProperty username;
    private final StringProperty password;
    
    public User() {
        this(null, null);
    }

    public User(String username, String password) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
    
}
