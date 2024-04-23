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
    @PostMapping("/search")
    public String searchBooks(@RequestParam(value = "query") String query,
                              @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo, Model model) {
        PageResponse<ElasticResponse> bookList = getSearchPageAdaptor(query,pageNo);

        if (bookList != null) {
            int blockLimit = 3;
            int nowPage = bookList.getPageNo() + 1;
            int startPage = Math.max(nowPage - blockLimit, 1);
            int endPage = Math.min(nowPage + blockLimit, bookList.getTotalPages());

            model.addAttribute("hNowPage", nowPage);
            model.addAttribute("StartPage", startPage);
            model.addAttribute("EndPage", endPage);
            model.addAttribute("bookList", bookList.getContent());
        }

        return "main/page/search";
    }
    private PageResponse<ElasticResponse> getSearchPageAdaptor(String query, int pageNo) {
        ResponseEntity<BaseResponse<PageResponse<ElasticResponse>>> elasticResponse = elasticAdaptor.getSearchPage(query,
                pageNo);
        return handleResponse(elasticResponse);
    }
}
