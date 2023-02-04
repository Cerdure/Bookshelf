package com.cerdure.bookshelf.controller;

import com.cerdure.bookshelf.domain.enums.MemberJoinType;
import com.cerdure.bookshelf.dto.loginApi.LoginApiSessionDto;
import com.cerdure.bookshelf.service.LoginServiceImpl;
import com.cerdure.bookshelf.dto.member.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginServiceImpl loginService;

    @GetMapping("/login")
    public String login(@Valid @ModelAttribute("loginDto") LoginDto loginDto, BindingResult bindingResult) {
        return "login";
    }

    @GetMapping("/logout/check")
    public String logout(HttpServletRequest request) {
        try {
            LoginApiSessionDto memberSession = (LoginApiSessionDto) request.getSession(false).getAttribute("apiLogin");
            if (memberSession.getMemberJoinType().equals(MemberJoinType.KAKAO)) {
                return "redirect:/logout/api/kakao";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return "redirect:/logout";
        }
    }
}
