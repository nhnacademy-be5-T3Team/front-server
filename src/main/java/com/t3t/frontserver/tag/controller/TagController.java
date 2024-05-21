package com.t3t.frontserver.tag.controller;

import com.t3t.frontserver.book.model.dto.TagDto;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.tag.client.TagApiClient;
import com.t3t.frontserver.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TagController {
    private final TagApiClient tagApiClient;
    private final TagService tagService;

    @GetMapping("/tags")
    public ResponseEntity<BaseResponse<PageResponse<TagDto>>> getTagList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "tagId", required = false) String sortBy) {

        return tagApiClient.getTagList(pageNo, pageSize, sortBy);
    }

    /**
     * 태그 목록 조회
     * @param pageNo   태그 목록의 페이지 번호 (기본값: 0).
     * @param pageSize 페이지 당 항목 수 (기본값: 10).
     * @param sortBy   정렬 기준 필드 (기본값: tagId).
     * @return 태그 목록 페이지
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/admin/tags")
    public String getTagListAdmin(Model model,
          @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
          @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
          @RequestParam(value = "sortBy", defaultValue = "tagId", required = false) String sortBy) {

        PageResponse<TagDto> tagList = tagService.getTagList(pageNo, pageSize, sortBy);

        if (tagList != null) {
            int blockLimit = 5; // 현재 페이지 앞뒤로 보여줄 개수 설정
            int nowPage = tagList.getPageNo() + 1;
            int startPage = Math.max(nowPage - blockLimit, 1);
            int endPage = Math.min(nowPage + blockLimit, tagList.getTotalPages());

            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("tagList", tagList.getContent());
        }
        return "admin/page/tagList";
    }

    @GetMapping("/admin/tags/{tagId}/edit")
    public String getModifyTagAdmin(Model model, @PathVariable Long tagId){
        TagDto tag = tagService.getTag(tagId);
        model.addAttribute("tag", tag);
        return "admin/page/editTag";
    }
}
