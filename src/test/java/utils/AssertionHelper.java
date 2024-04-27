package utils;

import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionHelper {

    public static void assertResponseStatusCodeAndEmptyBody(Response response, int expectedStatusCode) {
        assertThat(response.statusCode()).isEqualTo(expectedStatusCode);
        assertThat(response.getBody().asString()).isEmpty();
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
