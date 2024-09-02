package berich.backend.entity;

import berich.backend.dto.BookDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter

public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 단방향 관계 설정
    @JoinColumn(name = "user_id", nullable = false)// 외래키 설정
    private UserEntity userEntity;

    @NotNull
    @Column(nullable = false)
    private LocalDate eventDate;

    // 수입 or 지출
    @NotNull
    @Column(nullable = false)
    private String type;

    @NotNull
    @Column(nullable = false)
    private String category;

    @NotNull
    @Column(nullable = false)
    private String detail;

    @NotNull
    @Column(nullable = false)
    private Long cost;


    public static BookEntity createBook(@NotNull BookDTO bookDTO, UserEntity userEntity) {
        return BookEntity.builder()
                .eventDate(bookDTO.getEventDate())
                .type(bookDTO.getType())
                .category(bookDTO.getCategory())
                .detail(bookDTO.getDetail())
                .cost(bookDTO.getCost())
                .userEntity(userEntity)
                .build();
    }

    // 가계부 수정
    public void updateBook(BookDTO bookDTO) {
        this.eventDate = bookDTO.getEventDate();
        this.type = bookDTO.getType();
        this.category = bookDTO.getCategory();
        this.detail = bookDTO.getDetail();
        this.cost = bookDTO.getCost();
    }
}

