package berich.backend.controller;

import berich.backend.dto.BudgetDTO;
import berich.backend.entity.BudgetEntity;
import berich.backend.entity.UserEntity;
import berich.backend.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/budget")

public class BudgetController {
    private final BudgetService budgetService;

    // 예산 추가 및 수정
    // ismodifiable = true인 경우에만 수정 가능하고 수정 한 번 하면 false로 바꿈 => 모달 창으로 소비습관을 위해 달에 수정 1번만 가능하다고 신중하게 예산 선정하라고 말해주기

    @PostMapping("/{userId}")
    public ResponseEntity<BudgetEntity> BudgetSetting(@PathVariable("userId") Long id, @RequestBody @Valid BudgetDTO budgetDto) {
        System.out.println(id);
        return ResponseEntity.ok().body(budgetService.modifyBudget(id, budgetDto));
    }

    //예산 현황 조회 ()
}
