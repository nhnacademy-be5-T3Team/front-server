package com.t3t.frontserver.main.controller;

import com.t3t.frontserver.category.adaptor.CategoryAdaptor;
import com.t3t.frontserver.category.response.CategoryListResponse;
import com.t3t.frontserver.common.exception.ApiDataFetchException;
import com.t3t.frontserver.main.adaptor.RecommendationAdaptor;
import com.t3t.frontserver.main.response.BookInfoBrief;
import com.t3t.frontserver.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final RecommendationAdaptor recommendationAdaptor;
    private final CategoryAdaptor categoryAdaptor;

    @GetMapping("/")
    public String homeView(Model model) {

        int defaultMaxCount = 10;

        // 현재 날짜를 기준으로 date 값을 생성
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<CategoryListResponse> categoryList = getDataFromCategoryAdaptor();
        List<BookInfoBrief> recentlyPublishedBookList = getDataFromRecommendationAdaptor(() -> recommendationAdaptor.getRecentlyPublishedBooks(formattedDate, defaultMaxCount));
        List<BookInfoBrief> mostLikeBookList = getDataFromRecommendationAdaptor(() -> recommendationAdaptor.getMostLikeBooks(defaultMaxCount));
        List<BookInfoBrief> bestSellerBookList = getDataFromRecommendationAdaptor(() -> recommendationAdaptor.getBestSellerBooks(defaultMaxCount));

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("recentlyPublishedBookList", recentlyPublishedBookList);
        model.addAttribute("mostLikeBookList", mostLikeBookList);
        model.addAttribute("bestSellerBookList", bestSellerBookList);

        return "main/page/home.html";
    }

    private List<CategoryListResponse> getDataFromCategoryAdaptor() {
        ResponseEntity<BaseResponse<List<CategoryListResponse>>> categoriesResponse = categoryAdaptor.getCategories();
        return handleResponse(categoriesResponse);
    }

    private List<BookInfoBrief> getDataFromRecommendationAdaptor(Supplier<ResponseEntity<BaseResponse<List<BookInfoBrief>>>> requestSupplier) {
        ResponseEntity<BaseResponse<List<BookInfoBrief>>> responseEntity = requestSupplier.get();
        return handleResponse(responseEntity);
    }

    private <T> List<T> handleResponse(ResponseEntity<BaseResponse<List<T>>> responseEntity) {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return Objects.requireNonNull(responseEntity.getBody()).getData();
        } else {
            throw new ApiDataFetchException(responseEntity.getStatusCodeValue());
        }
    }
}
