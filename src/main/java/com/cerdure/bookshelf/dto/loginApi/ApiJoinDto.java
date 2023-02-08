package com.cerdure.bookshelf.dto.loginApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiJoinDto {
    @NotNull
    private String email;
    @NotNull
    private String memberName;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String zipcode;
    @NotNull
    private String street;
    @NotNull
    private String city;

}
