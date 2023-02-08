package com.cerdure.bookshelf.dto.loginApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String email;
    private String memberImage;
    private String nickname;
    private String gender;
    private String birthday;


}
