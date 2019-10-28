package Formula1RacingKeywords;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class CircuitInformation {

    private static CircuitInformation instance;

    private CircuitInformation() {
    }

    public static CircuitInformation getInstance () {
        if(instance == null){
            instance = new CircuitInformation();
        }
        return instance;
    }

    public Response getCircuits() {
        return given().when().get("/circuits.json");
    }
}
