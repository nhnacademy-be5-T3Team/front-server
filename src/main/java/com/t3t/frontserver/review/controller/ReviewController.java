package com.t3t.frontserver.review.controller;

import com.t3t.frontserver.auth.util.SecurityContextUtils;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.review.model.request.ReviewCommentUpdateRequest;
import com.t3t.frontserver.review.model.request.ReviewRegisterRequest;
import com.t3t.frontserver.review.model.response.ReviewResponse;
import com.t3t.frontserver.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ReviewController {
    private final ReviewService reviewService;

    /**
     * 리뷰 점수를 가져오는 메서드
     * @return 리뷰 점수를 담고 있는 맵 객체 <br>
     *         key - 점수를 나타내는 문자열, value - 값은 해당 점수를 나타내는 정수
     * @author Yujin-nKim(김유진)
     */
    @ModelAttribute("score")
    public Map<Integer, String> reviews() {
        Map<Integer, String> score = new LinkedHashMap<>();
        score.put(1, "1점");
        score.put(2, "2점");
        score.put(3, "3점");
        score.put(4, "4점");
        score.put(5, "5점");
        return score;
    }

    /**
     * 리뷰 등록 가능 여부를 확인 후 리뷰 등록 페이지로 이동 또는 리다이렉트
     * @param model             뷰에 전달할 데이터를 담는 모델 객체
     * @param redirectAttributes 리다이렉트 속성 객체
     * @param bookId            리뷰를 작성할 도서의 식별자
     * @return 리뷰 등록 페이지 뷰
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/reviews/register")
    public String reviewPage(Model model, RedirectAttributes redirectAttributes, @RequestParam Long bookId) {

        Long memberId = SecurityContextUtils.getMemberId();

        // 현재 회원이 해당 도서에 대해 이미 리뷰를 작성했는지 확인
        if(!reviewService.existsReview(memberId, bookId)) {
            // 리뷰를 작성하지 않은 경우 리뷰 작성 페이지로 이동
            model.addAttribute("reviewRegisterRequest", new ReviewRegisterRequest());
            model.addAttribute("bookId", bookId);
            return "main/page/registerReview";
        }

        // 이미 리뷰를 작성한 경우, 메세지와 함께 리다이렉트
        redirectAttributes.addFlashAttribute("message", "이미 리뷰를 작성했습니다.");
        return "redirect:/mypage/order";
    }

    /**
     * 리뷰 수정 페이지 요청
     * @param model 뷰에 전달할 데이터를 담는 모델 객체
     * @param reviewId 리뷰 ID
     * @return 요청 수정 페이지
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/reviews/edit")
    public String reviewEditPage(Model model, @ModelAttribute("score") Map<Integer, String> score, @RequestParam Long reviewId) {
        ReviewResponse review = reviewService.findReviewByReviewId(reviewId);
        model.addAttribute("review", review);
        model.addAttribute("score", score);
        return "main/page/editReview";
    }

    /**
     * 리뷰 생성 요청
     * @param request 리뷰 생성 요청 객체
     * @param bindingResult 데이터 바인딩 프로세스의 결과
     * @param redirectAttributes 리디렉션시에 사용되는 속성
     * @return 요청 처리 후 리디렉션할 뷰
     * @author Yujin-nKim(김유진)
     */
    @PostMapping("/reviews")
    public String createReview(@ModelAttribute @Valid ReviewRegisterRequest request, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // 바인딩 오류가 발생한 경우
            log.error(bindingResult.toString());
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()).toString();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/reviews/register?bookId=" + request.getBookId();
        }

        log.info("리뷰 생성 요청 : {}", request.toString());
        reviewService.createReview(request);
        return "redirect:/mypage/order";
    }

    /**
     * 사용자 ID에 해당하는 리뷰 페이지 조회
     * @param pageNo   페이지 번호
     * @param pageSize 페이지 크기
     * @param sortBy   정렬 기준
     * @return 주어진 회원 ID에 대한 리뷰 페이지 응답
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/mypage/review")
    public String findReviewsByMemberId(Model model,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "reviewCreatedAt", required = false) String sortBy) {

        PageResponse<ReviewResponse> reviewList = reviewService.findReviewsByMemberId(pageNo, pageSize, sortBy);

        if (reviewList != null) {
            int blockLimit = 5; // 현재 페이지 앞뒤로 보여줄 개수 설정
            int nowPage = reviewList.getPageNo() + 1;
            int startPage = Math.max(nowPage - blockLimit, 1);
            int endPage = Math.min(nowPage + blockLimit, reviewList.getTotalPages());

            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("reviewList", reviewList.getContent());
        }

        return "main/page/mypageReview";
    }

    /**
     * 리뷰 comment 수정 요청
     * @param reviewId 리뷰 ID
     * @param request 리뷰 comment 수정 요청 객체
     * @return 요청 처리 후 리디렉션할 뷰
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/reviews/{reviewId}/comment")
    public String updateReviewDetail(@PathVariable Long reviewId, @RequestBody ReviewCommentUpdateRequest request) {
        reviewService.updateReviewDetail(reviewId, request);
        return "redirect:/reviews/edit?reviewId="+reviewId;
    }

    /**
     * 리뷰 score 수정 요청
     * @param reviewId 리뷰 ID
     * @param score 수정할 리뷰 평점
     * @return 요청 처리 후 리디렉션할 뷰
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/reviews/{reviewId}/score")
    public String updateReviewScore(@PathVariable Long reviewId, @RequestParam Integer score) {
        reviewService.updateReviewScore(reviewId, score);
        return "redirect:/reviews/edit?reviewId="+reviewId;
    }

    /**
     * 리뷰 이미지 delete 요청
     * @param reviewId 리뷰 ID
     * @param reviewImageName 삭제할 리뷰 이미지
     * @return 요청 처리 후 리디렉션할 뷰
     * @author Yujin-nKim(김유진)
     */
    @DeleteMapping("/reviews/{reviewId}/image")
    public String deleteReviewImage(@PathVariable Long reviewId, @RequestParam String reviewImageName) {
        reviewService.deleteReviewImage(reviewImageName);
        return "redirect:/reviews/edit?reviewId="+reviewId;
    }

    /**
     * 리뷰 이미지 추가 요청
     * @param reviewId 리뷰 ID
     * @param imageList 추가할 리뷰 이미지 리스트
     * @return 요청 처리 후 리디렉션할 뷰
     * @author Yujin-nKim(김유진)
     */
    @PostMapping("/reviews/{reviewId}/image")
    public String addReviewImage(@PathVariable Long reviewId,
                                 @ModelAttribute List<MultipartFile> imageList) {

        reviewService.addReviewImage(reviewId, imageList);
        return "redirect:/reviews/edit?reviewId="+reviewId;
    }
}
