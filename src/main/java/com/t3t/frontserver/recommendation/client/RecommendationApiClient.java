package com.t3t.frontserver.recommendation.client;

import com.t3t.frontserver.main.response.BookInfoBrief;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "recommendationAdaptor", url = "${t3t.feignClient.url}")
public interface RecommendationApiClient {
    @GetMapping(value = "/t3t/bookstore/recommendations/recentlyPublished")
    ResponseEntity<BaseResponse<List<BookInfoBrief>>> getRecentlyPublishedBooks(
            @RequestParam("date") String date, @RequestParam("maxCount") int maxCount);

    @GetMapping(value = "/t3t/bookstore/recommendations/mostLike")
    ResponseEntity<BaseResponse<List<BookInfoBrief>>> getBooksByMostLikedAndHighAverageScore(
            @RequestParam("maxCount") int maxCount);

    @GetMapping(value = "/t3t/bookstore/recommendations/bestSeller")
    ResponseEntity<BaseResponse<List<BookInfoBrief>>> getBestSellerBooks(
            @RequestParam("maxCount") int maxCount);
}
