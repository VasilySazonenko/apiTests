import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TestExamples {
    @Test
    public void Test1() {
        Response response = get("https://reqres.in/api/users?page=2");
        System.out.println(response.getStatusCode());
        System.out.println("***********");
        System.out.println(response.getTime());
        System.out.println(response.getBody().asString());
        System.out.println("***********");
        System.out.println(response.getStatusLine());
        System.out.println("***********");
        System.out.println(response.getHeader("content-type"));
    }

    @Test
    public void Test2() {
        baseURI = "https://reqres.in/api";
        given().get("/users?page=2")
                .then()
                .statusCode(200)
                .body("data[1].id", equalTo(8))
                .log().all();
    }
}
