package berich.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// Spring Security 웹 보안 기능 활성화 => FilterChain 제공
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return webSecurity -> webSecurity.ignoring()
				.requestMatchers("/error");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.csrf(AbstractHttpConfigurer::disable) // csrf X
			.formLogin(AbstractHttpConfigurer::disable) // Spring Security가 제공해주는 기본 로그인 사용 X
			.httpBasic(AbstractHttpConfigurer::disable) // jwt 사용하므로 http 기본 인증 사용 X

			// URL 권한 설정
			.authorizeHttpRequests((auth) -> auth
					.requestMatchers("api/auth/login", "/", "api/auth/join").permitAll()
					.requestMatchers("/admin").hasRole("ADMIN")
					.anyRequest().authenticated());

		// 세션 설정 (토큰 방식은 필요 X)

		http
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	// 비밀번호 암호화를 위해서
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
