package exercises;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

import io.restassured.http.ContentType;
import java.net.HttpURLConnection;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class SampleTestCase extends BaseTestCase {

    @Test
    public void someTest(){
        given()
                .when()
                .get("/2014/1/circuits.json")
                .then()
                .body("MRData.series", Matchers.equalTo("f1"))
                .and().body("MRData.CircuitTable.Circuits[0].circuitName", containsString("Grand Prix"))
                .and().body("MRData.CircuitTable.Circuits.Location.country", hasItem("Australia"));
    }

    @Test
    public void jsonSchemaValidationTest(){
        given()
                .when()
                .get("/2014/1/circuits.json")
                .then()
                .body(matchesJsonSchemaInClasspath("json_schema/circuit-schema.json"));
    }

    @Test
    public void xmlSchemaValidationTest(){
        given()
                .when()
                .basePath("")
                .get("/xml/cars")
                .then()
                .body(matchesXsdInClasspath("xml-schema/cars-xsd.xml"));
    }
/* Work only for local_env*/
    @Test
    public void getOauth2TokenTest(){

        String accessToken = given()
                .when()
                .auth().preemptive()
                .basic("oauth", "gimmeatoken")
                .get("oauth2/token")
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .contentType(ContentType.JSON)
                .body("access_token", not(isEmptyOrNullString()))
                .extract().path("access_token");

        assertThat(accessToken, equalTo("access_granted"));
    }

    @Test
    public void testGetCircuitsWithKeywords(){
        circuitInformation.getCircuits().then().statusCode(HttpURLConnection.HTTP_OK);
    }

    @Test
    public void testGetDriversWithKeywords(){
        driverInformation.getDrivers().then().statusCode(HttpURLConnection.HTTP_OK);
    }
}
