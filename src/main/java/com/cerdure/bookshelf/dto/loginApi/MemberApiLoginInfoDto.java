package com.cerdure.bookshelf.dto.loginApi;

import com.cerdure.bookshelf.domain.member.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberApiLoginInfoDto {
    private String name;
    private String email;
    private String phone;
    private String password;
    private Address address;
}
