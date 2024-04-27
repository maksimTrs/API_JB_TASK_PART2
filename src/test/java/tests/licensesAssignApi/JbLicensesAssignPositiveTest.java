package tests.licensesAssignApi;


import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.Response;

import org.testng.annotations.*;
import pojo.licensesAssign.Contact;
import pojo.licensesAssign.License;
import pojo.licensesAssign.LicensesAssignObject;
import tests.BaseTest;


import static api.ApiConstants.*;
import static utils.AssertionHelper.assertResponseStatusCodeAndEmptyBody;
import static api.ResponseHelper.extractApiResponse;
import static enums.StatusCodeEnum.CODE_200;
import static utils.PropertyReader.getLicenseFromBundle;


public class JbLicensesAssignPositiveTest extends BaseTest {

    private static final String Team002_LICENSE_1_ID = getLicenseFromBundle("WebStormLicenseID_Team002_1");
    private static final String Team002_LICENSE_2_ID = getLicenseFromBundle("WebStormLicenseID_Team002_2");


    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: Payload with 'licenseId' and without 'license' partitions")
    @Test()
    public void verifyAssignLicenseApiWithoutLicenseSectionTest() {
        LicensesAssignObject license = LicensesAssignObject.builder()
                .licenseId(Team002_LICENSE_1_ID)
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(license, ASSIGN_LICENSE);

        assertResponseStatusCodeAndEmptyBody(response, CODE_200.CODE);
    }


    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: Payload without 'licenseId' and with 'license' partitions")
    @Test()
    public void verifyAssignLicenseApiWithoutLicenseIdFieldTest() {

        LicensesAssignObject license = LicensesAssignObject.builder()
                .license(License.builder()
                        .productCode(TEAM001_ACTIVE_PRODUCT_CODE)
                        .team(TEAM001_ID_CODE)
                        .build())
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(license, ASSIGN_LICENSE);

        assertResponseStatusCodeAndEmptyBody(response, CODE_200.CODE);
    }


    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API: Payload with 'licenseId' and with 'license' partitions")
    @Test()
    public void verifyAssignLicenseApiWithLicenseIdAndLicenseSectionsTest() {

        LicensesAssignObject license = LicensesAssignObject.builder()
                .licenseId(Team002_LICENSE_2_ID)
                .license(License.builder()
                        .productCode(TEAM001_ACTIVE_PRODUCT_CODE)
                        .team(TEAM001_ID_CODE)
                        .build())
                .contact(Contact.builder()
                        .build())
                .build();

        Response response = extractApiResponse(license, ASSIGN_LICENSE);

        assertResponseStatusCodeAndEmptyBody(response, CODE_200.CODE);
    }
}
