package com.t3t.frontserver.main.controller;

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

@Controller
@RequiredArgsConstructor
public class MainController {
    private final RecommendationAdaptor recommendationAdaptor;

    @GetMapping("/")
    public String homeView(Model model) {

        int defaultMaxCount = 10;

        // 현재 날짜를 기준으로 date 값을 생성
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        ResponseEntity<BaseResponse<List<BookInfoBrief>>> recentlyPublishedBookResponse = recommendationAdaptor.getRecentlyPublishedBooks(formattedDate, defaultMaxCount);
        ResponseEntity<BaseResponse<List<BookInfoBrief>>> mostLikeBooksResponse = recommendationAdaptor.getMostLikeBooks(defaultMaxCount);
        ResponseEntity<BaseResponse<List<BookInfoBrief>>> bestSellerBooksResponse = recommendationAdaptor.getBestSellerBooks(defaultMaxCount);

        List<BookInfoBrief> recentlyPublishedBookList = recentlyPublishedBookResponse.getBody().getData();
        List<BookInfoBrief> mostLikeBookList = mostLikeBooksResponse.getBody().getData();
        List<BookInfoBrief> bestSellerBookList = bestSellerBooksResponse.getBody().getData();

        model.addAttribute("recentlyPublishedBookList", recentlyPublishedBookList);
        model.addAttribute("mostLikeBookList", mostLikeBookList);
        model.addAttribute("bestSellerBookList", bestSellerBookList);

        return "main/page/home.html";
    }
}
