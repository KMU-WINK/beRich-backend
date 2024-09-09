package berich.backend.config;

import berich.backend.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.List;

@Configuration
// Spring Security 웹 보안 기능 활성화 => FilterChain 제공
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return webSecurity -> webSecurity.ignoring()
				.requestMatchers("/error");
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// HttpSecurity 객체를 사용하여 보안 설정
		http
			.csrf(AbstractHttpConfigurer::disable) // csrf X
			.formLogin(AbstractHttpConfigurer::disable) // Spring Security가 제공해주는 기본 로그인 사용 X
			.httpBasic(AbstractHttpConfigurer::disable) // jwt 사용하므로 http 기본 인증 사용 X
		    .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Cors 설정


		// URL 권한 설정
			.authorizeHttpRequests((auth) -> auth
					.requestMatchers("/api/auth/login", "/api/budget/{userId}", "/api/auth/join", "/api/book/write/{budgetId}", "/api/book/modify/{bookId}", "/api/book/delete/{bookId}", "/api/book/list/{bookId}").permitAll()
					.requestMatchers("/admin").hasRole("ADMIN")
					.anyRequest().authenticated());

		// 세션 설정 (토큰 방식은 필요 X)

		http
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// 필터 등록

		http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);


		return http.build();
	}

	@Bean
	// 추후에 특정 도메인으로 cors 설정해줘야함 (인증정보 허용 - 로그인을 위해)
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration(); // CorsConfiguration 객체를 생성 -> Cors 설정
		configuration.setAllowedOrigins(List.of("*")); // 허용할 Origin 설정 (도메인)
		configuration.addAllowedHeader("*"); // 모든 요청 헤더 허용
		configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE")); // HTTP 메소드 설정
		configuration.setAllowCredentials(false); // 인증정보(쿠키, HTTP 인증 헤더 등)를 포함할지 여부
		configuration.addExposedHeader("Authorization"); // Access-Control-Expose-Headers 헤더 설정

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 Cors 설정 적용
		return source;  // CorsConfigurationSource 객체 반환
	}

	// 비밀번호 암호화를 위해서
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
