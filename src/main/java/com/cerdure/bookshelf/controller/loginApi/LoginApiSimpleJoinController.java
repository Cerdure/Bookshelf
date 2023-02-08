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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping("/login/api/apiJoin/{email}")
    private String simpleJoin(@PathVariable(name="email")String email, @Validated @ModelAttribute ApiJoinDto apiJoinDto){
        log.info("PostMail={}",email);

        MemberApiLoginInfoDto member = memberService.apiJoin(apiJoinDto, email);
        log.info("phone={}",member.getPhone());
        log.info("pw={}",member.getPassword());

        UsernamePasswordAuthenticationToken kakaoUser = new UsernamePasswordAuthenticationToken(member.getPhone(), member.getPassword());
        Authentication authentication = authenticationManager.authenticate(kakaoUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }
}
