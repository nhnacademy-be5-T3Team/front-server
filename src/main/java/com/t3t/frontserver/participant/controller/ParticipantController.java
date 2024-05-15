package com.t3t.frontserver.participant.controller;

import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.participant.client.ParticipantApiClient;
import com.t3t.frontserver.participant.dto.ParticipantDto;
import com.t3t.frontserver.participant.dto.ParticipantRoleDto;
import com.t3t.frontserver.participant.service.ParticipantService;
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
public class ParticipantController {
    private final ParticipantApiClient participantApiClient;
    private final ParticipantService participantService;

    @GetMapping("/participants")
    ResponseEntity<BaseResponse<PageResponse<ParticipantDto>>> getParticipantList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "participantId", required = false) String sortBy) {

        return participantApiClient.getParticipantList(pageNo, pageSize, sortBy);
    }

    @GetMapping("/participantRoles")
    ResponseEntity<BaseResponse<PageResponse<ParticipantRoleDto>>> getParticipantRoleList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "participantRoleId", required = false) String sortBy) {

        return participantApiClient.getParticipantRoleList(pageNo, pageSize, sortBy);
    }

    @GetMapping("/admin/participants")
    public String getParticipantListAdmin(Model model,
                                        @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                        @RequestParam(value = "sortBy", defaultValue = "participantId", required = false) String sortBy) {

        PageResponse<ParticipantDto> participantList = participantService.getParticipantList(pageNo, 20, sortBy);

        if (participantList != null) {
            int blockLimit = 5; // 현재 페이지 앞뒤로 보여줄 개수 설정
            int nowPage = participantList.getPageNo() + 1;
            int startPage = Math.max(nowPage - blockLimit, 1);
            int endPage = Math.min(nowPage + blockLimit, participantList.getTotalPages());

            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("participantList", participantList.getContent());
        }
        return "admin/page/participantList";
    }
}
