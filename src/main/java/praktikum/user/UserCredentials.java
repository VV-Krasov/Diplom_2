package praktikum.user;

public class UserCredentials {
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

    private String email;
    private String password;

    public UserCredentials(User user)
    {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
