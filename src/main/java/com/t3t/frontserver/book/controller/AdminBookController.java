package com.t3t.frontserver.book.controller;

import com.t3t.frontserver.book.client.BookApiClient;
import com.t3t.frontserver.book.client.BookFormApiClient;
import com.t3t.frontserver.book.model.dto.ParticipantMapDto;
import com.t3t.frontserver.book.model.request.RegisterBookRequest;
import com.t3t.frontserver.book.model.request.ModifyBookDetailRequest;
import com.t3t.frontserver.book.model.response.BookDetailResponse;
import com.t3t.frontserver.book.model.response.BookListResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/books")
@Controller
public class AdminBookController {

    private final BookApiClient bookApiClient;
    private final BookFormApiClient bookFormApiClient;

    /**
     * 도서 등록 페이지를 요청
     * @param model 데이터를 뷰에 전달하기 위한 Model 객체
     * @return 도서 등록 페이지 반환
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/new")
    public String getRegisterBookAdminPage(Model model) {
        model.addAttribute("bookRegisterRequest", new RegisterBookRequest());
        return "admin/page/registerBook";
    }

    /**
     * 도서 수정 페이지를 요청
     * @param model 데이터를 뷰에 전달하기 위한 Model 객체
     * @param bookId  수정할 책의 ID
     * @return 도서 수정 페이지 반환
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/{bookId}/edit")
    public String getEditBookAdminPage(Model model, @PathVariable Long bookId) {
        try {
            ResponseEntity<BaseResponse<BookDetailResponse>> response = bookApiClient.getBook(bookId);
            model.addAttribute("bookDetail", Objects.requireNonNull(response.getBody()).getData());
            model.addAttribute("modifyBookDetailRequest", new ModifyBookDetailRequest());
        } catch (FeignException exception) {
            log.error(exception.getMessage());
            model.addAttribute("errorMessage", "데이터를 가져오는데 실패했습니다.");
        }
        return "admin/page/editBook";
    }

    /**
     * 도서 목록을 조회
     * @param model    데이터를 뷰에 전달하기 위한 Model 객체
     * @param pageNo   요청된 페이지 번호 (기본값은 0이며, 0부터 시작)
     * @param pageSize 페이지당 도서 수 (기본값은 20)
     * @return 도서 목록 조회 페이지 반환
     * @author Yujin-nKim(김유진)
     */
    @GetMapping
    public String getBookListAdminPage(Model model,
                                       @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                       @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize) {

        try {
            ResponseEntity<BaseResponse<PageResponse<BookListResponse>>> response = bookApiClient.getAllBooks(pageNo, pageSize);
            if (response.getStatusCode() == HttpStatus.OK) {
                PageResponse<BookListResponse> bookList = Objects.requireNonNull(response.getBody()).getData();
                if (bookList != null) {
                    int blockLimit = 5; // 현재 페이지 앞뒤로 보여줄 개수 설정
                    int nowPage = bookList.getPageNo() + 1;
                    int startPage = Math.max(nowPage - blockLimit, 1);
                    int endPage = Math.min(nowPage + blockLimit, bookList.getTotalPages());

                    model.addAttribute("nowPage", nowPage);
                    model.addAttribute("startPage", startPage);
                    model.addAttribute("endPage", endPage);
                    model.addAttribute("bookList", bookList.getContent());
                }
            } else {
                log.info(response.getStatusCode().toString());
            }
        } catch (FeignException exception) {
            log.error(exception.getMessage());
            model.addAttribute("errorMessage", "데이터를 가져오는데 실패했습니다.");
        }
        return "admin/page/bookList";
    }

    /**
     * 도서를 등록하는 요청을 처리
     * @param request 등록하고자 하는 도서 정보를 담고 있는 BookRegisterRequest 객체
     * @author Yujin-nKim(김유진)
     */
    @PostMapping
    public String createBook(@ModelAttribute(value = "bookRegisterRequest") RegisterBookRequest request, RedirectAttributes redirectAttributes) {

        log.info("도서 등록 요청 = {}", request.toString());

        try {
            ResponseEntity<BaseResponse<Long>> response = bookFormApiClient.createBook(request);
            Long bookId = Objects.requireNonNull(response.getBody()).getData();
            String message = response.getBody().getMessage();
            redirectAttributes.addFlashAttribute("successMessage", message + "\n저장된 도서 Id : " + bookId);
            return "redirect:/admin/books/new";
        } catch (FeignException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("bookRegisterRequest", request);
            redirectAttributes.addFlashAttribute("errorMessage", "도서 등록에 실패했습니다.");
            return "redirect:/admin/books/new";
        }
    }

