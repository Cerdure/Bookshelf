package com.cerdure.bookshelf.controller.login;

import com.cerdure.bookshelf.dto.loginApi.LoginApiSessionDto;
import com.cerdure.bookshelf.dto.member.MemberDto;
import com.cerdure.bookshelf.service.classes.login.LoginServiceImpl;
import com.cerdure.bookshelf.service.classes.member.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

import static com.cerdure.bookshelf.domain.enums.MemberJoinType.KAKAO;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginServiceImpl loginService;
    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login/fail")
    public String loginFail(@RequestParam("error") String error, Model model) {
        model.addAttribute("alertText", error);
        return "login";
    }

    @GetMapping("/login/check")
    @ResponseBody
    public Boolean loginCheck(Authentication authentication) {
        return Objects.isNull(authentication);
    }

    @GetMapping("/logout/check")
    public String logout(HttpServletRequest request) {
        try {
            LoginApiSessionDto memberSession = (LoginApiSessionDto) request.getSession(false).getAttribute("apiLogin");
            if (memberSession.getMemberJoinType().equals(KAKAO)) {
                return "redirect:/logout/api/kakao";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return "redirect:/logout";
        }
    }

    @GetMapping("/login/verify/send")
    @ResponseBody
    public Map<String, Object> verifySend(@RequestParam("phone") String phone) {
        return loginService.verifyCodeSend(phone);
    }

    @PostMapping("/login/pw/update")
    @ResponseBody
    public Map<String, Object> updatePw(@ModelAttribute MemberDto memberDto) {
        return memberService.updatePw(memberDto);
    }
}
