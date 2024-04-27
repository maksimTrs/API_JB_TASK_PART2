package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static api.ApiConstants.*;


public abstract class BaseTest {


    private static final Map<String, String> API_HEADERS = new HashMap<>();
    public static Logger logger = Logger.getLogger(BaseTest.class);
    public static PrintStream printStream;
    public static RequestSpecification testRequestSpecification;
    public static RequestSpecification invalidApiKeyCodeRequestSpecification;
    public static RequestSpecification restrictedApiKeyCodeRequestSpecification;
    public static ResponseSpecification testResponseSpecification;

    @BeforeSuite
    public void beforeSuite() {

        API_HEADERS.put("X-Customer-Code", X_CUSTOMER_CODE);
        API_HEADERS.put("X-Api-Key", X_API_KEY);

        invalidApiKeyCodeRequestSpecification = createRequestSpecificationWithInvalidXApiKey("abcd1XyZ");
        restrictedApiKeyCodeRequestSpecification = createRequestSpecificationWithInvalidXApiKey(TEAM_X_API_KEY);
    }


    @BeforeClass
    public void setUp() throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
        String timestamp = dateFormat.format(new Date());
        Path directories = Files.createDirectories(Paths.get("logs/" + timestamp));
        printStream = new PrintStream(directories + "/RestAPILog_" + timestamp + ".log");

        testRequestSpecification = new RequestSpecBuilder()
                .setBaseUri(HOST)
                .setBasePath(SERVICE)
                .addFilter(new AllureRestAssured())
                .setContentType(ContentType.JSON)
                .addHeaders(API_HEADERS)
                .log(LogDetail.BODY)
                .build();

        testResponseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }


    @BeforeMethod
    public void beforeMethod(Method m) {
        logger.info("********************************************************************************");
        logger.info("TEST STARTED: " + m.getName());
        logger.info("********************************************************************************");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(Method m) {
        logger.info("********************************************************************************");
        logger.info("TEST FINISHED: " + m.getName());
        logger.info("********************************************************************************");
    }


    private RequestSpecification createRequestSpecificationWithInvalidXApiKey(String apiKeyValue) {
        return new RequestSpecBuilder()
                .setBaseUri(HOST)
                .setBasePath(SERVICE)
                .addFilter(new AllureRestAssured())
                .setContentType(ContentType.JSON)
                .addHeader("X-Api-Key", apiKeyValue)
                .addHeader("X-Customer-Code", API_HEADERS.get("X-Customer-Code"))
                .log(LogDetail.BODY)
                .build();
    }
}
