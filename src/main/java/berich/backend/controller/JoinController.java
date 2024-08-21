package berich.backend.controller;

import berich.backend.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import berich.backend.dto.JoinDTO;
import berich.backend.service.JoinService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")

public class JoinController {

	private final JoinService joinService;

	// 회원가입
	@PostMapping("/join")
	public ResponseEntity<UserEntity> JoinProcess(@RequestBody @Valid JoinDTO joinDto) {
		return ResponseEntity.ok().body(joinService.saveUser(joinDto));
	}
}
