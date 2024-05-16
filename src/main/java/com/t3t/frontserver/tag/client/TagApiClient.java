package com.t3t.frontserver.tag.client;

import com.t3t.frontserver.book.model.dto.TagDto;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tagApiClient", url = "${t3t.feignClient.url}")
public interface TagApiClient {

    /**
     * 태그 목록 조회
     * @param pageNo   태그 목록의 페이지 번호 (기본값: 0).
     * @param pageSize 페이지 당 항목 수 (기본값: 10).
     * @param sortBy   정렬 기준 필드 (기본값: tagId).
     * @return 200 OK, 태그 목록을 포함한 ResponseEntity. <br>
     *         204 NO_CONTENT, 데이터가 없는 경우 메시지 반환
     * @author Yujin-nKim(김유진)
     */
    @GetMapping(value = "/t3t/bookstore/tags")
    ResponseEntity<BaseResponse<PageResponse<TagDto>>> getTagList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "tagId", required = false) String sortBy);


    /**
     * 태그 수정 요청
     * @param tagId 수정할 태그의 식별자
     * @param tagName 태그 이름
     * @return 200 OK, 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/t3t/bookstore/tags/{tagId}")
    ResponseEntity<BaseResponse<Void>> modifyTag(@PathVariable Long tagId, @RequestParam String tagName);

    /**
     * 태그 상세  조회
     * @param tagId 수정할 태그의 식별자
     * @return 태그 상세
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/t3t/bookstore/tags/{tagId}")
    ResponseEntity<BaseResponse<TagDto>> getTag(@PathVariable Long tagId);

}
