package praktikum.ingredient;

import io.restassured.http.ContentType;

import java.util.List;

import static io.restassured.RestAssured.given;

public class IngredientsGenerator {
    public static List<String> getAvailableIngredients()
    {
        return given()
                .contentType(ContentType.JSON)
                .baseUri("https://stellarburgers.nomoreparties.site/")
                .when()
                .get("api/ingredients")
                .then().extract().jsonPath().get("data._id"); //jsonPath().getJsonObject("data");
    }

}
