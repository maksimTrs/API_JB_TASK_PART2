package tests.licensesAssignApi;


import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.licensesAssign.Contact;
import pojo.licensesAssign.License;
import pojo.licensesAssign.UserLicense;
import tests.BaseTest;

import static api.ApiConstants.*;
import static api.ApiResponseBuilder.extractApiResponse;
import static api.ApiResponseBuilder.extractApiResponseWithInvalidApiKeyCodeHeader;
import static enums.ApiErrorResponseCodeAndDescriptionFieldsEnum.*;
import static enums.ApiStatusCodesEnum.*;
import static utils.AssertionHelper.*;
import static utils.AuthorCommentsForTests.*;
import static utils.PropertyReader.getPropertyFromBundle;


@Feature("API NEGATIVE TESTING: POST /api/v1/customer/licenses/assign")
@Story("Testing POST /api/v1/customer/licenses/assign")
public class JbLicensesAssignNegativeTest extends BaseTest {

    private static final String Team002_LICENSE_3_ID = getPropertyFromBundle("WebStormLicenseID_Team002_3");
    private static final String Team002_LICENSE_4_ID = getPropertyFromBundle("WebStormLicenseID_Team002_4");
    private static final String Team002_EXPIRED_LICENSE_ID = getPropertyFromBundle("Invalid_LicenseID_Team002_1");


    @Description("Testing API: validate response with incorrect 'contact.email'  field")
    @Test
    public void verifyAssignLicenseWithInvalidEmailTest() {
        UserLicense licenseApiModel = UserLicense.builder()
                .licenseId(Team002_LICENSE_3_ID)
                .contact(Contact.builder()
                        .email("invalidEmail.com")
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        assertResponseStatusCodeAndResponseBody(response, CODE_400.CODE, INVALID_CONTACT_EMAIL_RESPONSE.getCode(),
                getFormatDescription(INVALID_CONTACT_EMAIL_RESPONSE.getDescription(),
                        licenseApiModel.getContact().getEmail(), ""));
    }

    @Description("Testing API: validate expired 'licenseId' field")
    @Test
    public void verifyAssignLicenseExpiredLicenseIdFieldTest() {
        UserLicense licenseApiModel = UserLicense.builder()
                .licenseId(Team002_EXPIRED_LICENSE_ID)
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        assertResponseStatusCodeAndResponseBody(response, CODE_400.CODE,
                LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN_RESPONSE.getCode(),
                LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN_RESPONSE.getDescription());
    }

    @Description("Testing API: validate non-existent licenses for the team per product")
    @Test
    public void verifyAssignLicenseEmptyAvailableLicensesForTeamProductTest() {
        UserLicense licenseApiModel = UserLicense.builder()
                .license(License.builder()
                        .productCode(TEAM002_PRODUCT_CODE_WITHOUT_LICENSE)
                        .team(TEAM002_ID_CODE)
                        .build())
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        assertResponseStatusCodeAndResponseBody(response, CODE_400.CODE,
                NO_AVAILABLE_LICENSE_TO_ASSIGN_RESPONSE.getCode(),
                getFormatDescription(NO_AVAILABLE_LICENSE_TO_ASSIGN_RESPONSE.getDescription(),
                        String.valueOf(licenseApiModel.getLicense().getTeam()), licenseApiModel.getLicense().getProductCode()));
    }

    @Description("Testing API: validate response for missed 'licenseId' and 'license' partitions")
    @Test
    public void verifyAssignLicenseForMissedLicenseIdAndLicensePartitionsTest() {
        UserLicense licenseApiModel = UserLicense.builder()
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        assertResponseStatusCodeAndResponseBody(response, CODE_400.CODE, MISSING_FIELD_RESPONSE.getCode(),
                MISSING_FIELD_RESPONSE.getDescription());
    }

    @Description("Testing API: validate response with invalid X-Api-Key header - 401 response code")
    @Test
    public void verifyAssignLicense401StatusForInvalidXApiKeyHeaderTest() {
        UserLicense licenseApiModel = UserLicense.builder()
                .licenseId(Team002_EXPIRED_LICENSE_ID)
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponseWithInvalidApiKeyCodeHeader(licenseApiModel,
                LICENSE_ASSIGNMENT_ENDPOINT,
                invalidApiKeyCodeRequestSpecification);

        assertResponseStatusCodeAndResponseBody(response, CODE_401.CODE, INVALID_TOKEN_RESPONSE.getCode(),
                INVALID_TOKEN_RESPONSE.getDescription());
    }

    @Description("Testing API: validate response with restricted X-Api-Key header - 403 response code")
    @Test
    public void verifyAssignLicenseStatus403ForInvalidXApiKeyHeaderTest() {
        UserLicense licenseApiModel = UserLicense.builder()
                .licenseId(Team002_EXPIRED_LICENSE_ID)
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponseWithInvalidApiKeyCodeHeader(licenseApiModel,
                LICENSE_ASSIGNMENT_ENDPOINT,
                restrictedApiKeyCodeRequestSpecification);

        assertResponseStatusCodeAndResponseBody(response, CODE_403.CODE, TEAM_MISMATCH_RESPONSE.getCode(),
                TEAM_MISMATCH_RESPONSE.getDescription());
    }

    @Description("Testing API: validate response with payload dummy 'license.team' id field - 404 response code")
    @Test
    public void verifyAssignLicenseStatus404WithInvalidTeamIdTest() {
        int invalidTeamId = 0;
        UserLicense licenseApiModel = UserLicense.builder()
                .license(License.builder()
                        .productCode(TEAM002_PRODUCT_CODE_WITHOUT_LICENSE)
                        .team(invalidTeamId)
                        .build())
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        assertResponseStatusCodeAndResponseBody(response, CODE_404.CODE, TEAM_NOT_FOUND_RESPONSE.getCode(),
                getFormatDescription(TEAM_NOT_FOUND_RESPONSE.getDescription(),
                        String.valueOf(licenseApiModel.getLicense().getTeam()), ""));
    }

    @Description("Testing API: validate response without 'contact' mandatory partition")
    @Test
    public void verifyResponseWithoutContactSectionTest() {
        UserLicense licenseApiModel = UserLicense.builder()
                .licenseId(Team002_EXPIRED_LICENSE_ID)
                .build();

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        /* I presume we have an API design validation issue here - instead of JSON response structure as we have
         *  for another negative tests:
         *  assertResponseBody(response, CODE_400.CODE, MISSING_FIELD.getCode(), MISSING_FIELD.getDescription());
         *  we are getting HTML response structure
         * */
        Allure.addAttachment("COMMENT FOR TEST:", COMMENT_1);
        assertResponseStatusCodeAndContentType(response, CODE_400.CODE, "application/json");
    }

    @Description("Testing API: validate response without mandatory 'license.productCode' field")
    @Test
    public void verifyResponseWithoutLicenseProductCodeFieldTest() {
        UserLicense licenseApiModel = UserLicense.builder()
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
        assertResponseStatusCodeAndContentType(response, CODE_400.CODE, "application/json");
    }

    @Description("Testing API: Payload without mandatory 'sendEmail' field")
    @Test
    public void verifyResponseWithoutSendEmailPayloadFieldTest() {
        UserLicense licenseApiModel = UserLicense.builder()
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
    @Test
    public void verifyResponseWithoutIncludeOfflineActivationCodePayloadFieldTest() {
        UserLicense licenseApiModel = UserLicense.builder()
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
