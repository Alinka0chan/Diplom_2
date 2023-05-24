package api.client;

import api.model.User;
import api.util.RestClient;
import api.util.UserCredentials;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends RestClient {
    private static final String REGISTER_USER_PATH = "/auth/register";
    private static final String LOGIN_USER_PATH = "/auth/login";
    private static final String UPDATE_AND_DELETE_USER_PATH = "/auth/user";

    @Step("Регистрация пользователя.")
    public ValidatableResponse register(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(REGISTER_USER_PATH)
                .then();
    }

    @Step("Авторизация пользователя.")
    public ValidatableResponse login(UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(LOGIN_USER_PATH)
                .then();
    }

    @Step("Изменение пользователя.")
    public ValidatableResponse updateUser(String accessToken, UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(userCredentials)
                .when()
                .patch(UPDATE_AND_DELETE_USER_PATH)
                .then();
    }

    @Step("Удаление пользователя.")
    public ValidatableResponse delete(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(UPDATE_AND_DELETE_USER_PATH)
                .then();
    }
}
