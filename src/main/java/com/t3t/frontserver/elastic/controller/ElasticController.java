package com.t3t.frontserver.elastic.controller;

import com.t3t.frontserver.category.client.CategoryApiClient;
import com.t3t.frontserver.category.response.CategoryTreeResponse;
import com.t3t.frontserver.elastic.client.ElasticClient;
import com.t3t.frontserver.elastic.model.response.ElasticResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.t3t.frontserver.util.ServiceUtils.handleResponse;

@Controller
@RequiredArgsConstructor
public class ElasticController {
    private final ElasticClient elasticAdaptor;
    private final CategoryApiClient categoryAdaptor;
    /**
     *
     * elasticsearch 기반 text 검색
     *
     * @param query      text 검색어
     * @param searchType 검색 유형
     * @param pageNo     페이지 번호
     * @param sortBy     정렬 기준 (기본값: "_socre")
     * @return 페이지로 정보를 가지고 이동
     */
    @GetMapping("/search")
    public String searchBooks(@RequestParam(value = "query") String query,
                              @RequestParam("searchType") String searchType,
                              @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                              @RequestParam(value = "sortBy", defaultValue = "_score", required = false) String sortBy,
                              Model model) {
            List<CategoryTreeResponse> categoryList = getDataFromCategoryAdaptor(1, 2);
            PageResponse<ElasticResponse> bookList = getSearchPageAdaptor(query, searchType, pageNo, sortBy);

            if (bookList != null) {
                int blockLimit = 3;
                int nowPage = bookList.getPageNo() + 1;
                int startPage = Math.max(nowPage - blockLimit, 1);
                int endPage = Math.min(nowPage + blockLimit, bookList.getTotalPages());

                model.addAttribute("nowPage", nowPage);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
                model.addAttribute("bookList", bookList.getContent());
                model.addAttribute("query", query); //페이징을 위한 검색어
                model.addAttribute("searchType", searchType);//페이징을 위한 검색유형
                model.addAttribute("sortBy", sortBy); //정렬 방식을 위한 객체

                model.addAttribute("categoryList", categoryList);
            }

            return "main/page/elasticSearch";

    }
    private PageResponse<ElasticResponse> getSearchPageAdaptor(String query,
                                                               String searchType,
                                                               int pageNo,
                                                               String sortBy) {
        ResponseEntity<BaseResponse<PageResponse<ElasticResponse>>> elasticResponse
                    = elasticAdaptor.getSearchPage(query, searchType, pageNo, sortBy);

        return handleResponse(elasticResponse);
    }
    private List<CategoryTreeResponse> getDataFromCategoryAdaptor(Integer startDepth, Integer maxDepth) {
        ResponseEntity<BaseResponse<List<CategoryTreeResponse>>> categoriesResponse = categoryAdaptor.getCategoryTreeByDepth(startDepth, maxDepth);
        return handleResponse(categoriesResponse);
    }
}
