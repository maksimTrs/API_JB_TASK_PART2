package tests.licensesAssignApi;


import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.licensesAssign.Contact;
import pojo.licensesAssign.License;
import pojo.licensesAssign.LicensesAssignObject;
import tests.BaseTest;

import static api.ApiConstants.*;
import static api.ApiResponseBuilder.extractApiResponse;
import static api.ApiResponseBuilder.extractApiResponseWithInvalidApiKeyCodeHeader;
import static enums.ApiResponseCodeAndDescriptionFieldsEnum.*;
import static enums.ApiStatusCodesEnum.*;
import static utils.AssertionHelper.*;
import static utils.AuthorCommentsForTests.*;
import static utils.PropertyReader.getPropertyFromBundle;


@Feature("API NEGATIVE TESTING: POST /api/v1/customer/licenses/assign")
@Story("Testing POST /api/v1/customer/licenses/assign")
public class JbLicensesAssignNegativeTest extends BaseTest {

    private static final String Team002_LICENSE_3_ID = getPropertyFromBundle("WebStormLicenseID_Team002_3");
    private static final String Team002_LICENSE_4_ID = getPropertyFromBundle("WebStormLicenseID_Team002_4");
    private static final String Team002_INVALID_LICENSE_ID = getPropertyFromBundle("Invalid_LicenseID_Team002_1");


    @Description("Testing API: validate response with incorrect 'contact.email'  field")
    @Test()
    public void verifyResponseWithInvalidEmailTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .licenseId(Team002_LICENSE_3_ID)
                .contact(Contact.builder()
                        .email("invalidEmail.com")
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        assertResponseStatusCodeWithCodeAndDescriptionValues(response, CODE_400.CODE, INVALID_CONTACT_EMAIL.getCode(),
                getFormatDescription(INVALID_CONTACT_EMAIL.getDescription(),
                        licenseApiModel.getContact().getEmail(), ""));
    }

    @Description("Testing API: validate expired 'licenseId' field")
    @Test()
    public void verifyExpiredLicenseIdFieldTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .licenseId(Team002_INVALID_LICENSE_ID)
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        assertResponseStatusCodeWithCodeAndDescriptionValues(response, CODE_400.CODE,
                LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN.getCode(),
                LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN.getDescription());
    }

    @Description("Testing API: validate non-existent licenses for the team per product")
    @Test()
    public void verifyEmptyAvailableLicensesForTeamProductTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .license(License.builder()
                        .productCode(TEAM002_PRODUCT_CODE_WITHOUT_LICENSE)
                        .team(TEAM002_ID_CODE)
                        .build())
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        assertResponseStatusCodeWithCodeAndDescriptionValues(response, CODE_400.CODE,
                NO_AVAILABLE_LICENSE_TO_ASSIGN.getCode(),
                getFormatDescription(NO_AVAILABLE_LICENSE_TO_ASSIGN.getDescription(),
                        String.valueOf(licenseApiModel.getLicense().getTeam()), licenseApiModel.getLicense().getProductCode()));
    }

    @Description("Testing API: validate response for missed 'licenseId' and 'license' partitions")
    @Test()
    public void verifyResponseForMissedLicenseIdAndLicensePartitionsTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        assertResponseStatusCodeWithCodeAndDescriptionValues(response, CODE_400.CODE, MISSING_FIELD.getCode(),
                MISSING_FIELD.getDescription());
    }

    @Description("Testing API: validate response with invalid X-Api-Key header - 401 response code")
    @Test()
    public void verifyResponse401ForInvalidXApiKeyHeaderTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .licenseId(Team002_INVALID_LICENSE_ID)
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponseWithInvalidApiKeyCodeHeader(licenseApiModel,
                LICENSE_ASSIGNMENT_ENDPOINT,
                invalidApiKeyCodeRequestSpecification);

        assertResponseStatusCodeWithCodeAndDescriptionValues(response, CODE_401.CODE, INVALID_TOKEN.getCode(),
                INVALID_TOKEN.getDescription());
    }

    @Description("Testing API: validate response with restricted X-Api-Key header - 403 response code")
    @Test()
    public void verifyResponse403ForInvalidXApiKeyHeaderTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .licenseId(Team002_INVALID_LICENSE_ID)
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponseWithInvalidApiKeyCodeHeader(licenseApiModel,
                LICENSE_ASSIGNMENT_ENDPOINT,
                restrictedApiKeyCodeRequestSpecification);

        assertResponseStatusCodeWithCodeAndDescriptionValues(response, CODE_403.CODE, TEAM_MISMATCH.getCode(),
                TEAM_MISMATCH.getDescription());
    }

    @Description("Testing API: validate response with payload dummy 'license.team' id field")
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

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        assertResponseStatusCodeWithCodeAndDescriptionValues(response, CODE_404.CODE, TEAM_NOT_FOUND.getCode(),
                getFormatDescription(TEAM_NOT_FOUND.getDescription(),
                        String.valueOf(licenseApiModel.getLicense().getTeam()), ""));
    }

    @Description("Testing API: validate response without 'contact' partition")
    @Test()
    public void verifyResponseWithoutContactSectionTest() {
        LicensesAssignObject licenseApiModel = LicensesAssignObject.builder()
                .licenseId(Team002_INVALID_LICENSE_ID)
                .build();

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        /* I presume we have an API design validation issue here - instead of JSON response structure as we have
         *  for another negative tests:
         *  assertResponseBody(response, CODE_400.CODE, MISSING_FIELD.getCode(), MISSING_FIELD.getDescription());
         *  we are getting HTML response structure
         * */
        Allure.addAttachment("COMMENT FOR TEST:", COMMENT_1);
        assertResponseStatusCodeWithContentTypeValue(response, CODE_400.CODE, "application/json");
    }

    @Description("Testing API: validate response without request 'license.productCode' field")
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

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        /* I presume we have an API design validation issue here - instead of JSON response structure as we have
         *  for another negative tests:
         *  assertResponseBody(response, CODE_400.CODE, MISSING_FIELD.getCode(), MISSING_FIELD.getDescription());
         *  we are getting HTML response structure
         * */
        Allure.addAttachment("COMMENT FOR TEST:", COMMENT_1);
        assertResponseStatusCodeWithContentTypeValue(response, CODE_400.CODE, "application/json");
    }

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

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        /* I presume we have an API design  issue here - AssignLicenseRequest body schema validation
         *   requires to have "sendEmail" field: "sendEmail*" marked as mandatory with red star "*" symbol
         *   but API does not require to send "sendEmail" field - it is optional
         *
         *   API DOC: https://account.jetbrains.com/api-doc#/Licenses/assignLicense
         * */
        Allure.addAttachment("COMMENT FOR TEST:", COMMENT_2);
        assertResponseStatusCodeAndEmptyBody(response, CODE_400.CODE);
    }

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

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        /* I presume we have an API design  issue here - AssignLicenseRequest body schema validation
         *   requires to have "includeOfflineActivationCode" field: "includeOfflineActivationCode*" marked as
         *   mandatory with red star "*" symbol
         *   but API does not require to send "includeOfflineActivationCode" field - it is optional
         *
         *   API DOC: https://account.jetbrains.com/api-doc#/Licenses/assignLicense
         * */
        Allure.addAttachment("COMMENT FOR TEST:", COMMENT_3);
        assertResponseStatusCodeAndEmptyBody(response, CODE_400.CODE);
    }
}
