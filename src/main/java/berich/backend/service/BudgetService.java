package berich.backend.service;

import berich.backend.dto.BudgetDTO;
import berich.backend.entity.BudgetEntity;
import berich.backend.entity.UserEntity;
import berich.backend.exception.CustomException;
import berich.backend.exception.ErrorCode;
import berich.backend.repository.BudgetRepository;
import berich.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class BudgetService {
    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;

    // 예산 추가
    @Transactional
    public BudgetEntity modifyBudget(Long userId, BudgetDTO budgetDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        try {

            LocalDate startDate = LocalDate.of(budgetDto.getDate().getYear(), budgetDto.getDate().getMonth(), 1);
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);

            // 해당 날짜에 이미 예산이 존재하는 경우 수정 가능한지 판단하고 수정
            if(budgetRepository.existsByDateRange(user, startDate, endDate)){
                BudgetEntity budget = budgetRepository.findByDateRange(user, startDate, endDate);

                if(budget.isModifiable()){
                    budget.updateBudget(budgetDto);
                    budgetRepository.save(budget);
                    return budget;
                } else {
                    throw new CustomException(ErrorCode.BUDGET_NOT_MODIFIABLE);
                }
            }

            BudgetEntity budget = BudgetEntity.createBudget(budgetDto, user);
            budgetRepository.save(budget);
            return budget;
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    // 남은 예산 확인
    public long remainingBudget (Long budgetId) {
        BudgetEntity budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new CustomException(ErrorCode.BUDGET_NOT_FOUND));

        return budget.getRemainingBudget();
    }
}