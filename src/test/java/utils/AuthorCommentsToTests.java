package utils;

public class AuthorCommentsToTests {

    public static String COMMENT_1 = "I presume we hava an API design validation issue here:" +
            " instead of JSON response structure as we have" +
            " for another negative tests" +
            " assertResponseBody(response, CODE_400.CODE, MISSING_FIELD.getCode()," +
            " MISSING_FIELD.getDescription());" +
            " we are getting HTML response structure";


    public static String COMMENT_2 = "I presume we hava an API design  issue here: " +
            " AssignLicenseRequest body schema validation" +
            " requires to have \"sendEmail\" field: \"sendEmail*\" marked as mandatory with red star \"*\"" +
            " symbol but API does not require to send \"sendEmail\" field - it is optional" +
            " API DOC: https://account.jetbrains.com/api-doc#/Licenses/assignLicense";

    public static String COMMENT_3 = "I presume we hava an API design  issue here: " +
            " AssignLicenseRequest body schema validation" +
            " requires to have \"includeOfflineActivationCode\" field: \"includeOfflineActivationCode*\" " +
            " marked as mandatory with red star \"*\" symbol" +
            " but API does not require to send \"includeOfflineActivationCode\" field - it is optional." +
            " API DOC: https://account.jetbrains.com/api-doc#/Licenses/assignLicense";
}
