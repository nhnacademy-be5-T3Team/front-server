package com.t3t.frontserver.main.adaptor;

import com.t3t.frontserver.main.response.BookInfoBrief;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "recommendationAdaptor", url = "http://localhost:8081")
public interface RecommendationAdaptor {
    @GetMapping(value = "/recommendations/recentlyPublished")
    ResponseEntity<BaseResponse<List<BookInfoBrief>>> getRecentlyPublishedBooks(
            @RequestParam("date") String date, @RequestParam("maxCount") int maxCount);

    @GetMapping(value = "/recommendations/mostLike")
    ResponseEntity<BaseResponse<List<BookInfoBrief>>> getMostLikeBooks(
            @RequestParam("maxCount") int maxCount);

    @GetMapping(value = "/recommendations/bestSeller")
    ResponseEntity<BaseResponse<List<BookInfoBrief>>> getBestSellerBooks(
            @RequestParam("maxCount") int maxCount);
}
