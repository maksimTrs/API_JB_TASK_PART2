package enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCodeEnum {
    CODE_200(200, "Success"),
    CODE_400(400, "Bad Request"),
    CODE_401(401, "Unauthorized"),
    CODE_404(404, "Not Found"),
    CODE_403(403, "Forbidden");

    public final int CODE;
    public final String MSG;
}
