package api.util;

import api.model.User;
import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public static User getRandom() {
        final String email = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(5);
        return new User(email, password, name);
    }

    public static User getRandomWithoutEmail() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(5);
        return new User(null, password, name);
    }

    public static User getRandomWithoutPassword() {
        final String email = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        final String name = RandomStringUtils.randomAlphabetic(5);
        return new User(email, null, name);
    }

    public static User getRandomWithoutName() {
        final String email = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, null);
    }
}
