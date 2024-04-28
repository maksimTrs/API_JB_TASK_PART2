package api;

import static utils.PropertyReader.getPropertyFromBundle;

public final class ApiConstants {

    public static final String X_CUSTOMER_CODE_HEADER;
    public static final String X_API_KEY_HEADER;
    public static final String TEAM_X_API_KEY_HEADER;
    public static final String HOST_URL = "https://account.jetbrains.com";
    public static final String CUSTOMER_API_ENDPOINT = "/api/v1/customer";
    public static final String LICENSE_ASSIGNMENT_ENDPOINT = "/licenses/assign";
    public static final String LICENSE_CHANGE_ENDPOINT = "/changeLicensesTeam";
    public static final int TEAM001_ID_CODE = Integer.parseInt(getPropertyFromBundle("Team001_ID"));
    public static final int TEAM002_ID_CODE = Integer.parseInt(getPropertyFromBundle("Team002_ID"));
    public static final String TEAM001_ACTIVE_PRODUCT_CODE = "WS";  // WebStorm
    public static final String TEAM002_PRODUCT_CODE_WITHOUT_LICENSE = "SPE";  // "Space Cloud Enterprise"

    static {
        String customerCode = System.getProperty("X_CUSTOMER_CODE_HEADER");
        String apiKey = System.getProperty("X_API_KEY_HEADER");
        String apiKeyTeam = System.getProperty("TEAM_X_API_KEY_HEADER");
        if (customerCode == null || customerCode.isBlank()) {
            customerCode = getPropertyFromBundle("X_CUSTOMER_CODE_HEADER");
        }
        if (apiKey == null || apiKey.isBlank()) {
            apiKey = getPropertyFromBundle("X_API_KEY_HEADER");
        }
        if (apiKeyTeam == null || apiKeyTeam.isBlank()) {
            apiKeyTeam = getPropertyFromBundle("TEAM_X_API_KEY_HEADER");
        }
        X_CUSTOMER_CODE_HEADER = customerCode;
        X_API_KEY_HEADER = apiKey;
        TEAM_X_API_KEY_HEADER = apiKeyTeam;
    }
}
