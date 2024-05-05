package enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiErrorResponseCodeAndDescriptionFieldsEnum {

    LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN_RESPONSE("LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN", "NON_PER_USER"),
    NO_AVAILABLE_LICENSE_TO_ASSIGN_RESPONSE("NO_AVAILABLE_LICENSE_TO_ASSIGN",
            "No available license found to assign in the team %s with product %s"),
    TEAM_MISMATCH_RESPONSE("TEAM_MISMATCH", "Token was generated for team with id 2046529"),
    INVALID_TOKEN_RESPONSE("INVALID_TOKEN", "The token provided is invalid"),
    TEAM_NOT_FOUND_RESPONSE("TEAM_NOT_FOUND", "%s"),
    INVALID_CONTACT_EMAIL_RESPONSE("INVALID_CONTACT_EMAIL", "%s"),
    MISSING_FIELD_RESPONSE("MISSING_FIELD", "Either licenseId or license must be provided");

    private final String code;
    private final String description;

    public static String getFormatDescription(String description, String firstValue, String secondValue) {
        return String.format(description, firstValue, secondValue);
    }
}