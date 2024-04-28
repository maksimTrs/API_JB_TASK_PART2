### **POST /api/v1/customer/licenses/assign, /api/v1/customer/changeLicensesTeam API TCs:**

---

##### To start tests via maven wrapper with TestNG on  WINDOWS, run commands:

`./mvnw -DsuiteXmlFile="AssignLicensesApiTests.xml" clean test`


`./mvnw -DsuiteXmlFile="ChangeLicensesTeamApiTests.xml" clean test`


`./mvnw -DsuiteXmlFile="RegressionApiTests.xml" clean test`  (all tests)

**OR**

##### To start tests via maven wrapper with TestNG  on MAC, run command:

`./mvnw -DsuiteXmlFile='AssignLicensesApiTests.xml' clean test`


`./mvnw -DsuiteXmlFile='ChangeLicensesTeamApiTests.xml' clean test`


`./mvnw -DsuiteXmlFile='RegressionApiTests.xml' clean test`  (all tests)



#### NOTE: To start tests with different mandatory  Headers, add variables before maven 'clean test' command:

`-DTEAM_X_API_KEY=TEAM_API_KEY -DX_API_KEY=COMPANY_X_API_KEY -DX_CUSTOMER_CODE=COMPANY_CODE`

---

### **To start allure report, run command:**

`mvn allure:serve`