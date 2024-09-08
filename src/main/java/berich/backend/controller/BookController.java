package berich.backend.controller;

import berich.backend.dto.BookDTO;
import berich.backend.entity.BookEntity;
import berich.backend.service.BookService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    public ResponseEntity<BookEntity> writeBook(@PathVariable("budgetId") Long id, @RequestBody @Valid BookDTO bookDTO) {
        return ResponseEntity.ok().body(bookService.writeBook(id, bookDTO));
    }

    // 가계부 수정
    @PutMapping("/modify/{bookId}/{userId}")
    public ResponseEntity<BookEntity> modifyBook(@PathVariable("bookId") Long bookId, @PathVariable("userID") Long userId, @RequestBody @Valid BookDTO bookDTO) {
        return ResponseEntity.ok().body(bookService.modifyBook(bookId, userId, bookDTO));
    }

    // 가계부 삭제 (현재 달에 해당하는 가계부만 삭제 가능)
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<BookEntity> deleteBook(@PathVariable("bookId") Long id) {
        return ResponseEntity.ok().body(bookService.deleteBook(id));
    }

    // 가계부 월별 조회 (해당 월에 해당하는 가계부 전체 조회)
    @GetMapping("/list/{userId}")
    public ResponseEntity<Map<LocalDate, List<BookEntity>>> listBook(@PathVariable("userId") Long id, @RequestParam("year") int year , @RequestParam("month") int month) {
        return ResponseEntity.ok().body(bookService.listBook(id, year, month));
    }
}