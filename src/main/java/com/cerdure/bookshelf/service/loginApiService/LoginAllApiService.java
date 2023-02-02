package com.cerdure.bookshelf.service.loginApiService;

import com.cerdure.bookshelf.domain.enums.MemberJoinType;
import com.cerdure.bookshelf.dto.loginApi.AuthResponseDto;
import com.cerdure.bookshelf.dto.loginApi.MemberResponseDto;

public interface LoginAllApiService {
    MemberJoinType getLoginType();
    AuthResponseDto getAccessToken(String code);

    MemberResponseDto getUserInfo(String token);

    void logOut(String code);

}
