package praktikum;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.user.User;
import praktikum.user.UserClient;
import praktikum.user.UserGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;
@RunWith(Enclosed.class)
public class UserCreateTest {

    private static final UserClient userClient = new UserClient();

    @RunWith(Parameterized.class)
    public static class ParameterizedTests
    {
        private final User user;

        public ParameterizedTests(String name, String password, String email)
        {
            user = new User(name, password, email);
        }

        @Parameterized.Parameters
        public static Object[][] getUserData()
        {
            return new Object[][] {
                    {null,null,null},
                    {"Sergio",null,"mario222_19@yandex.ru"},
                    {"Mario","pass1234qwer#",null},
                    {null,"password881qq","not_mario111@yandex.ru"}
            };
        }

        @Test
        public void createUserWithoutRequiredDataTest()
        {
            userClient.create(user)
                    .assertThat().statusCode(SC_FORBIDDEN)
                    .body("success", is(false)).and()
                    .body("message", is("Email, password and name are required fields"));
        }
    }

    public static class NotParameterizedTests
    {
        User user = UserGenerator.createRandomUser();

        @Test
        public void createUserTest()
        {
            userClient.create(user)
                    .assertThat().statusCode(SC_OK)
                    .body("success", is(true));
        }

        @Test
        public void createUserThatAlreadyExistsTest()
        {
            userClient.create(user)
                    .assertThat().statusCode(SC_OK)
                    .body("success", is(true));

            userClient.create(user)
                    .assertThat().statusCode(SC_FORBIDDEN)
                    .body("success", is(false)).and()
                    .body("message", is("User already exists"));
        }
    }
}