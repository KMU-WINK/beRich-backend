package berich.backend.controller;

import berich.backend.dto.BudgetDTO;
import berich.backend.entity.UserEntity;
import berich.backend.repository.UserRepository;
import berich.backend.service.SettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/setting")

public class SettingController {
    private final SettingService settingService;

    // 예산 수정
    @PutMapping("/budget/{id}")
    public ResponseEntity<UserEntity> BudgetSetting(@PathVariable("id") Long id, @RequestBody @Valid BudgetDTO budgetDto) {
        System.out.println(id);
        return ResponseEntity.ok().body(settingService.modifyBudget(id, budgetDto));
    }
}
