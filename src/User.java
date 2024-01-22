import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private String username;
    private String password;
    private boolean madePurchase;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.madePurchase = false;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean hasMadePurchase() {
        return madePurchase;
    }

    public void setMadePurchase(boolean madePurchase) {
        this.madePurchase = madePurchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}