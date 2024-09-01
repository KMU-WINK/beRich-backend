package berich.backend.service;

import berich.backend.dto.BudgetDTO;
import berich.backend.entity.UserEntity;
import berich.backend.exception.CustomException;
import berich.backend.exception.ErrorCode;
import berich.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class SettingService {
    private final UserRepository userRepository;

    // 예산 수정
    @Transactional
    public UserEntity modifyBudget(Long id, BudgetDTO budgetDto) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        try {
            user.setBudget(budgetDto.getBudget());
            return user;
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_ARGUMENT);
        }
    }
}