package tests.changeLicensesTeamApi;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.changeLicensesTeam.TeamLicenses;
import tests.BaseTest;

import java.util.List;

import static api.ApiConstants.LICENSE_CHANGE_ENDPOINT;
import static api.ApiConstants.TEAM002_ID_CODE;
import static api.ApiResponseBuilder.extractApiResponse;
import static enums.ApiErrorResponseCodeAndDescriptionFieldsEnum.TEAM_NOT_FOUND_RESPONSE;
import static enums.ApiErrorResponseCodeAndDescriptionFieldsEnum.getFormatDescription;
import static enums.ApiStatusCodesEnum.CODE_400;
import static enums.ApiStatusCodesEnum.CODE_404;
import static utils.AssertionHelper.assertResponseStatusCodeAndContentType;
import static utils.AssertionHelper.assertResponseStatusCodeAndResponseBody;
import static utils.AuthorCommentsForTests.COMMENT_1;


@Feature("API NEGATIVE TESTING: POST api/v1/customer/changeLicensesTeam")
@Story("Testing POST api/v1/customer/changeLicensesTeam")
public class JbChangeLicensesTeamNegativeTest extends BaseTest {

    private final List<String> LICENSE_EMPTY_LIST = List.of();

    @Description("Testing API: validate  payload with dummy 'targetTeamId' field")
    @Test
    public void verifyResponse404WithInvalidTargetTeamIdTest() {
        int nonValidTeamId = 999;
        TeamLicenses teamLicenses = TeamLicenses.builder()
                .targetTeamId(nonValidTeamId)
                .licenseIds(LICENSE_EMPTY_LIST)
                .build();

        Response response = extractApiResponse(teamLicenses, LICENSE_CHANGE_ENDPOINT);

        assertResponseStatusCodeAndResponseBody(response, CODE_404.CODE, TEAM_NOT_FOUND_RESPONSE.getCode(),
                getFormatDescription(TEAM_NOT_FOUND_RESPONSE.getDescription(),
                        String.valueOf(teamLicenses.getTargetTeamId()), ""));
    }

    @Description("Testing API: validate response without 'licenseIds' partition")
    @Test
    public void verifyResponseWithoutLicenseIdsSectionTest() {
        TeamLicenses teamLicenses = TeamLicenses.builder()
                .targetTeamId(TEAM002_ID_CODE)
                .build();

        Response response = extractApiResponse(teamLicenses, LICENSE_CHANGE_ENDPOINT);

        /* I presume we have an API design validation issue here - instead of JSON response structure as we have
         *  for another negative tests:
         *  assertResponseBody(response, CODE_400.CODE, MISSING_FIELD.getCode(), MISSING_FIELD.getDescription());
         *  we are getting HTML response structure
         * */
        Allure.addAttachment("COMMENT FOR TEST:", COMMENT_1);
        assertResponseStatusCodeAndContentType(response, CODE_400.CODE, "application/json");
    }
}
