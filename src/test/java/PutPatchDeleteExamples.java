import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class PutPatchDeleteExamples {
    @Test
    public void testPut() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "morpheus");
        map.put("job", "leader zeus");

        JSONObject request = new JSONObject(map);
        System.out.println(request.toJSONString());

        baseURI = "https://reqres.in/api";

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .put("/users/2")
                .then()
                .statusCode(200)
                .log().all();
    }
        @Test
        public void testPatch() {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "morpheus");
            map.put("job", "leader zeus");

            JSONObject request = new JSONObject(map);
            System.out.println(request.toJSONString());

            baseURI = "https://reqres.in";

            given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(request.toJSONString())
                    .when()
                    .patch("/api/users/2")
                    .then()
                    .statusCode(200)
                    .log().all();
        }
    @Test
    public void testDelete() {
               baseURI = "https://reqres.in";


                when()
                .delete("/api/users/2")
                .then()
                .statusCode(204)
                .log().all();
    }
}
