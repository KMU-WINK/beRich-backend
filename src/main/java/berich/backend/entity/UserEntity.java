package berich.backend.entity;

import berich.backend.dto.JoinDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank// 객체 수준에서 null 값이 들어오는 것을 방지
    @Column(length = 12, nullable = false) // 데이터베이스 수준에서 null 값이 들어가는 것을 방지
    private String username;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Column(nullable = false)
    private String role;

    // 가계부 작성 포인트
    @NotNull
    @Column(nullable = false)
    private Long point;

    // pet 레벨
    @NotNull
    @Column(nullable = false)
    private int petLevel;

    // pet 이름
    @Column
    private String petName;

    public static UserEntity createUser(@NotNull JoinDTO joinDTO, PasswordEncoder encoder) {
        return UserEntity.builder()
                .username(joinDTO.getUsername())
                .password(encoder.encode(joinDTO.getPassword()))
                .email(joinDTO.getEmail())
                .role("ROLE_ADMIN")
                .point(0L)
                .petLevel(0)
                .petName("부자")
                .build();
    }
}