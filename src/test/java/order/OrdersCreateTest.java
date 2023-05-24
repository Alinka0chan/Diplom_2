package order;

import api.client.OrdersClient;
import api.client.UserClient;
import api.model.Orders;
import api.model.User;
import api.util.OrdersGenerator;
import api.util.UserGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class OrdersCreateTest {
    private Orders orders;
    private OrdersClient ordersClient;
    private UserClient userClient;
    private User user;
    private String userAccessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandom();
        ValidatableResponse registerResponse = userClient.register(user);
        userAccessToken = registerResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Успешное создание заказа.")
    @Description("Проверка кода состояния и значения поля для /orders (успешный запрос)")
    public void createOrdersAndCheckResponse() {
        ordersClient = new OrdersClient();
        orders = OrdersGenerator.getOrders();
        ValidatableResponse createOrdersResponse = ordersClient.createOrders(userAccessToken, orders);
        createOrdersResponse
                .statusCode(200)
                .body("success", equalTo(true));

    }

    @After
    public void cleanUp() {
        if (userAccessToken != null) {
            userClient.delete(userAccessToken);
        }
    }
}
