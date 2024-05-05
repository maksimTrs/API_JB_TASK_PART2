### **POST /api/v1/customer/licenses/assign, /api/v1/customer/changeLicensesTeam API TCs:**

[**jetbrains api**](https://account.jetbrains.com/api-doc#/)

---

##### To start tests via maven wrapper with TestNG on  WINDOWS, run commands:

`./mvnw -DsuiteXmlFile="AssignLicensesApiTests.xml" clean test`

`./mvnw -DsuiteXmlFile="ChangeLicensesTeamApiTests.xml" clean test`

`./mvnw -DsuiteXmlFile="RegressionApiTests.xml" clean test`  (all tests)

**OR**

##### To start tests via maven wrapper with TestNG  on MAC, run command:

`chmod +x mvnw`  (to add permissions to execute maven wrapper)

`./mvnw -DsuiteXmlFile='AssignLicensesApiTests.xml' clean test`

`./mvnw -DsuiteXmlFile='ChangeLicensesTeamApiTests.xml' clean test`

`./mvnw -DsuiteXmlFile='RegressionApiTests.xml' clean test`  (all tests)

#### NOTE: To start tests with different mandatory  Headers, add variables before maven 'clean test' command:

`-DTEAM_X_API_KEY_HEADER=TEAM_API_KEY -DX_API_KEY_HEADER=COMPANY_X_API_KEY -DX_CUSTOMER_CODE_HEADER=COMPANY_CODE`

---

### **To start allure report, run command:**

`./mvnw allure:serve`