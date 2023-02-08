package com.cerdure.bookshelf.client.naver;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name="naverLogin", url = "https://nid.naver.com")
public interface NaverLoginClient {
    @GetMapping("/oauth2.0/token")
    ResponseEntity<String> accessToken(
            @RequestParam("grant_type") String type,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("code") String code,
            @RequestParam("state") String state
    );
}
