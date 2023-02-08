package com.cerdure.bookshelf.service.loginApiService;

import com.cerdure.bookshelf.client.kakao.KakaoLoginClient;
import com.cerdure.bookshelf.client.kakao.KakaoLogoutAllClient;
import com.cerdure.bookshelf.client.kakao.KakaoLogoutClient;
import com.cerdure.bookshelf.client.kakao.KakaoMembernfoClient;
import com.cerdure.bookshelf.domain.enums.MemberJoinType;
import com.cerdure.bookshelf.dto.loginApi.AuthResponseDto;
import com.cerdure.bookshelf.dto.loginApi.KakaoLoginInfoResponseDto;
import com.cerdure.bookshelf.dto.loginApi.MemberResponseDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.cerdure.bookshelf.dto.loginApi.KakaoLoginInfoResponseDto.KakaoLoginData;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoApiLoginImpl implements LoginAllApiService {
    private final KakaoLogoutAllClient kakaoLogoutAllClient;
    private final KakaoLoginClient kakaoLoginClient;
    private final KakaoMembernfoClient kakaoMembernfoClient;
    private final KakaoLogoutClient kakaoLogoutClient;


    @Value("${social.client.kakao.rest-api-key}")
    private String clientId;
    @Value("${social.client.kakao.secret-key}")
    private String clientSecret;
    @Value("${social.client.kakao.grant_type}")
    private String grantType;
    @Value("${social.client.kakao.redirect-uri}")
    private String redirectUri;
    @Value("${social.client.kakao.logout-redirect-uri}")
    private String logoutRedirect;
    @Override
    public MemberJoinType getLoginType() {
        return MemberJoinType.KAKAO;
    }

    @Override
    public AuthResponseDto getAccessToken(String code) {
        ResponseEntity<?> accessToken = kakaoLoginClient.getAccessToken(grantType, clientId, clientSecret, redirectUri, code);
        return new Gson().fromJson(String.valueOf(accessToken.getBody()),AuthResponseDto.class);
    }

    @Override
    public MemberResponseDto getUserInfo(String token) {
        Map<String, String> header = new HashMap<>();
        header.put("authorization","Bearer " + token);

        ResponseEntity<?> response = kakaoMembernfoClient.getUserInfo(header);

        String jsonString = response.getBody().toString();

        Gson gson = new Gson();

        KakaoLoginInfoResponseDto kakaoLoginInfoResponseDto = gson.fromJson(jsonString, KakaoLoginInfoResponseDto.class);
        KakaoLoginData kakaoLoginData = Optional.ofNullable(kakaoLoginInfoResponseDto.getKakao_account()).orElse(KakaoLoginData.builder().build());
        String nickname = Optional.ofNullable(kakaoLoginData.getProfile()).orElse(KakaoLoginData.KakaoProfile.builder().build()).getNickname();
        String image = Optional.ofNullable(kakaoLoginData.getProfile()).orElse(KakaoLoginData.KakaoProfile.builder().build()).getImage();

        return MemberResponseDto.builder()
                .email(kakaoLoginData.getEmail())
                .gender(kakaoLoginData.getGender())
                .memberImage(image)
                .nickname(nickname)
                .birthday(kakaoLoginData.getBirthday())
                .build();
    }

    @Override
    public void logOut(String code) {
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer "+code+"");
        ResponseEntity<?> response = kakaoLogoutClient.doLogout(header);
        log.info("로그아웃 실행={}",response.getBody().toString());
    }
}
