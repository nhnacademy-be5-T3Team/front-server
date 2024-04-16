package com.t3t.frontserver.book.controller;

import com.t3t.frontserver.book.adaptor.BookAdaptor;
import com.t3t.frontserver.book.model.response.BookDetailResponse;
import com.t3t.frontserver.category.adaptor.CategoryAdaptor;
import com.t3t.frontserver.category.response.CategoryListResponse;
import com.t3t.frontserver.index.OrderFormRequest;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.review.adaptor.ReviewAdaptor;
import com.t3t.frontserver.review.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.t3t.frontserver.util.ServiceUtils.handleResponse;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final CategoryAdaptor categoryAdaptor;
    private final BookAdaptor bookAdaptor;
    private final ReviewAdaptor reviewAdaptor;

    @GetMapping("books/{bookId}")
    public String test(Model model, @PathVariable Long bookId,
                       @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo) {

        List<CategoryListResponse> categoryList = getDataFromCategoryAdaptor();
        BookDetailResponse bookDetailList = getDataFromBookAdaptor(bookId);
        PageResponse<ReviewResponse> reviewList = getDataFromReviewAdaptor(bookId, pageNo);

        // 리뷰 목록을 위한 페이징
        if (reviewList != null) {
            int blockLimit = 3; // 현재 페이지 앞뒤로 보여줄 개수 설정
            int nowPage = reviewList.getPageNo() + 1;
            int startPage = Math.max(nowPage - blockLimit, 1);
            int endPage = Math.min(nowPage + blockLimit, reviewList.getTotalPages());

            model.addAttribute("reviewNowPage", nowPage);
            model.addAttribute("reviewStartPage", startPage);
            model.addAttribute("reviewEndPage", endPage);
            model.addAttribute("reviewList", reviewList.getContent());
        }

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("bookDetailList", bookDetailList);

        // 주문 페이지로 넘기기 위한 객체 (도서 id, 주문 수량, 포장 id)
        model.addAttribute("orderFormRequest", new OrderFormRequest(bookId));

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

    private PageResponse<ReviewResponse> getDataFromReviewAdaptor(Long bookId, int pageNo) {
        ResponseEntity<BaseResponse<PageResponse<ReviewResponse>>> reviewsResponse = reviewAdaptor.getBook(bookId, pageNo);
        return handleResponse(reviewsResponse);
    }
}
