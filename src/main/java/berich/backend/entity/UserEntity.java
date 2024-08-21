package berich.backend.entity;

import berich.backend.dto.JoinDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name="user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter

public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull // 객체 수준에서 null 값이 들어오는 것을 방지
	@Column(length = 12, nullable=false) // 데이터베이스 수준에서 null 값이 들어가는 것을 방지
	private String username;

	@NotNull
	@Column(unique = true, nullable = false)
	private String email;

	@NotNull
	@Column(nullable = false)
	private String password;

	@NotNull
	@Column(nullable = false)
	private String role;

	public static UserEntity createUser(@NotNull JoinDTO joinDTO, PasswordEncoder encoder) {
		return UserEntity.builder()
				.username(joinDTO.getUsername())
				.password(encoder.encode(joinDTO.getPassword()))
				.email(joinDTO.getEmail())
				.role("ROLE_ADMIN")
				.build();
	}
}
