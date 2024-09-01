package berich.backend.controller;

import berich.backend.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")

public class BookController {
    private final BookService bookService;

    // 가계부 작성
    //@PostMapping("/write")


    // 연속 작성 일 수 체크

    // 예산 설정

    // 예산 분배해서 알려주기

    // 유저가 선택한 시간에 가계부 작성하라고 알람보내기

    // 소비 카테고리 분석해서 쓸데 없는 지출 알람 보내기

    // 소비 패턴 퍼센테이지로 보여주기

    // 월말 평가 (총 소비 카테고리 분석)

    // 가계부 수정
}
