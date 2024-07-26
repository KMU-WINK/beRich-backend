package berich.backend.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// @Data 를 사용하게 되면 사용하지 않는 어노테이션까지 한 번에 선언이 됨
// 객체 일관성을 위해 @Setter 대신 @Build를 사용해서 필요한 데이터만 build 하자

@Entity
@Table(name="user")
@Getter
//무분별한 객체 생성에 대해 한번 더 체크 가능 (User Class는 모든 정보를 가지고 있어야함 => 다른 곳에서 기본 생성자 실행 막음)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자가 없으면 JPA는 객체를 생성할 수 없음 (protected로 제어하는 것은 허용)

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull // 객체 수준에서 null 값이 들어오는 것을 방지
	@Column(unique=true, length = 12, nullable=false) // 데이터베이스 수준에서 null 값이 들어가는 것을 방지
	private String username;

	@NotNull
	@Column(unique = true, nullable = false)
	private String email;

	@NotNull
	@Column(nullable = false)
	private String password;

	// @Builder 는 @AllConstructor와 함께 사용해야하는데  @AllConstructor는 오류를 발생 시킬 수 있으므로 생성자에서 @Builder를 선언
	// 생성자에 @Builder를 선언하면 객체 생성 시 받지 않아야할 매개변수들도 빌더에 노출되는 문제점을 해결할 수 있음
	@Builder
	public User(
		String username,
		String  email,
		String  password){
		this.username = username;
		this.email = email;
		this.password = password;
	}
}
