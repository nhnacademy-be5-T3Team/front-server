package com.t3t.frontserver.book.controller;

import com.t3t.frontserver.book.adaptor.BookAdaptor;
import com.t3t.frontserver.book.model.response.BookDetailResponse;
import com.t3t.frontserver.category.adaptor.CategoryAdaptor;
import com.t3t.frontserver.category.response.CategoryListResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.review.adaptor.ReviewAdaptor;
import com.t3t.frontserver.review.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final ReviewAdaptor reviewAdaptor;

    @GetMapping("books/{bookId}")
    public String test(Model model, @PathVariable Long bookId, @PageableDefault(page = 0, size = 10) Pageable pageable) {

        List<CategoryListResponse> categoryList = getDataFromCategoryAdaptor();
        BookDetailResponse bookDetailList = getDataFromBookAdaptor(bookId);
        //Page<ReviewResponse> reviewPage = getDataFromReviewAdaptor(bookId, pageable);

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("bookDetailList", bookDetailList);
        //model.addAttribute("reviewList", reviewList);

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

    private Page<ReviewResponse> getDataFromReviewAdaptor(Long bookId, Pageable pageable) {
        ResponseEntity<BaseResponse<Page<ReviewResponse>>> reviewsResponse = reviewAdaptor.getBook(bookId, pageable);
        return handleResponse(reviewsResponse);
    }
}
