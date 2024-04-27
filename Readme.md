### **POST /api/v1/customer/licenses/assign API TCs:**

---

##### To start tests via maven wrapper with TestNG on  WINDOWS, run command:

`./mvnw -DsuiteXmlFile="AssignLicenseRequestApiTests.xml"  clean test`

**OR**

##### To start tests via maven wrapper with TestNG  on MAC, run command:

`./mvnw -DsuiteXmlFile='AssignLicenseRequestApiTests.xml'  clean test`

##### To start tests with mandatory optional Headers, add variables before maven 'clean test' command:

`-DTEAM_X_API_KEY=TEAM_API_KEY -DX_API_KEY=COMPANY_X_API_KEY -DX_CUSTOMER_CODE=COMPANY_CODE`

---

### **To start allure report, run command:**

`mvn allure:serve`