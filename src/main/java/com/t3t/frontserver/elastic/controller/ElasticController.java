package com.t3t.frontserver.elastic.controller;

import com.t3t.frontserver.elastic.adaptor.ElasticAdaptor;
import com.t3t.frontserver.elastic.model.response.ElasticResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.t3t.frontserver.util.ServiceUtils.handleResponse;

@Controller
@RequiredArgsConstructor
public class ElasticController {
    private final ElasticAdaptor elasticAdaptor;

    @GetMapping("/search")
    public String searchBooks(@RequestParam(value = "query") String query,
                              @RequestParam("searchType") String searchType,
                              @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                              @RequestParam(value = "sortBy", defaultValue = "_score", required = false) String sortBy,
                              Model model) {

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
            model.addAttribute("query",query); //페이징을 위한 검색어
            model.addAttribute("searchType",searchType);//페이징을 위한 검색유형
            model.addAttribute("sortBy",sortBy); //정렬 방식을 위한 객체
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
}
