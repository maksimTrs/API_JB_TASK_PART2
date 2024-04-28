package tests.changeLicensesTeamApi;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojo.changeLicensesTeam.ChangeLicensesTeamObject;
import tests.BaseTest;

import java.util.List;

import static api.ApiConstants.*;
import static api.ApiResponseBuilder.extractApiResponse;
import static enums.ApiStatusCodesEnum.CODE_200;
import static steps.TestSteps.prepareLicensesForTheTeam;
import static utils.AssertionHelper.assertResponseStatusCodeWithLicenseIdsValues;
import static utils.PropertyReader.getLicenseListFromBundle;

@Feature("API POSITIVE TESTING: POST api/v1/customer/changeLicensesTeam")
@Story("Testing POST api/v1/customer/changeLicensesTeam")
public class JbChangeLicensesTeamPositiveTest extends BaseTest {

    private static final List<String> LICENSE_LIST = getLicenseListFromBundle("DataGripLicenseListIDs");
    private static final List<String> FIRST_LICENSE = List.of(LICENSE_LIST.get(0));
    private final List<String> LICENSE_EMPTY_LIST = List.of();

    @BeforeMethod
    private void beforeTestMethod() {
        prepareLicensesForTheTeam(LICENSE_LIST, TEAM002_ID_CODE);
    }

    @Description("Testing API: Payload with single value in 'licenseIds' field")
    @Test()
    public void verifySingleLicenseTest() {
        ChangeLicensesTeamObject changeLicensesTeamObject = ChangeLicensesTeamObject.builder()
                .targetTeamId(TEAM001_ID_CODE)
                .licenseIds(FIRST_LICENSE)
                .build();

        Response response = extractApiResponse(changeLicensesTeamObject, LICENSE_CHANGE_ENDPOINT);

        assertResponseStatusCodeWithLicenseIdsValues(response, CODE_200.CODE, FIRST_LICENSE);
    }

    @Description("Testing API: Payload with multiple values in 'licenseIds' field")
    @Test()
    public void verifyMultipleLicensesTest() {
        ChangeLicensesTeamObject changeLicensesTeamObject = ChangeLicensesTeamObject.builder()
                .targetTeamId(TEAM001_ID_CODE)
                .licenseIds(LICENSE_LIST)
                .build();

        Response response = extractApiResponse(changeLicensesTeamObject, LICENSE_CHANGE_ENDPOINT);

        assertResponseStatusCodeWithLicenseIdsValues(response, CODE_200.CODE, LICENSE_LIST);
    }

    @Description("Testing API: Payload with empty list of values in 'licenseIds' field")
    @Test()
    public void verifyEmptyLicensesTest() {
        ChangeLicensesTeamObject changeLicensesTeamObject = ChangeLicensesTeamObject.builder()
                .targetTeamId(TEAM001_ID_CODE)
                .licenseIds(LICENSE_EMPTY_LIST)
                .build();

        Response response = extractApiResponse(changeLicensesTeamObject, LICENSE_CHANGE_ENDPOINT);

        assertResponseStatusCodeWithLicenseIdsValues(response, CODE_200.CODE, LICENSE_EMPTY_LIST);
    }
}
