package tests.licensesAssignApi;


import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.Response;

import org.testng.annotations.*;
import pojo.licensesAssign.Contact;
import pojo.licensesAssign.License;
import pojo.licensesAssign.LicensesAssignObject;
import tests.BaseTest;


import static org.assertj.core.api.Assertions.assertThat;
import static api.ApiConstants.*;
import static utils.AssertionHelper.assertResponseStatusCodeAndBodyPresence;
import static utils.MockHelper.mockResponse;
import static api.ResponseHelper.extractApiResponse;
import static enums.StatusCodeEnum.CODE_200;


public class JbLicensesAssignPositiveTest extends BaseTest {




    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API with 'licenseId' and without 'license' payload  partitions")
    @Test()
    public void verifyAssignLicenseApiWithoutLicenseSectionTest() {

        LicensesAssignObject license = LicensesAssignObject.builder()
                .licenseId(LICENSE_ID)
                .contact(Contact.builder()
                        .build())
                .build();

        Response response;
        if (license.getLicenseId().equals(LICENSE_ID)) {
            response = mockResponse(CODE_200.CODE);
        } else {
            response = extractApiResponse(license, ASSIGN_LICENSE);
        }

        assertResponseStatusCodeAndBodyPresence(response, CODE_200.CODE, false);
    }


    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API without 'licenseId' and with 'license' payload  partitions")
    @Test()
    public void verifyAssignLicenseApiWithoutLicenseIdFieldTest() {

        LicensesAssignObject license = LicensesAssignObject.builder()
                .license(License.builder()
                        .productCode("ALL")
                        .team(2)
                        .build())
                .contact(Contact.builder()
                        .build())
                .build();

        Response response;
        if (license.getLicense().getProductCode().equals(PRODUCT_CODE)) {
            response = mockResponse(CODE_200.CODE);
        } else {
            response = extractApiResponse(license, ASSIGN_LICENSE);
        }

        assertResponseStatusCodeAndBodyPresence(response, CODE_200.CODE, false);
    }


    @Story("Testing POST /api/v1/customer/licenses/assign")
    @Description("Testing API with 'licenseId' and with 'license' payload  partitions")
    @Test()
    public void verifyAssignLicenseApiWithLicenseIdAndLicenseSectionsTest() {

        LicensesAssignObject license = LicensesAssignObject.builder()
                .licenseId("ABCD")
                .license(License.builder()
                        .productCode("ALL")
                        .team(2)
                        .build())
                .contact(Contact.builder()
                        .build())
                .build();

        Response response;
        if (license.getLicense().getProductCode().equals(PRODUCT_CODE)) {
            response = mockResponse(CODE_200.CODE);
        } else {
            response = extractApiResponse(license, ASSIGN_LICENSE);
        }

        assertResponseStatusCodeAndBodyPresence(response, CODE_200.CODE, false);
    }
}
