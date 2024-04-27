package tests.changeLicensesTeamApi;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojo.changeLicensesTeam.ChangeLicensesTeamObject;
import tests.BaseTest;

import java.util.Arrays;
import java.util.List;

import static api.ApiConstants.*;
import static api.ResponseHelper.extractApiResponse;
import static enums.StatusCodeEnum.CODE_200;
import static org.assertj.core.api.Assertions.assertThat;
import static steps.TestSteps.prepareLicensesForTheTeam;
import static utils.AssertionHelper.assertLicenseIdsResponseBody;
import static utils.PropertyReader.getLicenseListFromBundle;

public class JbChangeLicensesTeamPositiveTest extends BaseTest {

    private static final List<String> LICENSE_LIST = getLicenseListFromBundle("DataGripLicenseListIDs");
    private static final String FIRST_LICENSE = LICENSE_LIST.get(0);
    private final List<String> LICENSE_EMPTY_LIST = List.of();

    @BeforeMethod
    private void beforeTestMethod() {
        prepareLicensesForTheTeam(LICENSE_LIST);
    }

    @Story("Testing POST api/v1/customer/changeLicensesTeam")
    @Description("Testing API: Payload with single license id")
    @Test()
    public void verifyChangeSingleLicenseTest() {
      //  prepareLicensesForTheTeam(LICENSE_LIST);

        ChangeLicensesTeamObject changeLicensesTeamObject = ChangeLicensesTeamObject.builder()
                .targetTeamId(TEAM001_ID_CODE)
                .licenseIds(List.of(FIRST_LICENSE))
                .build();

        Response response = extractApiResponse(changeLicensesTeamObject, CHANGE_LICENSE);
        assertLicenseIdsResponseBody(response, CODE_200.CODE, List.of(FIRST_LICENSE));
    }

    @Story("Testing POST api/v1/customer/changeLicensesTeam")
    @Description("Testing API: Payload with multiple license ids")
    @Test()
    public void verifyChangeMultipleLicensesTest() {
     //   prepareLicensesForTheTeam(LICENSE_LIST);

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
      //  prepareLicensesForTheTeam(LICENSE_LIST);

        ChangeLicensesTeamObject changeLicensesTeamObject = ChangeLicensesTeamObject.builder()
                .targetTeamId(TEAM001_ID_CODE)
                .licenseIds(LICENSE_EMPTY_LIST)
                .build();

        Response response = extractApiResponse(changeLicensesTeamObject, CHANGE_LICENSE);
        assertLicenseIdsResponseBody(response, CODE_200.CODE, LICENSE_EMPTY_LIST);
    }

}
