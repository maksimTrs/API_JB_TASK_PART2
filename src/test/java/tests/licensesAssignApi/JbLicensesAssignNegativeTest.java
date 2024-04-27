package tests.licensesAssignApi;


import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.licensesAssign.Contact;
import pojo.licensesAssign.License;
import pojo.licensesAssign.LicensesAssignObject;
import tests.BaseTest;

import static api.ApiConstants.*;
import static api.ResponseHelper.extractApiResponse;
import static api.ResponseHelper.extractApiResponseWithInvalidApiKeyCodeHeader;
import static enums.ApiResponseValuesEnum.*;
import static enums.StatusCodeEnum.*;
import static utils.AssertionHelper.*;
import static utils.AuthorCommentsToTests.*;
import static utils.PropertyReader.getPropertyFromBundle;


public class JbLicensesAssignNegativeTest extends BaseTest {

    private static final String Team002_LICENSE_3_ID = getPropertyFromBundle("WebStormLicenseID_Team002_3");
    private static final String Team002_LICENSE_4_ID = getPropertyFromBundle("WebStormLicenseID_Team002_4");
    private static final String Team002_INVALID_LICENSE_ID = getPropertyFromBundle("Invalid_LicenseID_Team002_1");

    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: validate expired licenseId")
    @Test()
    public void verifyExpiredLicenseTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .licenseId(Team002_INVALID_LICENSE_ID)
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, ASSIGN_LICENSE);

        assertResponseBody(response, CODE_400.CODE, LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN.getCode(),
                LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN.getDescription());
    }

    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: validate non-existent licenses for the team per product")
    @Test()
    public void verifyEmptyAvailableLicenseForTeamProductTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .license(License.builder()
                        .productCode(TEAM002_PRODUCT_CODE_WITHOUT_LICENSE)
                        .team(TEAM002_ID_CODE)
                        .build())
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, ASSIGN_LICENSE);

        assertResponseBody(response, CODE_400.CODE, NO_AVAILABLE_LICENSE_TO_ASSIGN.getCode(),
                getDescription(NO_AVAILABLE_LICENSE_TO_ASSIGN.getDescription(),
                        licenseApiModel.getLicense().getTeam(), licenseApiModel.getLicense().getProductCode()));
    }


    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: validate response for missed licenseId and license partitions")
    @Test()
    public void verifyResponseForMissedLicenseIdAndLicensePartitionsTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, ASSIGN_LICENSE);

        assertResponseBody(response, CODE_400.CODE, MISSING_FIELD.getCode(), MISSING_FIELD.getDescription());
    }


    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: validate response with invalid X-Api-Key header - 401 response")
    @Test()
    public void verifyResponse401ForInvalidXApiKeyHeaderTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .licenseId(Team002_INVALID_LICENSE_ID)
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponseWithInvalidApiKeyCodeHeader(licenseApiModel, ASSIGN_LICENSE,
                invalidApiKeyCodeRequestSpecification);

        assertResponseBody(response, CODE_401.CODE, INVALID_TOKEN.getCode(), INVALID_TOKEN.getDescription());
    }

    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: validate response with restricted X-Api-Key header - 403 response")
    @Test()
    public void verifyResponse403ForInvalidXApiKeyHeaderTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .licenseId(Team002_INVALID_LICENSE_ID)
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponseWithInvalidApiKeyCodeHeader(licenseApiModel, ASSIGN_LICENSE,
                restrictedApiKeyCodeRequestSpecification);

        assertResponseBody(response, CODE_403.CODE, TEAM_MISMATCH.getCode(), TEAM_MISMATCH.getDescription());
    }

    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: validate response with payload dummy team id")
    @Test()
    public void verifyResponse404WithInvalidTeamIdTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .license(License.builder()
                        .productCode(TEAM002_PRODUCT_CODE_WITHOUT_LICENSE)
                        .team(0)
                        .build())
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, ASSIGN_LICENSE);

        assertResponseBody(response, CODE_404.CODE, TEAM_NOT_FOUND.getCode(),
                getDescription(TEAM_NOT_FOUND.getDescription(),
                        licenseApiModel.getLicense().getTeam(), ""));
    }


    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: validate response without request contact partition")
    @Test()
    public void verifyResponseWithoutContactSectionTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .licenseId(Team002_INVALID_LICENSE_ID)
                .build();

        Response response = extractApiResponse(licenseApiModel, ASSIGN_LICENSE);


        /* I presume we hava an API design validation issue here - instead of JSON response structure as we have
         *  for another negative tests
         *  assertResponseBody(response, CODE_400.CODE, MISSING_FIELD.getCode(), MISSING_FIELD.getDescription());
         *  we are getting HTML response structure
         * */
        Allure.addAttachment("COMMENT FOR TEST", COMMENT_1);
        assertResponseStatusCodeAndContentType(response, CODE_400.CODE, "application/json");
    }


    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: validate response without request license.productCode field")
    @Test()
    public void verifyResponseWithoutLicenseProductCodeFieldTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .license(License.builder()
                        .productCode(null)
                        .team(TEAM002_ID_CODE)
                        .build())
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, ASSIGN_LICENSE);


        /* I presume we hava an API design validation issue here - instead of JSON response structure as we have
         *  for another negative tests
         *  assertResponseBody(response, CODE_400.CODE, MISSING_FIELD.getCode(), MISSING_FIELD.getDescription());
         *  we are getting HTML response structure
         * */
        Allure.addAttachment("COMMENT FOR TEST", COMMENT_1);
        assertResponseStatusCodeAndContentType(response, CODE_400.CODE, "application/json");
    }


    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: Payload without mandatory 'sendEmail' field")
    @Test()
    public void verifyResponseWithoutSendEmailPayloadFieldTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .licenseId(Team002_LICENSE_3_ID)
                .contact(Contact.builder()
                        .build())
                .sendEmail(null)
                .includeOfflineActivationCode(false)
                .build();

        Response response = extractApiResponse(licenseApiModel, ASSIGN_LICENSE);

        /* I presume we hava an API design  issue here - AssignLicenseRequest body schema validation
         *   requires to have "sendEmail" field: "sendEmail*" marked as mandatory with red star "*" symbol
         *   but API does not require to send "sendEmail" field - it is optional
         *   https://account.jetbrains.com/api-doc#/Licenses/assignLicense
         * */
        Allure.addAttachment("COMMENT FOR TEST", COMMENT_2);
        assertResponseStatusCodeAndEmptyBody(response, CODE_400.CODE);
    }

    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: Payload without mandatory 'includeOfflineActivationCode' field")
    @Test()
    public void verifyResponseWithoutIncludeOfflineActivationCodePayloadFieldTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .licenseId(Team002_LICENSE_4_ID)
                .contact(Contact.builder()
                        .build())
                .sendEmail(false)
                .includeOfflineActivationCode(null)
                .build();

        Response response = extractApiResponse(licenseApiModel, ASSIGN_LICENSE);

        /* I presume we hava an API design  issue here - AssignLicenseRequest body schema validation
         *   requires to have "includeOfflineActivationCode" field: "includeOfflineActivationCode*" marked as
         *   mandatory with red star "*" symbol
         *   but API does not require to send "includeOfflineActivationCode" field - it is optional
         *   https://account.jetbrains.com/api-doc#/Licenses/assignLicense
         * */
        Allure.addAttachment("COMMENT FOR TEST", COMMENT_3);
        assertResponseStatusCodeAndEmptyBody(response, CODE_400.CODE);
    }
}
