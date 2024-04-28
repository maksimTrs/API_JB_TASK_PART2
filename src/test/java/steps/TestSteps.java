package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.changeLicensesTeam.ChangeLicensesTeamObject;

import java.util.List;

import static api.ApiConstants.LICENSE_CHANGE_ENDPOINT;
import static api.ApiResponseBuilder.extractApiResponse;
import static enums.ApiStatusCodesEnum.CODE_200;
import static org.assertj.core.api.Assertions.assertThat;

public class TestSteps {

    @Step("Prepare team licenses: {licenseList}")
    public static void prepareLicensesForTheTeam(List<String> licenseList, int teamId) {
        ChangeLicensesTeamObject changeLicensesTeamObject = ChangeLicensesTeamObject.builder()
                .targetTeamId(teamId)
                .licenseIds(licenseList)
                .build();

        Response response = extractApiResponse(changeLicensesTeamObject, LICENSE_CHANGE_ENDPOINT);

        assertThat(response.statusCode()).isEqualTo(CODE_200.CODE);
    }
}
