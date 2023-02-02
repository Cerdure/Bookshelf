package com.cerdure.bookshelf.controller.loginApi;

import com.cerdure.bookshelf.domain.enums.MemberJoinType;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.loginApi.LoginApiSessionDto;
import com.cerdure.bookshelf.dto.loginApi.MemberApiLoginInfoDto;
import com.cerdure.bookshelf.service.loginApiService.LoginAllApiService;
import com.cerdure.bookshelf.service.loginApiService.LoginApiService;
import com.cerdure.bookshelf.web.security.SecurityConfig;
import com.cerdure.bookshelf.web.session.ApiSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class KakaoApiLoginController {
    private final LoginApiService loginApiService;
    private final AuthenticationManager authenticationManager;
    @GetMapping("/login/api/kakao")
    public String kakaoLoginApi(@RequestParam String code,HttpServletRequest request){

        LoginApiSessionDto loginSession = loginApiService.doApiLogin(code, MemberJoinType.KAKAO);
        MemberApiLoginInfoDto member = loginApiService.findMember(loginSession.getEmail());

        HttpSession session = request.getSession();
        session.setAttribute(ApiSession.API_SESSION,loginSession);

        if(member.getPhone()==null&&member.getAddress()==null){
            return "redirect:/login/api/apiJoin/"+loginSession.getEmail();
        }

        UsernamePasswordAuthenticationToken kakaoUser = new UsernamePasswordAuthenticationToken(member.getPhone(), member.getEmail());
        Authentication authentication = authenticationManager.authenticate(kakaoUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/";
    }

    @GetMapping("/logout/api/kakao")
    public String kakaoLogout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        LoginApiSessionDto apiLogin = (LoginApiSessionDto) session.getAttribute("apiLogin");
        loginApiService.logout(apiLogin);
        session.invalidate();
        Cookie kc = new Cookie("JSESSIONID", null); // choiceCookieName(쿠키 이름)에 대한 값을 null로 지정
        kc.setMaxAge(0); // 유효시간을 0으로 설정
        response.addCookie(kc); // 응답 헤더에 추가해서 없어지도록 함
        return "redirect:/logout";
    }
}
