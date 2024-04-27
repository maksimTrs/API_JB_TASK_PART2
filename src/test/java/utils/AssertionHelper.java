package utils;

import io.restassured.response.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionHelper {

    public static void assertResponseStatusCodeAndEmptyBody(Response response, int expectedStatusCode) {
        assertThat(response.statusCode()).isEqualTo(expectedStatusCode);
        assertThat(response.getBody().asString()).isEmpty();
    }

    public static void assertResponseStatusCodeAndContentType(Response response, int expectedStatusCode,
                                                              String contentType) {
        assertThat(response.statusCode()).isEqualTo(expectedStatusCode);
        assertThat(response.getHeader("Content-Type")).contains(contentType);
    }

    public static void assertResponseBody(Response response, int expectedStatusCode,
                                          String codeMessage, String descriptionMessage) {
        assertThat(response.statusCode()).isEqualTo(expectedStatusCode);
        assertThat(response.jsonPath().get("code").toString())
                .isEqualTo(codeMessage);
        assertThat(response.jsonPath().get("description").toString())
                .isEqualTo(descriptionMessage);
    }

    public static void assertLicenseIdsResponseBody(Response response, int expectedStatusCode, List<String> expectedLicenseIds) {
        assertThat(response.statusCode()).isEqualTo(expectedStatusCode);
        // Get the "licenseIds" value as a List
        List<String> actualLicenseIds = response.jsonPath().getList("licenseIds", String.class);
        // Check if the list is null or empty
        if (expectedLicenseIds.isEmpty()) {
            assertThat(actualLicenseIds).isEmpty();
        } else {
            // Compare the actual list with the expected list
            assertThat(actualLicenseIds).containsExactlyInAnyOrderElementsOf(expectedLicenseIds);
        }
    }
}
