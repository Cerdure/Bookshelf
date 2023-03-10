package com.cerdure.bookshelf.controller.login.api;

import com.cerdure.bookshelf.dto.loginApi.LoginApiSessionDto;
import com.cerdure.bookshelf.dto.loginApi.MemberApiLoginInfoDto;
import com.cerdure.bookshelf.service.classes.login.api.LoginApiService;
import com.cerdure.bookshelf.web.session.ApiSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.cerdure.bookshelf.domain.enums.MemberJoinType.KAKAO;

@Controller
@RequiredArgsConstructor
public class KakaoApiLoginController {

    private final LoginApiService loginApiService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/login/api/kakao")
    public String kakaoLoginApi(@RequestParam String code, HttpServletRequest request) {

        LoginApiSessionDto loginSession = loginApiService.doApiLogin(code, KAKAO);
        MemberApiLoginInfoDto member = loginApiService.findMember(loginSession.getEmail());

        HttpSession session = request.getSession();
        session.setAttribute(ApiSession.API_SESSION, loginSession);

        if (member.getPhone() == null && member.getAddress() == null) {
            return "redirect:/login/api/apiJoin/" + loginSession.getEmail();
        }

        UsernamePasswordAuthenticationToken kakaoUser = new UsernamePasswordAuthenticationToken(member.getPhone(), member.getEmail());
        Authentication authentication = authenticationManager.authenticate(kakaoUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/";
    }

    @GetMapping("/logout/api/kakao")
    public String kakaoLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        LoginApiSessionDto apiLogin = (LoginApiSessionDto) session.getAttribute("apiLogin");
        loginApiService.logout(apiLogin);
        session.invalidate();
        Cookie kc = new Cookie("JSESSIONID", null); // choiceCookieName(?????? ??????)??? ?????? ?????? null??? ??????
        kc.setMaxAge(0); // ??????????????? 0?????? ??????
        response.addCookie(kc); // ?????? ????????? ???????????? ??????????????? ???
        return "redirect:/logout";
    }
}
