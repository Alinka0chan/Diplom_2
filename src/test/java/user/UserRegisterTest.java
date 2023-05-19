package user;

import api.client.UserClient;
import api.model.User;
import api.util.UserGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserRegisterTest {
    private UserClient userClient;
    private User user;
    private String userAccessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandom();
    }

    @Test
    @DisplayName("Успешное создание пользователя.")
    @Description("Проверка кода состояния и значения поля для /auth/register (успешный запрос)")
    public void registerNewUserAndCheckResponse() {
        ValidatableResponse registerResponse = userClient.register(user);
        registerResponse
                .statusCode(200)
                .body("success", equalTo(true));
        userAccessToken = registerResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Не успешное создание пользователя.")
    @Description("Проверка кода состояния и значения поля для /auth/register (не успешный запрос с повторением данных)")
    public void registerExistUserAndCheckResponse() {
        userClient.register(user);
        ValidatableResponse registerExistResponse = userClient.register(user);
        registerExistResponse
                .statusCode(403)
                .assertThat()
                .body("success", equalTo(false));
    }

    @After
    public void cleanUp() {
        if (userAccessToken != null) {
            userClient.delete(userAccessToken);
        }
    }
}
