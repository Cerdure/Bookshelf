package com.cerdure.bookshelf.dto.loginApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiJoinDto {
    private String email;
    private String memberName;
    private String phoneNumber;
    private String zipcode;
    private String street;
    private String city;
}
