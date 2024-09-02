package berich.backend.service;


import berich.backend.exception.CustomException;
import berich.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import berich.backend.dto.JoinDTO;
import berich.backend.entity.UserEntity;
import berich.backend.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)

public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserEntity saveUser(JoinDTO joinDto) {
        try {

            UserEntity user = UserEntity.createUser(joinDto, bCryptPasswordEncoder);
            userRepository.save(user);
            return user;

        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        } catch (IllegalArgumentException e) {
            throw new CustomException((ErrorCode.INVALID_ARGUMENT));
        }
    }
}
