package com.t3t.frontserver.elastic.adaptor;

import com.t3t.frontserver.elastic.model.response.ElasticResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "ElasticAdaptor", url = "${t3t.feignClient.url}")
public interface ElasticAdaptor {
    /**
     *
     * elasticsearch 기반 text 검색
     *
     * @param query      text 검색어
     * @param searchType 검색 유형
     * @param pageNo     페이지 번호
     * @param categoryId  카테고리 검색을 위한 카테고리번호
     * @param sortBy     정렬 기준 (기본값: "_socre")
     * @return 서버의 데이터를 가지고 옴
     */
    @GetMapping("/t3t/bookstore/search")
    ResponseEntity<BaseResponse<PageResponse<ElasticResponse>>>
    getSearchPage(@RequestParam String query,
                  @RequestParam String searchType,
                  @RequestParam int pageNo,
                  @RequestParam(value = "categoryId",required = false) BigDecimal categoryId,
                  @RequestParam(value = "sortBy", defaultValue = "_score", required = false) String sortBy);
}
