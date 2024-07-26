package berich.backend.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import berich.backend.user.domain.User;
import berich.backend.user.dto.UserDto;
import berich.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

@Service
// 읽기 전용 => 데이터베이스 성능 최적화 + 데이터 무결성 유지
@Transactional(readOnly = true)
@RequiredArgsConstructor // 불변성 보장

public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	// 쓰기 작업 있으므로 readOnly X
	@Transactional
	public User saveUser(UserDto userDto) {
		try {
			// 비밀번호 암호화
			userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
			User user = userDto.toEntity();
			return userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			// 데이터 무결성 위반 시 예외 처리
			throw new RuntimeException("DB 에러" + e.getMessage(), e);
		} catch (Exception e) {
			// 기타 예외 처리
			throw new RuntimeException("뭔가 잘못된 것" + e.getMessage(), e);
		}
	}

	// 이메일 중복 체크
	public boolean checkEmail(String email) {
		if(email == null || email.trim().isEmpty() || !email.contains("@")) {
			throw new IllegalArgumentException("Email must contain '@'");
		}
		return userRepository.existsByEmail(email);
	}

	// 유저 이름 중복 체크
	public boolean checkUsername(String username) {
		if(username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("username null or empty");
		}
		return userRepository.existsByUsername(username);
	}
}

