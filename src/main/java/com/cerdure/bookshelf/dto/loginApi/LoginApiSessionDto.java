package com.cerdure.bookshelf.dto.loginApi;

import com.cerdure.bookshelf.domain.enums.MemberJoinType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginApiSessionDto {
    private String code;
    private String email;
    private MemberJoinType memberJoinType;
}
