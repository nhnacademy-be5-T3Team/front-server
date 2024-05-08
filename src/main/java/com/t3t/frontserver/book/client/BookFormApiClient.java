package com.t3t.frontserver.book.client;

import com.t3t.frontserver.book.model.request.RegisterBookRequest;
import com.t3t.frontserver.config.FormConfiguration;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Feign을 사용하여 멀티파트 폼 데이터를 전송하는 BookFormApiClient 인터페이스
 * 이 인터페이스는 Feign을 사용하여 원격 서버에 HTTP 요청을 보내고, BookRegisterRequest 객체를 멀티파트 폼 데이터로 전송함
 * @author Yujin-nKim(김유진)
 */
@FeignClient(name = "bookFormApiClient", url = "${t3t.feignClient.url}", configuration = FormConfiguration.class)
public interface BookFormApiClient {

    /**
     * 새 책을 생성하는 POST 요청
     * 요청 바디에는 BookRegisterRequest 객체가 멀티파트 폼 데이터로 전송됨
     *
     * @param request 책을 등록하기 위한 요청 객체
     * @return 책 생성 요청에 대한 응답
     * @author Yujin-nKim(김유진)
     */
    @PostMapping(value = "/t3t/bookstore/books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<BaseResponse<Long>> createBook(@ModelAttribute RegisterBookRequest request);

    /**
     * 특정 도서의 썸네일을 수정
     * @param bookId  수정할 도서의 식별자
     * @param image   수정할 썸네일 이미지
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping(value = "/t3t/bookstore/books/{bookId}/book-thumbnail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<BaseResponse<Void>> updateBookThumbnail(@PathVariable Long bookId,
                                                           @ModelAttribute MultipartFile image);

    /**
     * 특정 도서의 이미지를 수정
     * @param bookId     수정할 도서의 식별자
     * @param imageList  수정할 이미지 리스트
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/t3t/bookstore/books/{bookId}/book-image")
    ResponseEntity<BaseResponse<Void>> updateBookImage(@PathVariable Long bookId,
                                                       @ModelAttribute List<MultipartFile> imageList);

}