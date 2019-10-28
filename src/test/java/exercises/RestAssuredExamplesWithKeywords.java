package exercises;

import static org.hamcrest.Matchers.hasSize;

import io.restassured.response.Response;
import java.net.HttpURLConnection;
import org.testng.annotations.Test;

public class RestAssuredExamplesWithKeywords extends BaseTestCaseWithKeywords {

    @Test
    public void testGetDrivers() {
        Response driversResponse = keywordManager.driverInformationKeywords().getDrivers();
        driversResponse.then().statusCode(HttpURLConnection.HTTP_OK);
    }

    @Test
    public void testGetConcreteDriver() {
        Response driversResponse = keywordManager.driverInformationKeywords().getDriver("abate");
        driversResponse.then().body("MRData.DriverTable.Drivers", hasSize(1));
    }

    @Test
    public void testGetCircuits(){
        keywordManager.circuitInformationKeywords().getCircuits().then().statusCode(HttpURLConnection.HTTP_OK);
    }
}
