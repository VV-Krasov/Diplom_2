package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.user.User;
import praktikum.user.UserClient;
import praktikum.user.UserCredentials;
import praktikum.user.UserGenerator;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class UserChangeDataTest {
    private static final UserClient userClient = new UserClient();
    private final User userNewData;
    private final User user = UserGenerator.createRandomUser();
    private final UserCredentials userCredentials = new UserCredentials(user);

    public UserChangeDataTest(String name, String password, String email)
    {
        userNewData = new User(name, password, email);
    }

    @Parameterized.Parameters
    public static Object[][] getNewUserData()
    {
        User userToEdit = UserGenerator.createRandomUser();

        return new Object[][]{
                {userToEdit.getName(), userToEdit.getPassword(), userToEdit.getEmail()},
                {userToEdit.getName(), null, null},
                {null,userToEdit.getPassword(), null},
                {null, null, "forSureNotExistingEmail" + userToEdit.getEmail()},
                {null, null, null}
        };
    }

    @Test
    public void userChangeDataWithAuthorizationTest()
    {
        String token = userClient.create(user)
                .assertThat()
                .body("success", is(true)).extract().path("accessToken");

        userClient.changeData(token, userNewData)
                .assertThat().statusCode(SC_OK);
    }

    @Test
    public void userChangeDataWithoutAuthorizationTest()
    {
        userClient.create(user)
                .assertThat()
                .body("success", is(true));

        userClient.login(userCredentials)
                .assertThat().statusCode(SC_OK)
                .body("success", is(true));

        userClient.changeData(userNewData)
                .assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", is(false)).and()
                .body("message", is("You should be authorised")).and()
                .body(not(contains(userNewData)));
    }
}