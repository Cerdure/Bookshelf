package com.cerdure.bookshelf.dto.loginApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoLoginInfoResponseDto {
    private String id;
    @Builder.Default
    private KakaoLoginData kakao_account = KakaoLoginData.builder().build();
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoLoginData {
        private String gender;
        private String email;
        private String birthday;
        private String name;

        @Builder.Default
        private KakaoProfile profile = KakaoProfile.builder().build();
        @Builder.Default
        private KakaoPropery properties = KakaoPropery.builder().build();
        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class KakaoProfile {
            private String nickname;
            private String image;
        }
        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class KakaoPropery {
            private String nickname;
        }
    }
}
