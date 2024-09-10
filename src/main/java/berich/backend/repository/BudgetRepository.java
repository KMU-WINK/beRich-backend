package berich.backend.repository;

import berich.backend.entity.BudgetEntity;
import berich.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {
    @Query("SELECT COUNT(b) > 0 FROM BudgetEntity b WHERE b.userEntity = :user AND b.date BETWEEN :startDate AND :endDate")
    boolean existsByDateRange(@Param("user") UserEntity user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT b FROM BudgetEntity b WHERE b.userEntity = :user AND b.date BETWEEN :startDate AND :endDate")
    Optional<BudgetEntity> findByDateRange(@Param("user") UserEntity user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
