package com.cerdure.bookshelf.dto.member;

import com.cerdure.bookshelf.domain.enums.MemberJoinType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfoUpdateDto {

    private String phone;
    private String nickName;
    private String name;
    private String postNum;
    private String city;
    private String street;
    private String email;
    private MemberJoinType memberJoinType;
    private String memberprofile;
}
