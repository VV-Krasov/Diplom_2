package praktikum;

import org.junit.Assert;
import org.junit.Test;
import praktikum.ingredient.IngredientsGenerator;
import praktikum.order.Order;
import praktikum.order.OrderClient;
import praktikum.order.OrderGenerator;
import praktikum.user.User;
import praktikum.user.UserClient;
import praktikum.user.UserGenerator;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.*;

public class GetUserOrdersTest {
    private final User user = UserGenerator.createRandomUser();
    private static final UserClient userClient = new UserClient();
    private final Order order = OrderGenerator.createRandomOrder(
            IngredientsGenerator.getAvailableIngredients());
    private static final OrderClient orderClient = new OrderClient();
    @Test
    public void getUserOrdersWithAuthorizationWithZeroOrders()
    {
        String accessToken = userClient.create(user)
                .assertThat()
                .body("success", is(true)).extract().path("accessToken");

        orderClient.getOrders(accessToken)
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true)).and()
                .body("orders", is(empty()));
    }

    @Test
    public void getUserOrdersWithoutAuthorizationWithZeroOrders()
    {
        orderClient.getOrders()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", is("You should be authorised")).and()
                .body("orders", is(nullValue()));
    }

    @Test
    public void getUserOrdersWithAuthorizationWithRandomOrders()
    {
        String accessToken = userClient.create(user)
                .assertThat()
                .body("success", is(true)).extract().path("accessToken");

        orderClient.makeOrder(order, accessToken)
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true)).and()
                .body("order.number", greaterThan(0)).and()
                .body("name", notNullValue()).and()
                .body("order", notNullValue());

        Order orderFromRequest = new Order(orderClient.getOrders(accessToken)
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true)).and()
                .extract().path("orders.ingredients"));
        Assert.assertThat(orderFromRequest.getIngredients(), is(contains(order.getIngredients())));
    }

    @Test
    public void getUserOrdersWithoutAuthorizationWithRandomOrders()
    {
        String accessToken = userClient.create(user)
                .assertThat()
                .body("success", is(true)).extract().path("accessToken");

        orderClient.makeOrder(order, accessToken)
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true)).and()
                .body("order.number", greaterThan(0)).and()
                .body("name", notNullValue()).and()
                .body("order", notNullValue());

        orderClient.getOrders()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", is("You should be authorised")).and()
                .body("orders", is(nullValue()));
    }
}