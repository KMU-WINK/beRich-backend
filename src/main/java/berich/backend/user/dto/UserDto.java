package berich.backend.user.dto;

import berich.backend.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	private String username;
	private String email;
	private String password;

	public User toEntity() {
		return User.builder() // User 클래스에서 생성된 빌더 호출
			.username(username)
			.email(email)
			.password(password)
			.build(); // 설정된 값으로 User 객체 생성
	}
}
