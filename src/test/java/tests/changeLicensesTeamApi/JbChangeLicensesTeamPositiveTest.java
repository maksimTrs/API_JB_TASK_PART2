package tests.changeLicensesTeamApi;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.changeLicensesTeam.TeamLicenses;
import tests.BaseTest;

import java.util.List;

import static api.ApiConstants.*;
import static api.ApiResponseBuilder.extractApiResponse;
import static enums.ApiStatusCodesEnum.CODE_200;
import static steps.TestSteps.prepareLicensesForTheTeam;
import static utils.AssertionHelper.assertResponseStatusCodeAndLicenseIds;
import static utils.PropertyReader.getLicenseListFromBundle;

@Feature("API POSITIVE TESTING: POST api/v1/customer/changeLicensesTeam")
@Story("Testing POST api/v1/customer/changeLicensesTeam")
public class JbChangeLicensesTeamPositiveTest extends BaseTest {

    private static final List<String> LICENSE_LIST = getLicenseListFromBundle("DataGripLicenseListIDs");
    private final List<String> LICENSE_EMPTY_LIST = List.of();

    @Description("Testing API: Payload with single value in 'licenseIds' field")
    @Test
    public void verifyTransferSingleLicenseTest() {
        prepareLicensesForTheTeam(LICENSE_LIST, TEAM002_ID_CODE);
        List<String> single_license_list = List.of(LICENSE_LIST.get(0));

        TeamLicenses teamLicenses = TeamLicenses.builder()
                .targetTeamId(TEAM001_ID_CODE)
                .licenseIds(single_license_list)
                .build();

        Response response = extractApiResponse(teamLicenses, LICENSE_CHANGE_ENDPOINT);

        assertResponseStatusCodeAndLicenseIds(response, CODE_200.CODE, single_license_list);
    }

    @Description("Testing API: Payload with multiple values in 'licenseIds' field")
    @Test
    public void verifyTransferMultipleLicensesTest() {
        prepareLicensesForTheTeam(LICENSE_LIST, TEAM002_ID_CODE);

        TeamLicenses teamLicenses = TeamLicenses.builder()
                .targetTeamId(TEAM001_ID_CODE)
                .licenseIds(LICENSE_LIST)
                .build();

        Response response = extractApiResponse(teamLicenses, LICENSE_CHANGE_ENDPOINT);

        assertResponseStatusCodeAndLicenseIds(response, CODE_200.CODE, LICENSE_LIST);
    }

    @Description("Testing API: Payload with empty list of values in 'licenseIds' field")
    @Test
    public void verifyTransferEmptyLicensesTest() {
        TeamLicenses teamLicenses = TeamLicenses.builder()
                .targetTeamId(TEAM001_ID_CODE)
                .licenseIds(LICENSE_EMPTY_LIST)
                .build();

        Response response = extractApiResponse(teamLicenses, LICENSE_CHANGE_ENDPOINT);

        assertResponseStatusCodeAndLicenseIds(response, CODE_200.CODE, LICENSE_EMPTY_LIST);
    }
}
