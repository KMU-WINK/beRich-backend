package berich.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Getter

public class BookDTO {
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;

    @NotNull
    private String type;

    @NotBlank
    private String category;

    @NotBlank
    private String detail;

    @NotNull
    @PositiveOrZero(message = "금액은 0 이상이어야 함")
    private Long cost;
}
