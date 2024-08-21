package berich.backend.exception;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Builder
@Data
public class ErrorDTO {
    private int status;
    private String code;
    private String msg;

    public static ResponseEntity<ErrorDTO> toResponseEntity(ErrorCode e) {
        return ResponseEntity.status(e.getStatus().value())
                .body(ErrorDTO.builder()
                .status(e.getStatus().value())
                .code(e.getCode())
                .msg(e.getMsg())
                .build());
    }
}
