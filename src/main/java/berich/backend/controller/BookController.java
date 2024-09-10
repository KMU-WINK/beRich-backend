package berich.backend.controller;

import berich.backend.dto.BookDTO;
import berich.backend.entity.BookEntity;
import berich.backend.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")

public class BookController {
    private final BookService bookService;

    // 가계부 작성
    @PostMapping("/write/{budgetId}")
    public ResponseEntity<BookEntity> writeBook(@PathVariable("budgetId") Long budgetId, @RequestBody @Valid BookDTO bookDTO) {
        return ResponseEntity.ok().body(bookService.writeBook(budgetId, bookDTO));
    }

    // 가계부 수정
    @PutMapping("/modify/{bookId}")
    public ResponseEntity<BookEntity> modifyBook(@PathVariable("bookId") Long bookId, @RequestBody @Valid BookDTO bookDTO) {
        return ResponseEntity.ok().body(bookService.modifyBook(bookId, bookDTO));
    }

    // 가계부 삭제
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<BookEntity> deleteBook(@PathVariable("bookId") Long bookId) {
        return ResponseEntity.ok().body(bookService.deleteBook(bookId));
    }

    // 가계부 월별 조회 (해당 월에 해당하는 가계부 전체 조회)
    @GetMapping("/list/{userId}")
    public ResponseEntity<Map<LocalDate, List<BookEntity>>> listBook(@PathVariable("userId") Long id, @RequestParam("year") int year , @RequestParam("month") int month) {
        return ResponseEntity.ok().body(bookService.listBook(id, year, month));
    }

    // 가계부 개별 조회
    @GetMapping("/detail/{bookId}")
    public ResponseEntity<BookEntity> detailBook(@PathVariable("bookId") Long bookId) {
        return ResponseEntity.ok().body(bookService.detailBook(bookId));
    }

}