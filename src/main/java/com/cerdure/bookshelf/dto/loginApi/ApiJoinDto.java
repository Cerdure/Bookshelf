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
    private String name;
    @NotNull
    private String phone;
    @NotNull
    private String city;
    @NotNull
    private String street;
    @NotNull
    private String zipcode;
}
