package Formula1RacingKeywords;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class DriverInformation {

    private static DriverInformation instance = null;

    private DriverInformation() {
    }

    public static DriverInformation getInstance () {
        if(instance == null){
            instance = new DriverInformation();
        }
        return instance;
    }

    public Response getDrivers() {
        return given().when().get("/drivers.json");
    }

    public Response getDriver(String driverId){
        return given().when().get("/drivers/{driverId}.json", driverId);
    }
}
