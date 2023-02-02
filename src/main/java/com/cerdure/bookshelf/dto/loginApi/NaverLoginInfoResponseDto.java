package com.cerdure.bookshelf.dto.loginApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NaverLoginInfoResponseDto {
    @Builder.Default
    private Response response=Response.builder().build();
    private String resultCode;
    private String message;
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private String id;
        private String nickName;
        private String profile_image;
        private String age;
        private String gender;
        private String email;
        private String mobile;
        private String name;
        private String birthyear;
        private String birthday;
    }
}
