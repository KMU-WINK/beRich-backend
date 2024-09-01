package berich.backend.controller;

import berich.backend.dto.BudgetDTO;
import berich.backend.entity.UserEntity;
import berich.backend.repository.UserRepository;
import berich.backend.service.SettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/setting")

public class SettingController {
    private final SettingService settingService;

    // 예산 수정
    @PostMapping("/budget")
    public ResponseEntity<UserEntity> BudgetSetting(@RequestBody @Valid BudgetDTO budgetDto) {
        return ResponseEntity.ok().body(settingService.modifyBudget(budgetDto));
    }
}
