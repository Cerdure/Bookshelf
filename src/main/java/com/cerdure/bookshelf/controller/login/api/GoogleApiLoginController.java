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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.cerdure.bookshelf.domain.enums.MemberJoinType.Google;

@Controller
@RequiredArgsConstructor
public class GoogleApiLoginController {

    private final LoginApiService loginApiService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/login/api/google")
    public String googleLoginApi(@RequestParam("code") String code, HttpServletRequest request) {

        LoginApiSessionDto loginSession = loginApiService.doApiLogin(code, Google);
        MemberApiLoginInfoDto member = loginApiService.findMember(loginSession.getEmail());

        HttpSession session = request.getSession();
        session.setAttribute(ApiSession.API_SESSION, loginSession);

        if (member.getPhone() == null && member.getAddress() == null) {
            return "redirect:/login/api/apiJoin/" + loginSession.getEmail();
        }

        UsernamePasswordAuthenticationToken naverUser = new UsernamePasswordAuthenticationToken(member.getPhone(), member.getEmail());
        Authentication authentication = authenticationManager.authenticate(naverUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/";
    }
}
