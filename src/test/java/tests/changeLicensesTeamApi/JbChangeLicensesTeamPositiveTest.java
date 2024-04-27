package tests.changeLicensesTeamApi;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojo.changeLicensesTeam.ChangeLicensesTeamObject;
import tests.BaseTest;

import java.util.List;

import static api.ApiConstants.CHANGE_LICENSE;
import static api.ApiConstants.TEAM001_ID_CODE;
import static api.ResponseHelper.extractApiResponse;
import static enums.StatusCodeEnum.CODE_200;
import static steps.TestSteps.prepareLicensesForTheTeam;
import static utils.AssertionHelper.assertLicenseIdsResponseBody;
import static utils.PropertyReader.getLicenseListFromBundle;

public class JbChangeLicensesTeamPositiveTest extends BaseTest {

    private static final List<String> LICENSE_LIST = getLicenseListFromBundle("DataGripLicenseListIDs");
    private static final List<String> FIRST_LICENSE = List.of(LICENSE_LIST.get(0));
    private final List<String> LICENSE_EMPTY_LIST = List.of();


    @BeforeMethod
    private void beforeTestMethod() {
        prepareLicensesForTheTeam(LICENSE_LIST);
    }


    @Story("Testing POST api/v1/customer/changeLicensesTeam")
    @Description("Testing API: Payload with single license id")
    @Test()
    public void verifyChangeSingleLicenseTest() {
        ChangeLicensesTeamObject changeLicensesTeamObject = ChangeLicensesTeamObject.builder()
                .targetTeamId(TEAM001_ID_CODE)
                .licenseIds(FIRST_LICENSE)
                .build();

        Response response = extractApiResponse(changeLicensesTeamObject, CHANGE_LICENSE);
        assertLicenseIdsResponseBody(response, CODE_200.CODE, FIRST_LICENSE);
    }

    @Story("Testing POST api/v1/customer/changeLicensesTeam")
    @Description("Testing API: Payload with multiple license ids")
    @Test()
    public void verifyChangeMultipleLicensesTest() {
        ChangeLicensesTeamObject changeLicensesTeamObject = ChangeLicensesTeamObject.builder()
                .targetTeamId(TEAM001_ID_CODE)
                .licenseIds(LICENSE_LIST)
                .build();

        Response response = extractApiResponse(changeLicensesTeamObject, CHANGE_LICENSE);
        assertLicenseIdsResponseBody(response, CODE_200.CODE, LICENSE_LIST);
    }


    @Story("Testing POST api/v1/customer/changeLicensesTeam")
    @Description("Testing API: Payload with empty list of the license ids")
    @Test()
    public void verifyChangeEmptyLicensesTest() {
        ChangeLicensesTeamObject changeLicensesTeamObject = ChangeLicensesTeamObject.builder()
                .targetTeamId(TEAM001_ID_CODE)
                .licenseIds(LICENSE_EMPTY_LIST)
                .build();

        Response response = extractApiResponse(changeLicensesTeamObject, CHANGE_LICENSE);
        assertLicenseIdsResponseBody(response, CODE_200.CODE, LICENSE_EMPTY_LIST);
    }
}
