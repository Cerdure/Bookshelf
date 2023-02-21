package com.cerdure.bookshelf.web.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        String error;
        if (e instanceof BadCredentialsException || e instanceof InternalAuthenticationServiceException) {
            error = "아이디 또는 비밀번호가 맞지 않습니다.";
        } else if (e instanceof UsernameNotFoundException) {
            error = "존재하지 않는 아이디 입니다.";
        } else {
            error = "요청이 실패하였습니다.";
        }

        error = URLEncoder.encode(error, "UTF-8");
        setDefaultFailureUrl("/login/fail?error=" + error);
        super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
    }
}
