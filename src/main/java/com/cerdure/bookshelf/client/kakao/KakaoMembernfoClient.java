package com.cerdure.bookshelf.client.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient( name = "kakoMemberInfo", url="https://kapi.kakao.com")
public interface KakaoMembernfoClient {

    @GetMapping("/v2/user/me")
    ResponseEntity<String> getUserInfo(@RequestHeader Map<String, String> header);
}
