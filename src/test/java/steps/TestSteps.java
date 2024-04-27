package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.changeLicensesTeam.ChangeLicensesTeamObject;

import java.util.List;

import static api.ApiConstants.CHANGE_LICENSE;
import static api.ApiConstants.TEAM002_ID_CODE;
import static api.ResponseHelper.extractApiResponse;
import static enums.StatusCodeEnum.CODE_200;
import static org.assertj.core.api.Assertions.assertThat;

public class TestSteps {

    @Step("Prepare licenses for the team")
    public  static void prepareLicensesForTheTeam(List<String> licenseList) {
        ChangeLicensesTeamObject changeLicensesTeamObject = ChangeLicensesTeamObject.builder()
                .targetTeamId(TEAM002_ID_CODE)
                .licenseIds(licenseList)
                .build();

        Response response = extractApiResponse(changeLicensesTeamObject, CHANGE_LICENSE);
        assertThat(response.statusCode()).isEqualTo(CODE_200.CODE);
    }

}
