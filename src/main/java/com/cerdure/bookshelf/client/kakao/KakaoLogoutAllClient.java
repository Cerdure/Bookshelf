package com.cerdure.bookshelf.client.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(value = "kakaologoutAll", url="https://kauth.kakao.com")
public interface KakaoLogoutAllClient {

     @GetMapping("/oauth/logout")
     ResponseEntity<String> doLogout2(
             @RequestParam("client_id") String clientId,
             @RequestParam("logout_redirect_uri") String logoutUrl
     );
}
