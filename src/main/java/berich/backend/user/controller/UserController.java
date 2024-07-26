package berich.backend.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import berich.backend.user.dto.UserDto;
import berich.backend.user.repository.UserRepository;
import berich.backend.user.domain.User;
import berich.backend.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor // 불변성 보장
@RequestMapping("/api/auth")

public class UserController {

	private final UserService userService;

	// 회원가입
	@PostMapping("/join")
	public ResponseEntity<User> saveUser(@RequestBody UserDto userDto) {
		User savedUser = userService.saveUser(userDto);
		return ResponseEntity.ok(savedUser);
	}

	// email 중복 체크
	@GetMapping("/check/email/{email}")
	public ResponseEntity<Boolean> checkEmail(@PathVariable("email") String email) {
		return ResponseEntity.ok(userService.checkEmail(email));
	}

	// username 중복 체크
	@GetMapping("/check/username/{username}")
	public ResponseEntity<Boolean> checkUsername(@PathVariable("username") String username) {
		return ResponseEntity.ok(userService.checkUsername(username));
	}
}
