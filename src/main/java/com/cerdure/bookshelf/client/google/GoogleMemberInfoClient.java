package com.cerdure.bookshelf.client.google;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="GoogleMemberInfo", url="https://www.googleapis.com")
public interface GoogleMemberInfoClient {

    @GetMapping("/userinfo/v2/me")
    ResponseEntity<String> getUserInfo(@RequestParam("access_token") String code);
}
