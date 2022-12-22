import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetAndPostExamples {
        @Test
    public void testGet() {
        baseURI = "https://reqres.in/api";
        given().get("/users?page=2")
                .then()
                .statusCode(200)
                .body("data[4].first_name", equalTo("George"))
                .body("data.first_name", hasItems("George", "Rachel"));
    }

    @Test
    public void testPost() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "morpheus");
        map.put("job", "leader");

        JSONObject request = new JSONObject(map);
        System.out.println(request.toJSONString());

        baseURI = "https://reqres.in/api";

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .log().all();
    }

}
