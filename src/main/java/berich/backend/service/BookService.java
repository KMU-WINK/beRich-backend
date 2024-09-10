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
    public BookEntity writeBook(Long budgetId, BookDTO bookDTO) {
        BudgetEntity budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new CustomException(ErrorCode.BUDGET_NOT_FOUND));

        // 예산의 년, 월과 작성할 가계부의 년, 월이 다르면 예외처리
        if(budget.getDate().getYear() != bookDTO.getEventDate().getYear() || budget.getDate().getMonth() != bookDTO.getEventDate().getMonth()) {
            throw new CustomException(ErrorCode.BUDGET_ID_NOT_CORRECT);
        }

        try{
            BookEntity book = BookEntity.createBook(bookDTO, budget);
            bookRepository.save(book);

            System.out.println(book.getType().getClass());

            if("지출".equals(book.getType())) {
                budget.plusOutlay(book.getCost());
            } else if("수입".equals(book.getType())) {
                budget.plusIncome(book.getCost());
            } else {
                throw new CustomException(ErrorCode.INVALID_ARGUMENT);
            }
            return book;

        } catch(IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    // 가계부 수정
    @Transactional
    public BookEntity modifyBook(Long bookId, BookDTO bookDTO) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        BudgetEntity budget = book.getBudgetEntity();

        try {

            if("지출".equals(book.getType()) & "지출".equals(bookDTO.getType())) {
                budget.minusOutlay(book.getCost());
                budget.plusOutlay(bookDTO.getCost());
            } else if("지출".equals(book.getType()) & "수입".equals(bookDTO.getType())) {
                budget.minusOutlay(book.getCost());
                budget.plusIncome(bookDTO.getCost());
            } else if("수입".equals(book.getType()) & "지출".equals(bookDTO.getType())) {
                budget.minusIncome(book.getCost());
                budget.plusOutlay(bookDTO.getCost());
            } else if("수입".equals(book.getType()) & "수입".equals(bookDTO.getType())) {
                budget.minusIncome(book.getCost());
                budget.plusIncome(bookDTO.getCost());
            } else {
                throw new CustomException(ErrorCode.INVALID_ARGUMENT);
            }

            book.updateBook(bookDTO);
            bookRepository.save(book);
            return book;
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    // 가계부 삭제
    @Transactional
    public BookEntity deleteBook(Long bookId) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        try {
            if("지출".equals(book.getType())) {
                book.getBudgetEntity().minusOutlay(book.getCost());
            } else if("수입".equals(book.getType())) {
                book.getBudgetEntity().minusIncome(book.getCost());
            } else {
                throw new CustomException(ErrorCode.INVALID_ARGUMENT);
            }

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
            List<BookEntity> books = bookRepository.findByUserAndMonth(user, year, month);

            // 날짜별로 그룹화
            Map<LocalDate, List<BookEntity>> groupBooks = books.stream()
                    .collect(Collectors.groupingBy(BookEntity::getEventDate));

            return groupBooks;

        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_ARGUMENT);
        }
    }
}