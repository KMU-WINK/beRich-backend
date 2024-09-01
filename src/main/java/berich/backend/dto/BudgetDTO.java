package berich.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BudgetDTO {
    @NotNull
    @Min(value = 0, message = "예산은 양수로 설정해야함")
    private Long budget;
}
