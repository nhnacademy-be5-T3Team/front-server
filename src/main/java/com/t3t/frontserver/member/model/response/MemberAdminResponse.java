package com.t3t.frontserver.member.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberAdminResponse {
    public Long id;
    public String name;
    public String email;
}
