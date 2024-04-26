package utils;

import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionHelper {

    public static void assertResponseStatusCodeAndBodyPresence(Response response,
                                                               int expectedStatusCode,
                                                               boolean isBodyPresent) {
        assertThat(response.statusCode()).isEqualTo(expectedStatusCode);
        if (isBodyPresent) {
            assertThat(response.body()).isNotNull();
        } else {
            assertThat(response.body()).isNull();
        }
    }

    public static void assertResponseBody(Response response, int expectedStatusCode,
                                          String codeMessage, String descriptionMessage) {
        assertThat(response.statusCode()).isEqualTo(expectedStatusCode);
        assertThat(response.jsonPath().get("code").toString())
                .isEqualTo(codeMessage);
        assertThat(response.jsonPath().get("description").toString())
                .isEqualTo(descriptionMessage);
    }
}
