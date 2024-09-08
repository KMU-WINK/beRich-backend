package berich.backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "001_DUPLICATED_EMAIL", "이미 가입된 이메일입니다."),
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "002_INVALID_ARGUMENT", "누락된 정보가 있는지 확인해주세요."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "003_USER_NOT_FOUND", "사용자를 찾을 수 없습니다."),
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "004_BOOK_NOT_FOUND", "가계부를 찾을 수 없습니다."),
    BUDGET_NOT_FOUND(HttpStatus.NOT_FOUND, "004_BUDGET_NOT_FOUND", "예산 정보를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "006_INVALID_PASSWORD", "비밀번호가 일치하지 않습니다."),
    DUPLICATED_BUDGET(HttpStatus.BAD_REQUEST, "007_DUPLICATED_BUDGET", "이미 등록되어 있는 예산입니다. 수정을 이용해주세요."),
    BUDGET_NOT_MODIFIABLE(HttpStatus.BAD_REQUEST, "008_BUDGET_NOT_MODIFIABLE", "이미 이번 달 예산이 등록되어 수정이 불가능합니다.");

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
