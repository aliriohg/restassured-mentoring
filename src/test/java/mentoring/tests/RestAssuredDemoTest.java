package mentoring.tests;

import io.restassured.http.ContentType;
import mentoring.entities.UpdateUserResponse;
import mentoring.entities.UserBody;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestAssuredDemoTest {


    private String uri = "https://reqres.in/";
    private String basePath = "api";

    @Test
    public void testGetRestAssured() {

        given().log().all().baseUri(uri).basePath(basePath)
                .when().get("/users/2")
                .then().log().all().statusCode(200);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5})
    public void testGetWithPathParamRestAssured(int userId) {
        given().log().all().baseUri(uri).basePath(basePath)
                .pathParam("userId", userId)
                .when().get("users/{userId}")
                .then().log().all().statusCode(200);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5})
    public void testGetWithQueryParamRestAssured(int queryParam) {

        given().log().all().baseUri(uri).basePath(basePath).queryParams("page", queryParam)
                .when().get("users")
                .then().log().all().statusCode(200);
    }


    @ParameterizedTest
    @ValueSource(ints = {21, 23, 55, Integer.MAX_VALUE})
    public void testUserNotFound(int userId) {
        given().log().all().baseUri(uri).basePath(basePath)
                .pathParam("userId", userId)
                .when().get("users/{userId}")
                .then().log().all().statusCode(404);
    }

    @Test
    public void testValidateResponseRestAssured() {

        given().log().all().baseUri(uri).basePath(basePath)
                .when().get("unknown")
                .then().log().all().statusCode(200)
                .body("data", hasSize(6))
                .body("page", equalTo(1))
                .body("data.id", hasItems(1, 2, 3, 4, 5, 6));
    }

    @Test
    public void testPostRestAssured() {
        given().log().all().baseUri(uri).basePath(basePath).contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .when().post("users")
                .then().log().all()
                .statusCode(201)
                .body("id", notNullValue());
    }

    @Test
    public void testPostBodyAsObjectRestAssured() {
        UserBody body = new UserBody("morpheus", "leader");
        given().log().all().baseUri(uri).basePath(basePath).contentType(ContentType.JSON)
                .body(body)
                .when().post("users")
                .then().log().all()
                .statusCode(201)
                .body("id", notNullValue());
    }

    @Test
    public void testPutSaveResponseAsObjectRestAssured() {
        UserBody body = new UserBody("morpheus", "leader");
        UpdateUserResponse response = given().log().all().baseUri(uri).basePath(basePath).contentType(ContentType.JSON)
                .body(body)
                .when().put("users/2")
                .then().log().all()
                .statusCode(200).extract().body().as(UpdateUserResponse.class);

        System.out.println(response.getUpdatedAt());
    }


    static Stream<Arguments> stringIntAndListProvider() {
        return Stream.of(
                Arguments.of("user", "passwd"),
                Arguments.of("alriio", "contra")
        );
    }

    @ParameterizedTest
    @MethodSource("stringIntAndListProvider")
    void testBasicAuthRestAssured(String user, String password) {
        String url = "https://httpbin.org";
        given().log().all().baseUri(url).basePath("/basic-auth/{user}/{passwd}")
                .pathParam("user", user).pathParam("passwd", password)
                .auth().basic(user, password)
                .log().all().when().get()
                .then().log().all().statusCode(200);
    }

    @Test
    void testBaererAuthRestAssured() {
        String url = "https://httpbin.org";
        given().log().all().baseUri(url).basePath("/basic-auth/bearer")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkc2IuZWFzeXNvbC5uZXQvIiwic3ViIjoiRUFTWVNPTC8zNDY0NSIsImV4cCI6MTYwMDkxNDg3MH0.fcSdWsM9xo7YlMJi76cFNkZu_NUfPrs39RAxVi8QA00")
                .log().all().when().get()
                .then().log().all().statusCode(200);
    }

}
