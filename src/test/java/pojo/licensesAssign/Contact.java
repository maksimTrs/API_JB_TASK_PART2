package pojo.licensesAssign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Contact {

    @Builder.Default
    private String firstName = new Faker().name().firstName();
    @Builder.Default
    private String lastName = new Faker().name().lastName();
    @Builder.Default
    private String email = new Faker().internet().emailAddress();
}