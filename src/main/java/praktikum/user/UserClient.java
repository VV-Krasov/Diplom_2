package praktikum.user;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient {
    protected final String BASE_URI = "https://stellarburgers.nomoreparties.site/";
    protected final String ROOT = "/api/auth";
    public ValidatableResponse create(User user)
    {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(user)
                .when()
                .post(ROOT + "/register")
                .then();
    }

    public ValidatableResponse login(UserCredentials userCredentials)
    {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(userCredentials)
                .when()
                .post(ROOT + "/login")
                .then();
    }

    public ValidatableResponse logout(String refreshToken)
    {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(refreshToken)
                .when()
                .post(ROOT + "/logout")
                .then();
    }







/*
    public ValidatableResponse getFreshToken(String token) {
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .baseUri(BASE_URI)
                .when()
                .post(ROOT + "/token")
                .then();
        return response;
    }*/
    public ValidatableResponse getData(UserCredentials userCredentials)
    {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(userCredentials)
                .when()
                .get(ROOT + "/user")
                .then();
    }

    public ValidatableResponse changeData(String token, User newUserData)
    {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(BASE_URI)
                .body(newUserData)
                .when()
                .patch(ROOT + "/user")
                .then();
    }

    public ValidatableResponse changeData(User newUserData)
    {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(newUserData)
                .when()
                .patch(ROOT + "/user")
                .then();
    }
}
