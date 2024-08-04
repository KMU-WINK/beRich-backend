package berich.backend.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import berich.backend.dto.JoinDTO;
import berich.backend.entity.UserEntity;
import berich.backend.repository.UserRepository;

@Service
public class JoinService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	// 생성자 주입
	public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public void saveUser(JoinDTO joinDto) {

		String username = joinDto.getUsername();
		String email = joinDto.getEmail();
		String password = joinDto.getPassword();

		Boolean isExist = userRepository.existsByEmail(email);

		// 만약 db에 동일한 이메일이 이미 있으면 Join Process 즉시 종료
		if(isExist) {
			return;
		}

		UserEntity data = new UserEntity();

		data.setUsername(username);
		data.setEmail(email);
		data.setPassword(bCryptPasswordEncoder.encode(password));
		data.setRole("ROLE_ADMIN");

		userRepository.save(data);
	}
}
