package enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseValuesEnum {
    LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN("LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN", "NON_PER_USER"),
    NO_AVAILABLE_LICENSE_TO_ASSIGN("NO_AVAILABLE_LICENSE_TO_ASSIGN",
            "No available license found to assign in the team %d with product %s"),
    TEAM_MISMATCH("TEAM_MISMATCH", "Token was generated for team with id 2046529"),
    INVALID_TOKEN("INVALID_TOKEN", "The token provided is invalid"),
    TEAM_NOT_FOUND("TEAM_NOT_FOUND", "%d"),
    MISSING_FIELD("MISSING_FIELD", "Either licenseId or license must be provided");

    private final String code;
    private final String description;

    public static String getDescription(String description, int teamId, String productCode) {
        return String.format(description, teamId, productCode);
    }
}