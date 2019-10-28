package exercises;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.config.EncoderConfig.encoderConfig;

import Formula1RacingKeywords.KeywordManager;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import properties.PropertiesController;

public class BaseTestCaseWithKeywords {

    private static final String BASE_URI = PropertiesController.getProperty("workshop.base.url");
    private static final String BASE_PATH = PropertiesController.getProperty("workshop.base.path");
    private WireMockServer wireMockServer;
    KeywordManager keywordManager;

    BaseTestCaseWithKeywords() {
        keywordManager = KeywordManager.getInstance();
    }

    @BeforeSuite
    public void before() {
        RestAssuredConfig config = RestAssured.config()
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)
                        .defaultContentCharset("UTF-8"));

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setConfig(config)
                .setBaseUri(BASE_URI)
                .setBasePath(BASE_PATH)
                .setContentType(ContentType.ANY)
                .setAccept(ContentType.ANY)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        wireMockServer = new WireMockServer(options().port(8089));
        wireMockServer.start();
    }

    @AfterSuite
    public void after() {
        wireMockServer.stop();
    }
}
