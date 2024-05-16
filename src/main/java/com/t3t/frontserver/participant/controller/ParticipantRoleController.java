package com.t3t.frontserver.participant.controller;

import com.t3t.frontserver.book.model.dto.PublisherDto;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.participant.dto.ParticipantRoleDto;
import com.t3t.frontserver.participant.service.ParticipantRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ParticipantRoleController {
    private final ParticipantRoleService participantRoleService;

    @GetMapping("/admin/participantRoles")
    public String getParticipantRoleListAdmin(Model model,
                                        @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                        @RequestParam(value = "sortBy", defaultValue = "participantRoleId", required = false) String sortBy) {

        PageResponse<ParticipantRoleDto> participantRoleList = participantRoleService.getParticipantRoleList(pageNo, 20, sortBy);

        if (participantRoleList != null) {
            int blockLimit = 5; // 현재 페이지 앞뒤로 보여줄 개수 설정
            int nowPage = participantRoleList.getPageNo() + 1;
            int startPage = Math.max(nowPage - blockLimit, 1);
            int endPage = Math.min(nowPage + blockLimit, participantRoleList.getTotalPages());

            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("participantRoleList", participantRoleList.getContent());
        }
        return "admin/page/participantRoleList";
    }
}
