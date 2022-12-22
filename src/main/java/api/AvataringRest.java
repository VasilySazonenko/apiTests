package api;

import static io.restassured.RestAssured.given;


import static org.junit.Assert.*;

import org.junit.Test;
import java.util.List;
import java.util.stream.Collectors;


public class AvataringRest {
    public final static String URL = "https://reqres.in/";

    @Test
    public void checkAvatarAndIdTest() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecOk200());
        List<UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
        users.forEach(x -> assertTrue(x.getAvatar().contains(x.getId().toString())));
        assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));
        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());

        for (int i = 0; i < avatars.size(); i++) {
            assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    @Test
    public void successRegTest() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecOk200());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Registration user = new Registration("eve.holt@reqres.in", "pistol");
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);
        assertNotNull(successReg.getId());
        assertNotNull(successReg.getToken());
        assertEquals(id, successReg.getId());
        assertEquals(token, successReg.getToken());
    }

    @Test
    public void failedRegTest() {
        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecError400());
        String error = "Missing password";
        Registration user = new Registration("sydney@fife", "");
        FailedReg failedReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(FailedReg.class);
       assertEquals(error, failedReg.getError());
    }

    @Test
    public void checkYearList(){
        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecOk200());
List<YearList> yearList = given().
        when().
        get("api/unknown").
        then().log().all().
        extract().jsonPath().getList("data", YearList.class);
List<Integer> years = yearList.stream().map(YearList::getYear).collect(Collectors.toList());
List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());
assertEquals(sortedYears, years);
    }

    @Test
    public void checkDelete(){
        Specification.installSpecification(Specification.requestSpec(URL), Specification.responseSpecUnique(204));
   given()
           .when()
           .delete("/api/users?page=2")
           .then()
           .log()
           .all();
    }
}
