package com.cerdure.bookshelf.service.loginApiService;

import com.cerdure.bookshelf.client.google.GoogleLoginClient;
import com.cerdure.bookshelf.client.google.GoogleMemberInfoClient;
import com.cerdure.bookshelf.domain.enums.MemberJoinType;
import com.cerdure.bookshelf.dto.loginApi.AuthResponseDto;
import com.cerdure.bookshelf.dto.loginApi.GoogleLoginInfoResponseDto;
import com.cerdure.bookshelf.dto.loginApi.GoogleTokenDto;
import com.cerdure.bookshelf.dto.loginApi.MemberResponseDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleApiImpl implements LoginAllApiService{
    private final GoogleLoginClient googleLoginClient;
    private final GoogleMemberInfoClient googleMemberInfoClient;
    @Value("${social.client.google.rest-api-key}")
    private String clientId;
    @Value("${social.client.google.secret-key}")
    private String secretKey;
    @Value("${social.client.google.redirect-uri}")
    private String redirectUrl;
    @Value("${social.client.google.grant_type}")
    private String type;
    @Override
    public MemberJoinType getLoginType() {
        return MemberJoinType.Google;
    }

    @Override
    public AuthResponseDto getAccessToken(String code) {
        ResponseEntity<?> accesseToken = googleLoginClient.getAccesseToken(
                GoogleTokenDto.builder()
                        .code(code)
                        .client_id(clientId)
                        .client_secret(secretKey)
                        .redirect_uri(redirectUrl)
                        .grant_type(type)
                        .build()
        );

        return new Gson().fromJson((String.valueOf(accesseToken.getBody())), AuthResponseDto.class);
    }

    @Override
    public MemberResponseDto getUserInfo(String token) {
        ResponseEntity<?> userInfo = googleMemberInfoClient.getUserInfo(token);

        String info = userInfo.getBody().toString();

        GoogleLoginInfoResponseDto googleLoginInfoResponse = new Gson().fromJson(info, GoogleLoginInfoResponseDto.class);

        return MemberResponseDto.builder()
                .email(googleLoginInfoResponse.getEmail())
                .memberImage(googleLoginInfoResponse.getPicture())
                .nickname(googleLoginInfoResponse.getGiven_name())
                .birthday("없음")
                .build();
    }

    @Override
    public void logOut(String code) {

    }
}
