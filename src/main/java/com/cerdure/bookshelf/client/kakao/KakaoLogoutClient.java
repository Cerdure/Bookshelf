package com.cerdure.bookshelf.client.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(value = "kakaologout", url="https://kapi.kakao.com")
public interface KakaoLogoutClient {
    @GetMapping("/v1/user/logout")
    ResponseEntity<String> doLogout(@RequestHeader Map<String,String> header);
}
