package berich.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import berich.backend.entity.UserEntity;

//JpaRepostiroy의 제네릭 타입 매개변수 <엔티티 클래스의 타입, 기본키 타입>

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
}
