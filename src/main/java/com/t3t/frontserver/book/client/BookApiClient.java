package com.t3t.frontserver.book.client;

import com.t3t.frontserver.book.model.dto.ParticipantMapDto;
import com.t3t.frontserver.book.model.request.ModifyBookDetailRequest;
import com.t3t.frontserver.book.model.response.BookDetailResponse;
import com.t3t.frontserver.book.model.response.BookListResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "bookApiClient", url = "${t3t.feignClient.url}")
public interface BookApiClient {
    /**
     * 책의 상세 정보를 조회
     * @param bookId 도서의 ID
     * @return 200 OK, 성공 메세지
     */
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

    /**
     * 특정 도서의 출판사 정보를 수정
     * @param bookId 수정할 도서의 식별자
     * @param publisherId 수정할 출판사의 id
     * @return  200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/t3t/bookstore/books/{bookId}/publisher")
    ResponseEntity<BaseResponse<Void>> updateBookPublisher(@PathVariable Long bookId,
                                                           @RequestParam Long publisherId);

    /**
     * 특정 도서의 참여자를 수정
     * @param bookId          수정할 도서의 식별자
     * @param participantList 수정할 참여자 매핑 리스트
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/t3t/bookstore/books/{bookId}/participant")
    ResponseEntity<BaseResponse<Void>> updateBookParticipant(@PathVariable Long bookId,
                                                             @RequestBody @Valid List<ParticipantMapDto> participantList);

    /**
     * 특정 도서의 태그를 수정
     * @param bookId   수정할 도서의 식별자
     * @param tagList  수정할 태그 리스트
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/t3t/bookstore/books/{bookId}/tag")
    ResponseEntity<BaseResponse<Void>> updateBookTag(@PathVariable Long bookId,
                                                     @RequestBody @Valid List<Long> tagList);

    /**
     * 특정 도서의 카테고리를 수정
     * @param bookId       수정할 도서의 식별자
     * @param categoryList 수정할 카테고리 리스트
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/t3t/bookstore/books/{bookId}/category")
    ResponseEntity<BaseResponse<Void>> updateBookCategory(@PathVariable Long bookId,
                                                          @RequestBody @Valid List<Integer> categoryList);

    /**
     * 도서 삭제 요청을 처리
     * @param bookId 삭제하고자 하는 도서 id
     * @author Yujin-nKim(김유진)
     */
    @DeleteMapping(value = "/t3t/bookstore/books/{bookId}")
    ResponseEntity<BaseResponse<Void>> deleteBook(@PathVariable Long bookId);
}