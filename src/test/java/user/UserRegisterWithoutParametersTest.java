package user;

import api.client.UserClient;
import api.model.User;
import api.util.UserGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class UserRegisterWithoutParametersTest {
    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Не успешное создание курьера.")
    @Description("Проверка кода состояния и значения поля для /auth/register (запрос без email)")
    public void registerWithoutEmailUserAndCheckResponse() {
        user = UserGenerator.getRandomWithoutEmail();
        ValidatableResponse registerResponse = userClient.register(user);
        registerResponse
                .statusCode(403)
                .body("success", equalTo(false));
        assertEquals("Email, password and name are required fields", registerResponse.extract().path("message"));
    }

    @Test
    @DisplayName("Не успешное создание курьера.")
    @Description("Проверка кода состояния и значения поля для /auth/register (запрос без password)")
    public void registerWithoutPasswordUserAndCheckResponse() {
        user = UserGenerator.getRandomWithoutPassword();
        ValidatableResponse registerResponse = userClient.register(user);
        registerResponse
                .statusCode(403)
                .body("success", equalTo(false));
        assertEquals("Email, password and name are required fields", registerResponse.extract().path("message"));
    }

    @Test
    @DisplayName("Не успешное создание курьера.")
    @Description("Проверка кода состояния и значения поля для /auth/register (запрос без name)")
    public void registerWithoutNameUserAndCheckResponse() {
        user = UserGenerator.getRandomWithoutName();
        ValidatableResponse registerResponse = userClient.register(user);
        registerResponse
                .statusCode(403)
                .body("success", equalTo(false));
        assertEquals("Email, password and name are required fields", registerResponse.extract().path("message"));
    }
}
