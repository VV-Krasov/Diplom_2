package praktikum.user;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public static User createRandomUser()
    {
        return new User(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(7) + "@yandex.ru");
    }
}
