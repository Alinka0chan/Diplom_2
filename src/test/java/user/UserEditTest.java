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

public class UserEditTest {
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
    @DisplayName("Успешное изменение данных пользователя.")
    @Description("Проверка кода состояния и значения поля для /api/auth/user (успешный запрос)")
    public void editNewUserAndCheckResponse() {
        ValidatableResponse editResponse = userClient.updateUser(userAccessToken, UserCredentials.from(new User(user.getEmail() + "ds",user.getPassword() + "ds",user.getName() + "ds")));
        editResponse
                .statusCode(200)
                .body("success", equalTo(true));
        userAccessToken = editResponse.extract().path("accessToken");
    }

    @After
    public void cleanUp() {
        if (userAccessToken != null) {
            userClient.delete(userAccessToken);
        }
    }
}
