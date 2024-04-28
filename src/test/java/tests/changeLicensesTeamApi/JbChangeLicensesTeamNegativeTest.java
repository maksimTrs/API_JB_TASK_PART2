package tests.changeLicensesTeamApi;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.changeLicensesTeam.ChangeLicensesTeamObject;
import tests.BaseTest;

import java.util.List;

import static api.ApiConstants.LICENSE_CHANGE_ENDPOINT;
import static api.ApiConstants.TEAM002_ID_CODE;
import static api.ApiResponseBuilder.extractApiResponse;
import static enums.ApiResponseCodeAndDescriptionFieldsEnum.TEAM_NOT_FOUND;
import static enums.ApiResponseCodeAndDescriptionFieldsEnum.getDescription;
import static enums.ApiStatusCodesEnum.CODE_400;
import static enums.ApiStatusCodesEnum.CODE_404;
import static utils.AssertionHelper.assertResponseStatusCodeWithCodeAndDescriptionValues;
import static utils.AssertionHelper.assertResponseStatusCodeWithContentTypeValue;
import static utils.AuthorCommentsForTests.COMMENT_1;


@Feature("API NEGATIVE TESTING: POST api/v1/customer/changeLicensesTeam")
@Story("Testing POST api/v1/customer/changeLicensesTeam")
public class JbChangeLicensesTeamNegativeTest extends BaseTest {

    private final List<String> LICENSE_EMPTY_LIST = List.of();

    @Description("Testing API: validate  payload with dummy 'targetTeamId' field")
    @Test()
    public void verifyResponse404WithInvalidTargetTeamIdTest() {
        ChangeLicensesTeamObject changeLicensesTeamObject = ChangeLicensesTeamObject.builder()
                .targetTeamId(999)
                .licenseIds(LICENSE_EMPTY_LIST)
                .build();

        Response response = extractApiResponse(changeLicensesTeamObject, LICENSE_CHANGE_ENDPOINT);

        assertResponseStatusCodeWithCodeAndDescriptionValues(response, CODE_404.CODE, TEAM_NOT_FOUND.getCode(),
                getDescription(TEAM_NOT_FOUND.getDescription(),
                        changeLicensesTeamObject.getTargetTeamId(), ""));
    }

    @Description("Testing API: validate response without 'licenseIds' partition")
    @Test()
    public void verifyResponseWithoutLicenseIdsSectionTest() {
        ChangeLicensesTeamObject changeLicensesTeamObject = ChangeLicensesTeamObject.builder()
                .targetTeamId(TEAM002_ID_CODE)
                .build();

        Response response = extractApiResponse(changeLicensesTeamObject, LICENSE_CHANGE_ENDPOINT);

        /* I presume we have an API design validation issue here - instead of JSON response structure as we have
         *  for another negative tests:
         *  assertResponseBody(response, CODE_400.CODE, MISSING_FIELD.getCode(), MISSING_FIELD.getDescription());
         *  we are getting HTML response structure
         * */
        Allure.addAttachment("COMMENT FOR TEST:", COMMENT_1);
        assertResponseStatusCodeWithContentTypeValue(response, CODE_400.CODE, "application/json");
    }
}
