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

public class OrdersCreateWithoutParametersTest {
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

    }

    @Test
    @DisplayName("Не успешное создание заказа с неверным хешем ингредиентов.")
    @Description("Проверка кода состояния и значения поля для /orders (запрос с неверным ingredients)")
    public void createOrdersWithIncorrectOrdersAndCheckResponse() {
        orders = OrdersGenerator.getIncorrectOrders();
        ValidatableResponse createOrdersResponse = ordersClient.createOrders(userAccessToken, orders);
        createOrdersResponse
                .statusCode(500);
    }

    @Test
    @DisplayName("Успешное создание заказа без ингредиентов.")
    @Description("Проверка кода состояния и значения поля для /orders (запрос без ingredients)")
    public void createOrdersWithoutIngredientsAndCheckResponse() {
        orders = OrdersGenerator.getOrders();
        ValidatableResponse createOrdersResponse = ordersClient.createOrderWithoutIngredients(userAccessToken);
        createOrdersResponse
                .statusCode(400);
        assertEquals("Ingredient ids must be provided", createOrdersResponse.extract().path("message"));
    }

    @Test
    @DisplayName("Успешное создание заказа без авторизации.")
    @Description("Проверка кода состояния и значения поля для /orders (запрос без AccessToken)")
    public void createOrdersWithoutTokenAndCheckResponse() {
        orders = OrdersGenerator.getOrders();
        ValidatableResponse createOrdersResponse = ordersClient.createOrderWithoutAccessToken(orders);
        createOrdersResponse
                .statusCode(200);
    }

    @After
    public void cleanUp() {
        if (userAccessToken != null) {
            userClient.delete(userAccessToken);
        }
    }
}
