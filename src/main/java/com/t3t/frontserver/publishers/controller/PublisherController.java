package com.t3t.frontserver.publishers.controller;

import com.t3t.frontserver.book.model.dto.PublisherDto;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.publishers.client.PublisherApiClient;
import com.t3t.frontserver.publishers.service.PublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PublisherController {

    private final PublisherApiClient publisherApiClient;
    private final PublisherService publisherService;

    @GetMapping("/publishers")
    public ResponseEntity<BaseResponse<PageResponse<PublisherDto>>> getPublisherList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "publisherId", required = false) String sortBy) {

        return publisherApiClient.getPublisherList(pageNo, pageSize, sortBy);
    }

    @GetMapping("/admin/publishers")
    public String getPublisherListAdmin(Model model,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "publisherId", required = false) String sortBy) {

        PageResponse<PublisherDto> publisherList = publisherService.getPublisherList(pageNo, 20, sortBy);

        if (publisherList != null) {
            int blockLimit = 5; // 현재 페이지 앞뒤로 보여줄 개수 설정
            int nowPage = publisherList.getPageNo() + 1;
            int startPage = Math.max(nowPage - blockLimit, 1);
            int endPage = Math.min(nowPage + blockLimit, publisherList.getTotalPages());

            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("publisherList", publisherList.getContent());
        }
        return "admin/page/publisherList";
    }
}
