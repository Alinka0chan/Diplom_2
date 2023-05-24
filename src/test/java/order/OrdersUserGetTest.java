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
import static org.junit.Assert.assertEquals;

public class OrdersUserGetTest {
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
        ordersClient = new OrdersClient();
        orders = OrdersGenerator.getOrders();
        ordersClient.createOrders(userAccessToken, orders);
    }

    @Test
    @DisplayName("Успешное получение заказов конкретного пользователя.")
    @Description("Проверка кода состояния и значения поля для /api/orders (успешный запрос)")
    public void getOrdersUserAndCheckResponse() {
        ValidatableResponse ordersClientResponse = ordersClient.getUserOrders(userAccessToken);
        ordersClientResponse
                .statusCode(200)
                .body("success", equalTo(true));

    }

    @Test
    @DisplayName("Не успешное получение заказов конкретного пользователя без аторизации.")
    @Description("Проверка кода состояния и значения поля для /api/orders (без accessToken)")
    public void getOrdersUserWithoutAuthorizationAndCheckResponse() {
        ValidatableResponse ordersClientResponse = ordersClient.getUserOrders("");
        ordersClientResponse
                .statusCode(401)
                .body("success", equalTo(false));
        assertEquals("You should be authorised", ordersClientResponse.extract().path("message"));
    }

    @After
    public void cleanUp() {
        if (userAccessToken != null) {
            userClient.delete(userAccessToken);
        }
    }
}
