package berich.backend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
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
    @Min(value = 0, message = "사용 금액은 양수로 설정해야함")
    private Long cost;
}
