package utils;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.when;
import static tests.BaseTest.logger;

public class MockHelper {

    public static Response mockResponse(int statusCode) {
        Response response = Mockito.mock(Response.class);
        when(response.statusCode()).thenReturn(statusCode);

        logger.info("<<< The status code is: " + response.statusCode() + ". Mock is triggered >>>");
        return response;
    }
}
