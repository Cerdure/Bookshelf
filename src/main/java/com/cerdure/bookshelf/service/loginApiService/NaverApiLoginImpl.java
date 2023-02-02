package com.cerdure.bookshelf.service.loginApiService;

import com.cerdure.bookshelf.client.naver.NaverLoginClient;
import com.cerdure.bookshelf.client.naver.NaverMemberInfoClient;
import com.cerdure.bookshelf.domain.enums.MemberJoinType;
import com.cerdure.bookshelf.dto.loginApi.AuthResponseDto;
import com.cerdure.bookshelf.dto.loginApi.MemberResponseDto;
import com.cerdure.bookshelf.dto.loginApi.NaverLoginInfoResponseDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverApiLoginImpl implements LoginAllApiService {

    private final NaverMemberInfoClient naverMemberInfoClient;
    private final NaverLoginClient naverLoginClient;

    @Value("${social.client.naver.rest-api-key}")
    private String clientId;
    @Value("${social.client.naver.secret-key}")
    private String secretKey;
    @Value("${social.client.naver.redirect-uri}")
    private String redirectUrl;
    @Value("${social.client.naver.grant_type}")
    private String type;

    @Override
    public MemberJoinType getLoginType() {
       return MemberJoinType.NAVER;
    }

    @Override
    public AuthResponseDto getAccessToken(String code) {
        ResponseEntity<?> stringResponseEntity = naverLoginClient.accessToken(type, clientId, secretKey, code,"1234");
        log.info("response={}", stringResponseEntity.toString());

        return new Gson().fromJson(String.valueOf(stringResponseEntity.getBody()), AuthResponseDto.class);
    }

    @Override
    public MemberResponseDto getUserInfo(String token) {
        log.info("token={}",token);
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer " + token);
        ResponseEntity<?> userInfo = naverMemberInfoClient.getUserInfo(header);
        String info = userInfo.getBody().toString();

        Gson gson= new Gson();
        NaverLoginInfoResponseDto naverLoginInfoResponse = gson.fromJson(info, NaverLoginInfoResponseDto.class);
        NaverLoginInfoResponseDto.Response response = naverLoginInfoResponse.getResponse();

        return MemberResponseDto.builder()
                .email(response.getEmail())
                .nickname(response.getNickName())
                .memberImage(response.getProfile_image())
                .build();
    }

    @Override
    public void logOut(String code) {

    }
}
