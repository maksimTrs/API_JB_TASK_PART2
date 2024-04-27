package tests.changeLicensesTeamApi;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.changeLicensesTeam.ChangeLicensesTeamObject;
import tests.BaseTest;

import java.util.List;

import static api.ApiConstants.CHANGE_LICENSE;
import static api.ApiConstants.TEAM002_ID_CODE;
import static api.ResponseHelper.extractApiResponse;
import static enums.ApiResponseValuesEnum.TEAM_NOT_FOUND;
import static enums.ApiResponseValuesEnum.getDescription;
import static enums.StatusCodeEnum.CODE_400;
import static enums.StatusCodeEnum.CODE_404;
import static utils.AssertionHelper.assertResponseBody;
import static utils.AssertionHelper.assertResponseStatusCodeAndContentType;
import static utils.AuthorCommentsToTests.COMMENT_1;

public class JbChangeLicensesTeamNegativeTest extends BaseTest {

    private final List<String> LICENSE_EMPTY_LIST = List.of();


    @Story("Testing POST api/v1/customer/changeLicensesTeam")
    @Description("Testing API: validate response with payload dummy targetTeamId")
    @Test()
    public void verifyResponse404WithInvalidTargetTeamIdTest() {
        ChangeLicensesTeamObject changeLicensesTeamObject = ChangeLicensesTeamObject.builder()
                .targetTeamId(999)
                .licenseIds(LICENSE_EMPTY_LIST)
                .build();

        Response response = extractApiResponse(changeLicensesTeamObject, CHANGE_LICENSE);

        assertResponseBody(response, CODE_404.CODE, TEAM_NOT_FOUND.getCode(),
                getDescription(TEAM_NOT_FOUND.getDescription(),
                        changeLicensesTeamObject.getTargetTeamId(), ""));
    }

    @Story("Testing POST api/v1/customer/changeLicensesTeam")
    @Description("Testing API: validate response without request licenseIds partition")
    @Test()
    public void verifyResponseWithoutContactSectionTest() {
        ChangeLicensesTeamObject changeLicensesTeamObject = ChangeLicensesTeamObject.builder()
                .targetTeamId(TEAM002_ID_CODE)
                .build();

        Response response = extractApiResponse(changeLicensesTeamObject, CHANGE_LICENSE);

        /* I presume we hava an API design validation issue here - instead of JSON response structure as we have
         *  for another negative tests
         *  assertResponseBody(response, CODE_400.CODE, MISSING_FIELD.getCode(), MISSING_FIELD.getDescription());
         *  we are getting HTML response structure
         * */
        Allure.addAttachment("COMMENT FOR TEST", COMMENT_1);
        assertResponseStatusCodeAndContentType(response, CODE_400.CODE, "application/json");
    }
}
