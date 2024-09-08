package berich.backend.service;

import berich.backend.dto.BookDTO;
import berich.backend.entity.BookEntity;
import berich.backend.entity.BudgetEntity;
import berich.backend.entity.UserEntity;
import berich.backend.exception.CustomException;
import berich.backend.exception.ErrorCode;
import berich.backend.repository.BookRepository;
import berich.backend.repository.BudgetRepository;
import berich.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BudgetRepository budgetRepository;

    // 가계부 작성
    @Transactional
    public BookEntity writeBook(Long id, BookDTO bookDTO) {
        BudgetEntity budget = budgetRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BUDGET_NOT_FOUND));

        try{

            BookEntity book = BookEntity.createBook(bookDTO, budget);
            bookRepository.save(book);
            return book;

        } catch(IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    // 가계부 수정
    @Transactional
    public BookEntity modifyBook(Long bookId, Long userId, BookDTO bookDTO) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        try {
            book.updateBook(bookDTO);
            bookRepository.save(book);
            return book;
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    // 가계부 삭제
    @Transactional
    public BookEntity deleteBook(Long id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        try {
            bookRepository.delete(book);
            return book;
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    // 가계부 월별 조회
    @Transactional
    public Map<LocalDate, List<BookEntity>> listBook(Long id, int year, int month) {
        // 해당 user 없으면 예외처리
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        try {
            List<BookEntity> books = bookRepository.findByUserAndMonth(user, year, month); // 해당 user의 해당 월 가계부 조회

            // 날짜별로 그룹화
            Map<LocalDate, List<BookEntity>> groupBooks = books.stream()
                    .collect(Collectors.groupingBy(BookEntity::getEventDate));

            return groupBooks;

        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_ARGUMENT);
        }
    }
}