package com.t3t.frontserver.book.controller;

import com.t3t.frontserver.book.adaptor.BookAdaptor;
import com.t3t.frontserver.book.model.response.BookDetailResponse;
import com.t3t.frontserver.category.adaptor.CategoryAdaptor;
import com.t3t.frontserver.category.response.CategoryListResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.t3t.frontserver.util.ServiceUtils.handleResponse;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final CategoryAdaptor categoryAdaptor;
    private final BookAdaptor bookAdaptor;

    @GetMapping("books/{bookId}")
    public String test(Model model, @PathVariable Long bookId) {

        List<CategoryListResponse> categoryList = getDataFromCategoryAdaptor();
        BookDetailResponse bookDetailResponse = getDataFromBookAdaptor(bookId);

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("bookDetailResponse", bookDetailResponse);

        return "main/page/detail.html";
    }

    private List<CategoryListResponse> getDataFromCategoryAdaptor() {
        ResponseEntity<BaseResponse<List<CategoryListResponse>>> categoriesResponse = categoryAdaptor.getCategories();
        return handleResponse(categoriesResponse);
    }

    private BookDetailResponse getDataFromBookAdaptor(Long bookId) {
        ResponseEntity<BaseResponse<BookDetailResponse>> bookDetailResponse = bookAdaptor.getBook(bookId);
        return handleResponse(bookDetailResponse);
    }
}
