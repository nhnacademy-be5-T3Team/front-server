package com.t3t.frontserver.book.controller;

import com.t3t.frontserver.book.client.BookApiClient;
import com.t3t.frontserver.book.model.request.BookRegisterRequest;
import com.t3t.frontserver.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/books")
@Controller
public class AdminBookController {

    private final BookApiClient bookApiClient;

    /**
     * 도서 등록 페이지를 요청
     * @param model 데이터를 뷰에 전달하기 위한 Model 객체
     * @return 도서 등록 페이지의 뷰 이름인 "admin/page/registerBook"을 반환
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/new")
    public String getRegisterBookAdminPage(Model model) {

        model.addAttribute("bookRegisterRequest", new BookRegisterRequest());
        return "admin/page/registerBook";
    }

    /**
     * 도서를 등록하는 요청을 처리
     * @param request 등록하고자 하는 도서 정보를 담고 있는 BookRegisterRequest 객체
     * @author Yujin-nKim(김유진)
     */
    @PostMapping
    public String createBook(@ModelAttribute("bookRegisterRequest") BookRegisterRequest request) {
        log.info(request.toString());
//        ResponseEntity<BaseResponse<Void>> response = bookApiClient.createBook(request);
        return "admin/page/registerBook";
    }
}
