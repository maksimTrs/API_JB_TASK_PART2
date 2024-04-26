package enums;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseValuesEnum {
    LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN("LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN",
            "No available license found to assign in the team %d with product %s"),
    TEAM_MISMATCH("TEAM_MISMATCH", "Token was generated for team with id 2046529"),
    INVALID_TOKEN("INVALID_TOKEN", "The token provided is invalid"),
    MISSING_FIELD("MISSING_FIELD", "Either licenseId or license must be provided"),
    EXPIRED_WITHOUT_FALLBACK("EXPIRED_WITHOUT_FALLBACK", "EXPIRED_WITHOUT_FALLBACK");

    private final String code;
    private final String description;

    public String getDescription(int teamId, String productCode) {
        if (this == LICENSE_IS_NOT_AVAILABLE_TO_ASSIGN) {
            return String.format(description, teamId, productCode);
        } else {
            return description;
        }
    }
}