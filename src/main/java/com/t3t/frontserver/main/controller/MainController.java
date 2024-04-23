package com.t3t.frontserver.main.controller;

import com.t3t.frontserver.category.client.CategoryApiClient;
import com.t3t.frontserver.category.response.CategoryTreeResponse;
import com.t3t.frontserver.recommendation.client.RecommendationApiClient;
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
import java.util.function.Supplier;

import static com.t3t.frontserver.util.ServiceUtils.handleResponse;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final RecommendationApiClient recommendationAdaptor;
    private final CategoryApiClient categoryAdaptor;

    @GetMapping("/")
    public String homeView(Model model) {

        int defaultMaxCount = 10;

        // 현재 날짜를 기준으로 date 값을 생성
        LocalDate currentDate = LocalDate.now();
        //String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String formattedDate = "2024-04-06"; // 테스트를 위해 값을 고정, 실제 배포시에는 삭제 예정
        List<CategoryTreeResponse> categoryList = getDataFromCategoryAdaptor(1, 2);
        List<BookInfoBrief> recentlyPublishedBookList = getDataFromRecommendationAdaptor(() -> recommendationAdaptor.getRecentlyPublishedBooks(formattedDate, defaultMaxCount));
        List<BookInfoBrief> mostLikeBookList = getDataFromRecommendationAdaptor(() -> recommendationAdaptor.getBooksByMostLikedAndHighAverageScore(defaultMaxCount));
        List<BookInfoBrief> bestSellerBookList = getDataFromRecommendationAdaptor(() -> recommendationAdaptor.getBestSellerBooks(defaultMaxCount));


        model.addAttribute("categoryList", categoryList);
        model.addAttribute("recentlyPublishedBookList", recentlyPublishedBookList);
        model.addAttribute("mostLikeBookList", mostLikeBookList);
        model.addAttribute("bestSellerBookList", bestSellerBookList);

        return "main/page/home.html";
    }

    private List<CategoryTreeResponse> getDataFromCategoryAdaptor(Integer startDepth, Integer maxDepth ) {
        ResponseEntity<BaseResponse<List<CategoryTreeResponse>>> categoriesResponse = categoryAdaptor.getCategoryTreeByDepth(startDepth, maxDepth);
        return handleResponse(categoriesResponse);
    }

    private List<BookInfoBrief> getDataFromRecommendationAdaptor(Supplier<ResponseEntity<BaseResponse<List<BookInfoBrief>>>> requestSupplier) {
        ResponseEntity<BaseResponse<List<BookInfoBrief>>> responseEntity = requestSupplier.get();
        return handleResponse(responseEntity);
    }
}
