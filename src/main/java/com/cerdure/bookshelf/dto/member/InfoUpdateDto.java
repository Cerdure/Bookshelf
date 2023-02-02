package com.cerdure.bookshelf.dto.member;

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
    private String postNum;
    private String city;
    private String street;
}
