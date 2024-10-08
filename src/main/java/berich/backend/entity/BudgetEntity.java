package berich.backend.entity;

import berich.backend.dto.BudgetDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.YearMonth;

@Entity
@Table(name="budget")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
@Getter

public class BudgetEntity {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //외래키 (userid)
    @ManyToOne // 단방향 관계 설정
    @JoinColumn(name = "user_id", nullable = false)// 외래키 설정
    private UserEntity userEntity;

    //해당 년도, 월
    @Column(nullable = false)
    @NotNull
    private LocalDate date;

    //예산
    @NotNull
    @Column(nullable = false, columnDefinition = "BIGINT CHECK (budget >= 0)")
    private Long budget;

    // 총 지출

    @NotNull
    @Column(nullable = false, columnDefinition = "BIGINT CHECK (outlay >= 0)")
    private Long outlay;

    // 총 수입

    @NotNull
    @Column(nullable = false, columnDefinition = "BIGINT CHECK (income >= 0)")
    private Long income;

    // 남은 예산

    @NotNull
    @Column(nullable = false)
    private Long remainingBudget;

    // 이번 달 예산 수정 가능 여부
    @NotNull
    @Column(nullable = false)
    private boolean isModifiable;

    public static BudgetEntity createBudget(@NotNull BudgetDTO budgetDTO, UserEntity userEntity) {
        return BudgetEntity.builder()
                .date(budgetDTO.getDate())
                .budget(budgetDTO.getBudget())
                .outlay(0L)
                .income(0L)
                .remainingBudget(budgetDTO.getBudget())
                .isModifiable(true)
                .userEntity(userEntity)
                .build();
    }

    public void updateBudget(@NotNull BudgetDTO budgetDTO) {
        this.budget = budgetDTO.getBudget();
        this.remainingBudget = budgetDTO.getBudget() - this.outlay; // 예산 수정에 따른 남은 예산 수정
        this.isModifiable = false; // 수정은 한 달에 한 번만 가능
    }

    // outlay 수정
    public void plusOutlay(Long cost) {
        this.outlay += cost;
        this.remainingBudget = this.budget - this.outlay;
    }

    public void minusOutlay(Long cost) {
        this.outlay -= cost;
        this.remainingBudget = this.budget + this.outlay;
    }

    // income 수정

    public void plusIncome(Long cost) {
        this.income += cost;
    }

    public void minusIncome(Long cost) {
        this.income -= cost;
    }
}
