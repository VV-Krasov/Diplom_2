package praktikum;

import org.junit.Test;
import praktikum.ingredient.IngredientsGenerator;
import praktikum.order.Order;
import praktikum.order.OrderClient;
import praktikum.order.OrderGenerator;
import praktikum.user.User;
import praktikum.user.UserClient;
import praktikum.user.UserGenerator;

import static org.apache.http.HttpStatus.*;

import static org.hamcrest.Matchers.*;

public class OrderCreateTest {
    private final User user = UserGenerator.createRandomUser();
    private static final UserClient userClient = new UserClient();
    private final Order order = OrderGenerator.createRandomOrder(
            IngredientsGenerator.getAvailableIngredients());
    private static final OrderClient orderClient = new OrderClient();

    @Test
    public void createOrderWithIngredientsWithAuthorization()
    {
        String accessToken = userClient.create(user)
                .assertThat()
                .body("success", is(true)).extract().path("accessToken");

        orderClient.makeOrder(order, accessToken)
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true)).and()
                .body("order.number", greaterThan(0)).and()
                .body("name", is(not(emptyString())));
    }

    @Test
    public void createOrderWithoutIngredientsWithAuthorization()
    {

        String accessToken = userClient.create(user)
                .assertThat()
                .body("success", is(true)).extract().path("accessToken");

        orderClient.makeOrder(accessToken)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false)).and()
                .body("message", is("Ingredient ids must be provided")).and()
                .body("name", nullValue()).and()
                .body("order", nullValue());
    }


    @Test
    public void createOrderWithIngredientsWithoutAuthorization()
    {
        orderClient.makeOrder(order)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("name", nullValue()).and()
                .body("order", nullValue());
    }

    @Test
    public void createOrderWithoutIngredientsWithoutAuthorization()
    {
        orderClient.makeOrder()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("name", nullValue()).and()
                .body("order", nullValue());
    }

    @Test
    public void createOrderWithWrongHashOfIngredientsWithAuthorization()
    {
        String accessToken = userClient.create(user)
                .assertThat()
                .body("success", is(true)).extract().path("accessToken");

        orderClient.makeOrder(OrderGenerator.createRandomOrderWithWrongHashIngredients(), accessToken)
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void createOrderWithWrongHashOfIngredientsWithoutAuthorization()
    {

        orderClient.makeOrder(OrderGenerator.createRandomOrderWithWrongHashIngredients())
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

}