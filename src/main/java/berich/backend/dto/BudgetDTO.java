package berich.backend.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BudgetDTO {

    private Long id;

    @Min(value = 0, message = "예산은 양수로 설정해야함")
    private Long budget;
}
