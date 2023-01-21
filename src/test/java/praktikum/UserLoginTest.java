package praktikum;
import org.junit.Test;
import praktikum.user.User;
import praktikum.user.UserClient;
import praktikum.user.UserCredentials;
import praktikum.user.UserGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class UserLoginTest {
    private final User user = UserGenerator.createRandomUser();
    private static final UserClient userClient = new UserClient();
    @Test
    public void loginWithCorrectUserData()
    {
        String refreshToken = userClient.create(user)
                .assertThat()
                .body("success", is(true)).extract().path("refreshToken");
        userClient.logout(refreshToken);

        userClient.login(new UserCredentials(user))
                .assertThat().statusCode(SC_OK)
                .body("success", is(true));
    }

    @Test
    public void loginWithIncorrectUserData()
    {
        String refreshToken = userClient.create(user)
                .assertThat()
                .body("success", is(true)).extract().path("refreshToken");

        userClient.logout(refreshToken);

        user.setPassword("WrongPassword");
        userClient.login(new UserCredentials(user))
                .assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", is(false));
    }
}