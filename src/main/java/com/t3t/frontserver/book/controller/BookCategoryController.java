package com.t3t.frontserver.book;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookCategoryController {

    @GetMapping("/category/{categoryId}/books")
    public void test(@PathVariable Integer categoryId){
        //TODO : 검색 페이지로 이동 - 카테고리 별 도서 검색 조회
    }
}
