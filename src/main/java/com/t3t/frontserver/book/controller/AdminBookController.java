package com.t3t.frontserver.book.controller;

import com.t3t.frontserver.book.model.request.BookRegisterRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/testAdmin/books")
public class AdminBookController {

    // 도서 등록 페이지 요청
    @GetMapping("/new")
    public String getRegisterBookAdminPage(Model model) {

        model.addAttribute("bookRegisterRequest", new BookRegisterRequest());
        return "admin/page/registerBook";
    }

    // 도서 등록 요청
    @PostMapping
    public String createBook(BookRegisterRequest request) {

        return "admin/page/registerBook";
    }
}
