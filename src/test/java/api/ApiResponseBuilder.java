package api;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static tests.BaseTest.*;


public class ApiResponseBuilder {

    public static Response extractApiResponse(Object requestBody, String endPointUrl) {
        return given()
                .spec(testRequestSpecification)
                .filter(new RequestLoggingFilter(LogDetail.URI, printStream))
                .filter(new ResponseLoggingFilter(LogDetail.ALL, printStream))
                .body(requestBody)
                .when()
                .post(endPointUrl)
                .then()
                .spec(testResponseSpecification)
                .extract().response();
    }

    public static Response extractApiResponseWithInvalidApiKeyCodeHeader(Object requestBody, String endPointUrl,
                                                                         RequestSpecification specWithInvalidApiKey) {
        return given()
                .spec(specWithInvalidApiKey)
                .filter(new RequestLoggingFilter(LogDetail.URI, printStream))
                .filter(new ResponseLoggingFilter(LogDetail.ALL, printStream))
                .body(requestBody)
                .when()
                .post(endPointUrl)
                .then()
                .spec(testResponseSpecification)
                .extract().response();
    }
}
