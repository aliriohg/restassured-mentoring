package mentoring;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestAssuredDemoTest {

// page with services "http://dummy.restapiexample.com/"
//dummy rest api with query parameters

    private String uri = "http://dummy.restapiexample.com";
    private String path = "api/v1";


    @Test
    public void testGetRestAssured(){

        given().log().all().baseUri(uri).basePath(path)
                .when().get("employees")
                        .then().log().all();

    }

    @Test
    public void testPostRestAssured(){
        given().log().all().baseUri(uri).basePath(path).contentType(ContentType.JSON)
                .body("{\"name\":\"test\",\"salary\":\"123\",\"age\":\"23\"}")
                .when().post("create")
                .then().log().all()
                .statusCode(200)
        .assertThat().body("data.name",equalTo("test"));
    }

    @Test
    public void testGetPathParamRestAssured(){

        given().log().all().baseUri(uri).basePath(path)
                .pathParam("parameter","1")
                .when().get("employee/{parameter}")
                .then().log().all();

    }

    @Test
    public void testAuthRestAssured(){

        given().log().all().baseUri(uri).basePath(path)
                .auth().basic("user","contra")
                .when().get("employee/parameter")
                .then().log().all();

    }

    @Test
    public void testAuthQueryRestAssured(){

        given().log().all()
                .queryParams("q", Arrays.asList("title:DNA","alirio"))
                .when().get("http://api.plos.org/search")
                .then().log().all();

    }


}
