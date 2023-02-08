package com.cerdure.bookshelf.controller.loginApi;

import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.loginApi.ApiJoinDto;
import com.cerdure.bookshelf.dto.loginApi.MemberApiLoginInfoDto;
import com.cerdure.bookshelf.dto.loginApi.MemberResponseDto;
import com.cerdure.bookshelf.repository.MemberRepository;
import com.cerdure.bookshelf.service.interfaces.MemberService;
import com.cerdure.bookshelf.service.loginApiService.LoginApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginApiSimpleJoinController {
    
    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;
    @GetMapping("/login/api/apiJoin/{email}")
    private String simpleJoinForm(@PathVariable(name="email")String email, Model model){
        log.info("GetMail={}",email);
        model.addAttribute("email",email);
        return "join-api";
    }
    @PostMapping("/login/apiJoin")
    @ResponseBody
    private boolean simpleJoin(@Validated @ModelAttribute ApiJoinDto apiJoinDto){
        log.info("PostMail={}", apiJoinDto.getEmail());

        MemberApiLoginInfoDto member = memberService.apiJoin(apiJoinDto);

        if(member==null){
            log.info("null 이미 가입한 회원입니다");
            return false;
        }else{
            UsernamePasswordAuthenticationToken kakaoUser = new UsernamePasswordAuthenticationToken(member.getPhone(), member.getPassword());
            Authentication authentication = authenticationManager.authenticate(kakaoUser);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        }
    }
}
