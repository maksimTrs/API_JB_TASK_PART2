package api;

import static utils.PropertyReader.getPropertyFromBundle;

public final class ApiConstants {

    public static final String X_CUSTOMER_CODE;
    public static final String X_API_KEY;
    public static final String TEAM_X_API_KEY;
    public static final String HOST = "https://account.jetbrains.com";
    public static final String SERVICE = "/api/v1/customer";
    public static final String ASSIGN_LICENSE = "/licenses/assign";
    public static final String CHANGE_LICENSE = "/changeLicensesTeam";
    public static final int TEAM001_ID_CODE = Integer.parseInt(getPropertyFromBundle("Team001_ID"));
    public static final int TEAM002_ID_CODE = Integer.parseInt(getPropertyFromBundle("Team002_ID"));
    ;
    public static final String TEAM001_ACTIVE_PRODUCT_CODE = "WS";
    ;
    public static final String TEAM002_PRODUCT_CODE_WITHOUT_LICENSE = "SPE";  // "Space Cloud Enterprise"

    static {
        String customerCode = System.getProperty("X_CUSTOMER_CODE");
        String apiKey = System.getProperty("X_API_KEY");
        String apiKeyTeam = System.getProperty("TEAM_X_API_KEY");
        if (customerCode == null || customerCode.isBlank()) {
            customerCode = getPropertyFromBundle("X_CUSTOMER_CODE");
        }
        if (apiKey == null || apiKey.isBlank()) {
            apiKey = getPropertyFromBundle("X_API_KEY");
        }
        if (apiKeyTeam == null || apiKeyTeam.isBlank()) {
            apiKeyTeam = getPropertyFromBundle("TEAM_X_API_KEY");
        }
        X_CUSTOMER_CODE = customerCode;
        X_API_KEY = apiKey;
        TEAM_X_API_KEY = apiKeyTeam;
    }
}
