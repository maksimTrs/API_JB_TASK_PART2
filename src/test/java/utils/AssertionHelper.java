package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionHelper {

    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String CODE_JSON_PATH = "code";
    private static final String DESCRIPTION_JSON_PATH = "description";
    private static final String LICENSES_IDS_JSON_PATH = "licenseIds";

    @Step("Assert response status code: {expectedStatusCode} and empty body")
    public static void assertResponseStatusCodeAndEmptyBody(Response response, int expectedStatusCode) {
        try {
            assertThat(response.statusCode())
                    .withFailMessage("Response status code is not " + expectedStatusCode)
                    .isEqualTo(expectedStatusCode);
            assertThat(response.getBody().asString()).withFailMessage("Response body is not empty").isEmpty();
        } catch (AssertionError e) {
            Allure.step("Assertion failed: Actual value does not match expected value: ");
            Allure.attachment("Expected status code", String.valueOf(expectedStatusCode));
            Allure.attachment("Actual body", String.valueOf(response.getBody().asString()));
            throw e;
        }
    }

    @Step("Assert response status code: {expectedStatusCode} and content type: {contentType}")
    public static void assertResponseStatusCodeAndContentType(Response response, int expectedStatusCode,
                                                              String expectedContentType) {
        try {
            assertThat(response.statusCode())
                    .withFailMessage("Response status code is not " + expectedStatusCode)
                    .isEqualTo(expectedStatusCode);
            assertThat(response.getHeader(HEADER_CONTENT_TYPE))
                    .withFailMessage("Response content type is not " + expectedContentType)
                    .contains(expectedContentType);
        } catch (AssertionError e) {
            Allure.step("Assertion failed: Actual value does not match expected value: ");
            Allure.attachment("Expected status code", String.valueOf(expectedStatusCode));
            Allure.attachment("Expected content type", expectedContentType);
            throw e;
        }
    }

    @Step("Assert response status code: {expectedStatusCode} and code: {codeMessage} and description: {descriptionMessage}")
    public static void assertResponseStatusCodeAndResponseBody(Response response, int expectedStatusCode,
                                                               String expectedCodeMessage, String expectedDescriptionMessage) {
        try {
            assertThat(response.statusCode())
                    .withFailMessage("Response status code is not " + expectedStatusCode)
                    .isEqualTo(expectedStatusCode);
            assertThat(response.jsonPath().get(CODE_JSON_PATH).toString())
                    .withFailMessage("code message is not " + expectedCodeMessage)
                    .isEqualTo(expectedCodeMessage);
            assertThat(response.jsonPath().get(DESCRIPTION_JSON_PATH).toString())
                    .withFailMessage("Description message is not " + expectedDescriptionMessage)
                    .isEqualTo(expectedDescriptionMessage);
        } catch (AssertionError e) {
            Allure.step("Assertion failed: Actual value does not match expected value: ");
            Allure.attachment("Expected code message", expectedCodeMessage);
            Allure.attachment("Expected description message", expectedDescriptionMessage);
            throw e;
        }
    }

    @Step("Assert response status code: {expectedStatusCode} and licenseIds: {expectedLicenseIds}")
    public static void assertResponseStatusCodeAndLicenseIds(Response response, int expectedStatusCode,
                                                             List<String> expectedLicenseIds) {

        assertThat(response.statusCode())
                .withFailMessage("Response status code is not " + expectedStatusCode)
                .isEqualTo(expectedStatusCode);

        try {
            List<String> actualLicenseIds = response.jsonPath().getList(LICENSES_IDS_JSON_PATH, String.class);

            if (expectedLicenseIds.isEmpty()) {
                assertThat(actualLicenseIds).withFailMessage("Response body is not empty").isEmpty();
            } else {
                // Compare the actual list with the expected list
                assertThat(actualLicenseIds)
                        .withFailMessage("License ids are not in " + expectedLicenseIds)
                        .containsExactlyInAnyOrderElementsOf(expectedLicenseIds);
            }
        } catch (AssertionError e) {
            Allure.step("Assertion failed: Actual value does not match expected value: ");
            Allure.attachment("Expected lLicense Ids", expectedLicenseIds.toString());
            throw e;
        }
    }
}
