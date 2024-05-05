package tests.licensesAssignApi;


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
import static enums.ApiStatusCodesEnum.CODE_200;
import static utils.AssertionHelper.assertResponseStatusCodeAndEmptyBody;
import static utils.PropertyReader.getPropertyFromBundle;


@Feature("API POSITIVE TESTING: POST /api/v1/customer/licenses/assign")
@Story("Testing POST /api/v1/customer/licenses/assign")
public class JbLicensesAssignPositiveTest extends BaseTest {

    private static final String Team002_LICENSE_1_ID = getPropertyFromBundle("WebStormLicenseID_Team002_1");
    private static final String Team002_LICENSE_2_ID = getPropertyFromBundle("WebStormLicenseID_Team002_2");

    @Description("Testing API: Payload with 'licenseId' and without 'license' partitions")
    @Test
    public void verifyAssignLicenseWithoutLicensePartitionTest() {
        UserLicense licenseApiModel = UserLicense.builder()
                .licenseId(Team002_LICENSE_1_ID)
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        assertResponseStatusCodeAndEmptyBody(response, CODE_200.CODE);
    }

    @Description("Testing API: Payload without 'licenseId' and with 'license' partitions")
    @Test
    public void verifyAssignLicenseWithoutLicenseIdFieldTest() {
        UserLicense licenseApiModel = UserLicense.builder()
                .license(License.builder()
                        .productCode(TEAM001_ACTIVE_PRODUCT_CODE)
                        .team(TEAM001_ID_CODE)
                        .build())
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        assertResponseStatusCodeAndEmptyBody(response, CODE_200.CODE);
    }

    @Description("Testing API: Payload with 'licenseId' and with 'license' partitions")
    @Test
    public void verifyAssignLicenseWithLicenseIdAndLicensePartitionsTest() {
        UserLicense licenseApiModel = UserLicense.builder()
                .licenseId(Team002_LICENSE_2_ID)
                .license(License.builder()
                        .productCode(TEAM001_ACTIVE_PRODUCT_CODE)
                        .team(TEAM001_ID_CODE)
                        .build())
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(licenseApiModel, LICENSE_ASSIGNMENT_ENDPOINT);

        assertResponseStatusCodeAndEmptyBody(response, CODE_200.CODE);
    }
}
