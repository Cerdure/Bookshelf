package com.cerdure.bookshelf.client.google;

import com.cerdure.bookshelf.dto.loginApi.GoogleTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "googleLogin", url="https://oauth2.googleapis.com")
public interface GoogleLoginClient {
    @PostMapping("/token")
    ResponseEntity<String> getAccesseToken(@RequestBody GoogleTokenDto googleTokenDto);
}
