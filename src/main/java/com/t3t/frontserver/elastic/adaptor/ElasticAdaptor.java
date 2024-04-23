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

@FeignClient(name = "ElasticAdaptor", url = "${t3t.feignClient.url}")
public interface ElasticAdaptor {
    @PostMapping("/search")
    ResponseEntity<BaseResponse<PageResponse<ElasticResponse>>> getSearchPage(@RequestParam String query,
                                                                              @RequestParam int pageNo);
}