    /**
     * 특정 도서의 상세 정보를 수정
     * @param bookId 수정할 도서의 식별자
     * @param request 수정할 도서의 상세 정보를 담은 요청 객체
     * @return  200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PostMapping("/{bookId}/edit")
    public String updateBookDetail(@PathVariable Long bookId, @ModelAttribute(value = "modifyBookDetailRequest") ModifyBookDetailRequest request, RedirectAttributes redirectAttributes) {

        log.info("도서 상세 정보 수정 요청 = {}", request.toString());

        try {
            ResponseEntity<BaseResponse<Void>> response = bookApiClient.updateBookDetail(bookId, request);
            redirectAttributes.addFlashAttribute("bookDetailModifySuccess", Objects.requireNonNull(response.getBody()).getMessage());
        } catch (FeignException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("bookDetailModifyError", "도서 상세 정보 수정에 실패했습니다.");
        }
        return "redirect:/admin/books/"+bookId+"/edit";
    }

    /**
     * 특정 도서의 출판사 정보를 수정
     * @param bookId 수정할 도서의 식별자
     * @param publisherId 수정할 출판사의 id
     * @return  200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/{bookId}/publisher")
    public String updateBookPublisher(@PathVariable Long bookId,
                                      @RequestParam Long publisherId,
                                      RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<BaseResponse<Void>> response = bookApiClient.updateBookPublisher(bookId, publisherId);
            redirectAttributes.addFlashAttribute("bookDetailModifySuccess", Objects.requireNonNull(response.getBody()).getMessage());
        } catch (FeignException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("bookDetailModifyError", "도서 출판사 정보 수정에 실패했습니다.");
        }
        return "redirect:/admin/books/"+bookId+"/edit";
    }

    /**
     * 특정 도서의 참여자를 수정
     * @param bookId          수정할 도서의 식별자
     * @param participantList 수정할 참여자 매핑 리스트
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/{bookId}/participant")
    public String updateBookParticipant(@PathVariable Long bookId,
                                        @RequestBody @Valid List<ParticipantMapDto> participantList,
                                        RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<BaseResponse<Void>> response = bookApiClient.updateBookParticipant(bookId, participantList);
            redirectAttributes.addFlashAttribute("bookDetailModifySuccess", Objects.requireNonNull(response.getBody()).getMessage());
        } catch (FeignException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("bookDetailModifyError", "도서 참여자 정보 수정에 실패했습니다.");
        }
        return "redirect:/admin/books/"+bookId+"/edit";
    }

    /**
     * 특정 도서의 태그를 수정
     * @param bookId   수정할 도서의 식별자
     * @param tagList  수정할 태그 리스트
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/{bookId}/tag")
    public String updateBookTag(@PathVariable Long bookId, @RequestBody @Valid List<Long> tagList,
                                RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<BaseResponse<Void>> response = bookApiClient.updateBookTag(bookId, tagList);
            redirectAttributes.addFlashAttribute("bookDetailModifySuccess", Objects.requireNonNull(response.getBody()).getMessage());
        } catch (FeignException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("bookDetailModifyError", "도서 태그 정보 수정에 실패했습니다.");
        }
        return "redirect:/admin/books/"+bookId+"/edit";
    }

    /**
     * 특정 도서의 카테고리를 수정
     * @param bookId       수정할 도서의 식별자
     * @param categoryList 수정할 카테고리 리스트
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("{bookId}/category")
    public String updateBookCategory(@PathVariable Long bookId,
                                     @RequestBody @Valid List<Integer> categoryList,
                                     RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<BaseResponse<Void>> response = bookApiClient.updateBookCategory(bookId, categoryList);
            redirectAttributes.addFlashAttribute("bookDetailModifySuccess", Objects.requireNonNull(response.getBody()).getMessage());
        } catch (FeignException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("bookDetailModifyError", "도서 카테고리 정보 수정에 실패했습니다.");
        }
        return "redirect:/admin/books/"+bookId+"/edit";
    }

    /**
     * 도서 삭제 요청을 처리
     * @param bookId 삭제하고자 하는 도서 id
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/{bookId}/delete")
    public String deleteBook(@PathVariable Long bookId, RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<BaseResponse<Void>> response = bookApiClient.deleteBook(bookId);
            redirectAttributes.addFlashAttribute("successMessage", response.getBody().getMessage());
        } catch (FeignException e) {
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "도서 삭제에 실패했습니다.");
        }
        return "redirect:/admin/books";
    }
}
