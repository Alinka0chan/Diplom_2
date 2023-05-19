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

public class UserLoginTest {
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
    @DisplayName("Успешная авторизация пользователя.")
    @Description("Проверка кода состояния и значения поля для /api/auth/login (успешный запрос)")
    public void loginNewUserAndCheckResponse() {
        ValidatableResponse loginResponse = userClient.login(UserCredentials.from(user));
        loginResponse
                .statusCode(200)
                .assertThat()
                .body( "success", equalTo(true));
    }

    @After
    public void cleanUp() {
        if (userAccessToken != null) {
            userClient.delete(userAccessToken);
        }
    }
}
