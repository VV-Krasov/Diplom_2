package praktikum.order;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient {
    protected final String BASE_URI = "https://stellarburgers.nomoreparties.site/";
    protected final String ROOT = "/api/orders";

    public ValidatableResponse makeOrder(Order order)
    {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post(ROOT)
                .then();
    }

    public ValidatableResponse makeOrder(Order order, String authorizationToken)
    {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", authorizationToken)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post(ROOT)
                .then();
    }

    public ValidatableResponse makeOrder()
    {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .post(ROOT)
                .then();
    }

    public ValidatableResponse makeOrder(String authorizationToken)
    {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", authorizationToken)
                .baseUri(BASE_URI)
                .when()
                .post(ROOT)
                .then();
    }

    public ValidatableResponse getOrders(String authorizationToken)
    {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", authorizationToken)
                .baseUri(BASE_URI)
                .when()
                .get(ROOT)
                .then();
    }

    public ValidatableResponse getOrders()
    {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(ROOT)
                .then();
    }
}
