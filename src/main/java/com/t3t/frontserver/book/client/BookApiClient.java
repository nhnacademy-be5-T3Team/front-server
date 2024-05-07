package com.t3t.frontserver.book.client;

import com.t3t.frontserver.book.model.request.ModifyBookDetailRequest;
import com.t3t.frontserver.book.model.response.BookDetailResponse;
import com.t3t.frontserver.book.model.response.BookListResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name = "bookApiClient", url = "${t3t.feignClient.url}")
public interface BookApiClient {
    @GetMapping(value = "/t3t/bookstore/books/{bookId}")
    ResponseEntity<BaseResponse<BookDetailResponse>> getBook(@PathVariable Long bookId);

    /**
     * 도서 목록을 페이징하여 가져오는 요청을 처리
     * @param pageNo   페이지 번호
     * @param pageSize 페이지 크기
     * @return 도서 목록과 관련된 응답 데이터를 포함하는 ResponseEntity
     * @author Yujin-nKim(김유진)
     */
    @GetMapping(value = "/t3t/bookstore/books")
    ResponseEntity<BaseResponse<PageResponse<BookListResponse>>> getAllBooks(@RequestParam int pageNo, @RequestParam int pageSize);

    /**
     * 특정 도서의 상세 정보를 수정
     * @param bookId 수정할 도서의 식별자
     * @param request 수정할 도서의 상세 정보를 담은 요청 객체
     * @return  200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping(value = "/t3t/bookstore/books/{bookId}/book-detail")
    ResponseEntity<BaseResponse<Void>> updateBookDetail(@PathVariable Long bookId,
                                                        @RequestBody @Valid ModifyBookDetailRequest request);

    @DeleteMapping(value = "/books/{bookId}")
    ResponseEntity<BaseResponse<Void>> deleteBook(@PathVariable Long bookId);
}