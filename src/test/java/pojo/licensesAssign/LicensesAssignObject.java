package pojo.licensesAssign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class LicensesAssignObject {
	private License license;
	@Builder.Default
	private Boolean sendEmail = false;
	@Builder.Default
	private Boolean includeOfflineActivationCode = false;
	private Contact contact;
	private String licenseId;
}