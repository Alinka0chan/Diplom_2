package api.client;

import api.model.Orders;
import api.util.RestClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersClient extends RestClient {
    private static final String CREATE_ORDERS_PATH = "/orders";

    @Step("Создание заказа.")
    public ValidatableResponse createOrders(String accessToken, Orders orders) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(orders)
                .when()
                .post(CREATE_ORDERS_PATH)
                .then();
    }

    @Step("Получение заказа определенного пользователя.")
    public ValidatableResponse getUserOrders(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .get(CREATE_ORDERS_PATH)
                .then();
    }

    @Step("Создание заказа без авторизации.")
    public ValidatableResponse createOrderWithoutAccessToken(Orders orders) {
        return given()
                .spec(getBaseSpec())
                .body(orders)
                .when()
                .post(CREATE_ORDERS_PATH)
                .then();
    }

    @Step("Создание заказа без без ингредиентов.")
    public ValidatableResponse createOrderWithoutIngredients(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .post(CREATE_ORDERS_PATH)
                .then();
    }
}
