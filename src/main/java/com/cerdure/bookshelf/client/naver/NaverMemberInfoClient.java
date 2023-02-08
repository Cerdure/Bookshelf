package com.cerdure.bookshelf.client.naver;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name="naverMemberInfo", url = "https://openapi.naver.com")
public interface NaverMemberInfoClient {
    @GetMapping("/v1/nid/me")
    ResponseEntity<String> getUserInfo(
            @RequestHeader Map<String, String> header
            );
}
