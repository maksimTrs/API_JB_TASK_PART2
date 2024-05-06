package utils;


import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static io.qameta.allure.model.Parameter.Mode.HIDDEN;
import static org.assertj.core.api.Assertions.assertThat;

public class AssertionHelper {

    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String CODE_JSON_PATH = "code";
    private static final String DESCRIPTION_JSON_PATH = "description";
    private static final String LICENSES_IDS_JSON_PATH = "licenseIds";

    @Step("Assert response status code: {expectedStatusCode} and empty body")
    public static void assertResponseStatusCodeAndEmptyBody(@Param(name = "response", mode = HIDDEN) Response response,
                                                            int expectedStatusCode) {
        assertThat(response.statusCode())
                .withFailMessage("Response status code is not " + expectedStatusCode)
                .isEqualTo(expectedStatusCode);
        assertThat(response.getBody().asString()).withFailMessage("Response body is not empty").isEmpty();
    }

    @Step("Assert response status code: {expectedStatusCode} and content type: {expectedContentType}")
    public static void assertResponseStatusCodeAndContentType(@Param(name = "response", mode = HIDDEN) Response response,
                                                              int expectedStatusCode,
                                                              String expectedContentType) {
        assertThat(response.statusCode())
                .withFailMessage("Response status code is not " + expectedStatusCode)
                .isEqualTo(expectedStatusCode);
        assertThat(response.getHeader(HEADER_CONTENT_TYPE))
                .withFailMessage("Response content type is not " + expectedContentType)
                .contains(expectedContentType);
    }

    @Step("Assert response status code: {expectedStatusCode} and code: {expectedCodeMessage} " +
            "and description: {expectedDescriptionMessage}")
    public static void assertResponseStatusCodeAndResponseBody(@Param(name = "response", mode = HIDDEN) Response response,
                                                               int expectedStatusCode,
                                                               String expectedCodeMessage, String expectedDescriptionMessage) {
        assertThat(response.statusCode())
                .withFailMessage("Response status code is not " + expectedStatusCode)
                .isEqualTo(expectedStatusCode);
        assertThat(response.jsonPath().get(CODE_JSON_PATH).toString())
                .withFailMessage("code message is not " + expectedCodeMessage)
                .isEqualTo(expectedCodeMessage);
        assertThat(response.jsonPath().get(DESCRIPTION_JSON_PATH).toString())
                .withFailMessage("Description message is not " + expectedDescriptionMessage)
                .isEqualTo(expectedDescriptionMessage);
    }

    @Step("Assert response status code: {expectedStatusCode} and licenseIds: {expectedLicenseIds}")
    public static void assertResponseStatusCodeAndLicenseIds(@Param(name = "response", mode = HIDDEN) Response response,
                                                             int expectedStatusCode,
                                                             List<String> expectedLicenseIds) {

        assertThat(response.statusCode())
                .withFailMessage("Response status code is not " + expectedStatusCode)
                .isEqualTo(expectedStatusCode);

        List<String> actualLicenseIds = response.jsonPath().getList(LICENSES_IDS_JSON_PATH, String.class);

        if (expectedLicenseIds.isEmpty()) {
            assertThat(actualLicenseIds).withFailMessage("Response body is not empty").isEmpty();
        } else {
            // Compare the actual list with the expected list
            assertThat(actualLicenseIds)
                    .withFailMessage("Response licenseIds is not " + expectedLicenseIds)
                    .containsExactlyInAnyOrderElementsOf(expectedLicenseIds);
        }
    }
}
