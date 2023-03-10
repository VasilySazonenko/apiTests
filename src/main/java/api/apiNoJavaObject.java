package api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class apiNoJavaObject {
    public final static String URL = "https://reqres.in/";

    @Test
    public void checkAvatarsNoObject() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecOk200());
        Response response = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .body("page", equalTo(2))
                .body("data.id", notNullValue())
                .body("data.email", notNullValue())
                .body("data.first_name", notNullValue())
                .body("data.last_name", notNullValue())
                .body("data.avatar", notNullValue())
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List<String> emails = jsonPath.get("data.email");
        List<Integer> ids = jsonPath.get("data.id");
        List<String> avatars = jsonPath.get("data.avatar");

        for (int i = 0; i < avatars.size(); i++) {
            assertTrue(avatars.get(i).contains(ids.get(i).toString()));
        }
        assertTrue(emails.stream().allMatch(x -> x.endsWith("@reqres.in")));

    }

    @Test
    public void successUserRegTest() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecOk200());
        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "pistol");
        given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .body("id", equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void successRegTestResponse() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecOk200());
        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "pistol");
        Response response = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        int id = jsonPath.get("id");
        String token = jsonPath.get("token");
        assertEquals(4, id);
        assertEquals("QpwL5tke4Pnpja7X4", token);
    }

    @Test
    public void unsuccessRegNoObject() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecError400());
        Map<String, String> user = new HashMap<>();
        user.put("email", "sydney@fife");
        given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .body("error", equalTo("Missing password"));
    }
}
