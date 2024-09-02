package berich.backend.repository;

import berich.backend.entity.BookEntity;
import berich.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Query("SELECT b FROM BookEntity b WHERE b.userEntity = :user " +
            "AND FUNCTION('YEAR', b.eventDate) = :year " +
            "AND FUNCTION('MONTH', b.eventDate) = :month")
    List<BookEntity> findByUserAndMonth(@Param("user") UserEntity user, @Param("year") int year, @Param("month") int month);

}
