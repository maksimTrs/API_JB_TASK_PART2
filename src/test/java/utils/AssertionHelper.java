package utils;

import io.restassured.response.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionHelper {

    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String CODE_JSON_PATH = "code";
    private static final String DESCRIPTION_JSON_PATH = "description";
    private static final String LICENSES_IDS_JSON_PATH = "licenseIds";

    public static void assertResponseStatusCodeAndEmptyBody(Response response, int expectedStatusCode) {
        assertThat(response.statusCode()).isEqualTo(expectedStatusCode);
        assertThat(response.getBody().asString()).isEmpty();
    }

    public static void assertResponseStatusCodeWithContentTypeValue(Response response, int expectedStatusCode,
                                                                    String contentType) {
        assertThat(response.statusCode()).isEqualTo(expectedStatusCode);
        assertThat(response.getHeader(HEADER_CONTENT_TYPE)).contains(contentType);
    }

    public static void assertResponseStatusCodeWithCodeAndDescriptionValues(Response response, int expectedStatusCode,
                                                                            String codeMessage, String descriptionMessage) {
        assertThat(response.statusCode()).isEqualTo(expectedStatusCode);
        assertThat(response.jsonPath().get(CODE_JSON_PATH).toString())
                .isEqualTo(codeMessage);
        assertThat(response.jsonPath().get(DESCRIPTION_JSON_PATH).toString())
                .isEqualTo(descriptionMessage);
    }

    public static void assertResponseStatusCodeWithLicenseIdsValues(Response response, int expectedStatusCode,
                                                                    List<String> expectedLicenseIds) {
        assertThat(response.statusCode()).isEqualTo(expectedStatusCode);

        List<String> actualLicenseIds = response.jsonPath().getList(LICENSES_IDS_JSON_PATH, String.class);

        if (expectedLicenseIds.isEmpty()) {
            assertThat(actualLicenseIds).isEmpty();
        } else {
            // Compare the actual list with the expected list
            assertThat(actualLicenseIds).containsExactlyInAnyOrderElementsOf(expectedLicenseIds);
        }
    }
}
