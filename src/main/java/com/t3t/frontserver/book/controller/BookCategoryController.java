package com.t3t.frontserver.book.controller;


import com.t3t.frontserver.book.client.BookCategoryApiClient;
import com.t3t.frontserver.book.model.response.BookDetailResponse;
import com.t3t.frontserver.category.client.CategoryApiClient;
import com.t3t.frontserver.category.response.CategoryTreeResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.t3t.frontserver.util.ServiceUtils.handleResponse;

@RequiredArgsConstructor
@Controller
public class BookCategoryController {
    private final BookCategoryApiClient bookCategoryApiClient;
    private final CategoryApiClient categoryAdaptor;

    @GetMapping("/category/{categoryId}/books")
    public String getBooksByCategoryId(Model model, @PathVariable Integer categoryId,
                                       @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo) {

        List<CategoryTreeResponse> categoryList = getDataFromCategoryAdaptor(1, 2);
        PageResponse<BookDetailResponse> bookList = getDataFromBookCategoryAdaptor(categoryId, pageNo);

        if (bookList != null) {
            int blockLimit = 3;
            int nowPage = bookList.getPageNo() + 1;
            int startPage = Math.max(nowPage - blockLimit, 1);
            int endPage = Math.min(nowPage + blockLimit, bookList.getTotalPages());

            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("bookList", bookList.getContent());
        }

        model.addAttribute("categoryList", categoryList);

        return "main/page/search.html";
    }

    private List<CategoryTreeResponse> getDataFromCategoryAdaptor(Integer startDepth, Integer maxDepth ) {
        ResponseEntity<BaseResponse<List<CategoryTreeResponse>>> categoriesResponse = categoryAdaptor.getCategoryTreeByDepth(startDepth, maxDepth);
        return handleResponse(categoriesResponse);
    }

    private PageResponse<BookDetailResponse> getDataFromBookCategoryAdaptor(Integer categoryId, int pageNo) {
        ResponseEntity<BaseResponse<PageResponse<BookDetailResponse>>> booksResponse = bookCategoryApiClient.getBooksByCategoryId(categoryId, pageNo);
        return handleResponse(booksResponse);
    }
}
