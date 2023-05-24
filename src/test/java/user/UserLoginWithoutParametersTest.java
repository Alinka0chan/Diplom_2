package user;

import api.client.UserClient;
import api.model.User;
import api.util.UserCredentials;
import api.util.UserGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class UserLoginWithoutParametersTest {
    private UserClient userClient;
    private User user;
    private String userAccessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandom();
        ValidatableResponse registerResponse = userClient.register(user);
        registerResponse
                .statusCode(200)
                .body("success", equalTo(true));
        userAccessToken = registerResponse.extract().path("accessToken");
    }
    @Test
    @DisplayName("Не успешная авторизация пользователя без логина.")
    @Description("Проверка кода состояния и значения поля для /api/auth/login (запрос без login).")
    public void loginUserWithoutEmailAndCheckResponse() {
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(new User(null,user.getPassword(),null)));
        loginResponse
                .statusCode(401)
                .assertThat()
                .body( "success", equalTo(false));
        assertEquals("email or password are incorrect", loginResponse.extract().path("message"));
    }
    @Test
    @DisplayName("Не успешная авторизация пользователя без пароля.")
    @Description("Проверка кода состояния и значения поля для /api/auth/login (запрос без login).")
    public void loginUserWithoutLoginAndCheckResponse() {
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(new User(user.getEmail(),null,null)));
        loginResponse
                .statusCode(401)
                .assertThat()
                .body( "success", equalTo(false));
        assertEquals("email or password are incorrect", loginResponse.extract().path("message"));
    }
    @Test
    @DisplayName("Не успешная авторизация пользователя с неверной почтой.")
    @Description("Проверка кода состояния и значения поля для /api/auth/login (запрос с неверным email)")
    public void loginUserWithInvalidLoginAndCheckResponse() {
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(new User(user.getEmail() + "ds",user.getPassword(),null)));
        loginResponse
                .statusCode(401)
                .assertThat()
                .body( "success", equalTo(false));
        assertEquals("email or password are incorrect", loginResponse.extract().path("message"));
    }
    @Test
    @DisplayName("Не успешная авторизация пользователя с неверным паролем.")
    @Description("Проверка кода состояния и значения поля для /api/auth/login (запрос с неверным password)")
    public void loginUserWithInvalidEmailAndCheckResponse() {
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(new User(user.getEmail(),user.getPassword() + "ds",null)));
        loginResponse
                .statusCode(401)
                .assertThat()
                .body( "success", equalTo(false));
        assertEquals("email or password are incorrect", loginResponse.extract().path("message"));
    }

    @After
    public void cleanUp() {
        if (userAccessToken != null) {
            userClient.delete(userAccessToken);
        }
    }
}
