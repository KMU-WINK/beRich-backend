package berich.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name="book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)


public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDate eventDate;

    @NotNull
    @Column(nullable = false)
    private String category;

    @NotNull
    @Column(nullable = false)
    private Long detail;

    @NotNull
    @Column(nullable = false)
    private Long budget;
}

