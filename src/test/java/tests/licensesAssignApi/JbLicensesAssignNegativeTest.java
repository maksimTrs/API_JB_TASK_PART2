package tests.licensesAssignApi;


import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.licensesAssign.Contact;
import pojo.licensesAssign.License;
import pojo.licensesAssign.LicensesAssignObject;
import tests.BaseTest;

import static org.assertj.core.api.Assertions.assertThat;
import static api.ApiConstants.*;
import static utils.AssertionHelper.assertResponseBody;
import static utils.AssertionHelper.assertResponseStatusCodeAndEmptyBody;
import static api.ResponseHelper.*;
import static enums.StatusCodeEnum.*;


public class JbLicensesAssignNegativeTest extends BaseTest {


    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: validate expired licenseId")
    @Test()
    public void verifyExpiredLicenseTest() {

        LicensesAssignObject license = LicensesAssignObject.builder()
                .licenseId(EXPIRED_LICENSE_ID)
                .license(License.builder()
                        .productCode("ALL")
                        .team(1)
                        .build())
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(license, ASSIGN_LICENSE);

        assertResponseBody(response, CODE_400.CODE, CODE_MSG, DESCRIPTION_MSG);
    }

    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: validate empty licences for team product")
    @Test()
    public void verifyEmptyAvailableLicenseForTeamProductTest() {
        LicensesAssignObject license = LicensesAssignObject.builder()
                .license(License.builder()
                        .productCode(LICENSE_PRODUCT_CODE)
                        .team(1)
                        .build())
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(license, ASSIGN_LICENSE);

        String testDescriptionMsg = String.format(DESCRIPTION_MSG2, license.getLicense().getTeam(),
                license.getLicense().getProductCode());
        assertResponseBody(response, CODE_400.CODE, CODE_MSG2, testDescriptionMsg);
    }


    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: validate response for missed  licenseId and license partitions")
    @Test()
    public void verifyResponseForMissedLicenseIdAndLicensePartitionsTest() {
        LicensesAssignObject license = LicensesAssignObject.builder()
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(license, ASSIGN_LICENSE);

        assertResponseBody(response, CODE_400.CODE, CODE_MSG3, DESCRIPTION_MSG3);
    }



    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: validate response with invalid X-Api-Key header - 401 response")
    @Test()
    public void verifyResponse401ForInvalidXApiKeyHeaderTest() {
        LicensesAssignObject license = LicensesAssignObject.builder()
                .licenseId(EXPIRED_LICENSE_ID)
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponseWithInvalidApiKeyCodeHeader(license, ASSIGN_LICENSE,
                invalidApiKeyCodeRequestSpecification);

        assertResponseBody(response, CODE_401.CODE, CODE_MSG4, DESCRIPTION_MSG4);
    }

    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: validate response with restricted X-Api-Key header - 403 response")
    @Test()
    public void verifyResponse403ForInvalidXApiKeyHeaderTest() {
        LicensesAssignObject license = LicensesAssignObject.builder()
                .licenseId(EXPIRED_LICENSE_ID)
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponseWithInvalidApiKeyCodeHeader(license, ASSIGN_LICENSE,
                restrictedApiKeyCodeRequestSpecification);

        assertResponseBody(response, CODE_403.CODE, CODE_MSG5, DESCRIPTION_MSG5);
    }



    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: validate response without request contact partition")
    @Test()
    public void verifyResponseWithoutContactSectionTest() {
        LicensesAssignObject license = LicensesAssignObject.builder()
                .licenseId(EXPIRED_LICENSE_ID)
                .license(License.builder()
                        .productCode(LICENSE_PRODUCT_CODE)
                        .team(1)
                        .build())
                .build();

        Response response = extractApiResponse(license, ASSIGN_LICENSE);

        assertThat(response.statusCode()).isEqualTo(CODE_400.CODE);
        assertThat(response.getHeader("Content-Type")).contains("application/json");
    }
}
