package com.cerdure.bookshelf.service.classes.login.api;

import com.cerdure.bookshelf.domain.enums.MemberJoinType;
import com.cerdure.bookshelf.domain.enums.MemberRole;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.loginApi.AuthResponseDto;
import com.cerdure.bookshelf.dto.loginApi.LoginApiSessionDto;
import com.cerdure.bookshelf.dto.loginApi.MemberApiLoginInfoDto;
import com.cerdure.bookshelf.dto.loginApi.MemberResponseDto;
import com.cerdure.bookshelf.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginApiService {

    private final List<LoginAllApiService> loginAllApiService;
    private final MemberRepository memberRepository;

    public LoginApiSessionDto doApiLogin(String code, MemberJoinType joinType) {
        LoginAllApiService loginApiService = this.getLoginApiService(joinType);
        AuthResponseDto accessToken = loginApiService.getAccessToken(code);
        MemberResponseDto userInfo = loginApiService.getUserInfo(accessToken.getAccess_token());

        if (userInfo.getEmail().isEmpty()) {
            throw new IllegalArgumentException("이메일이 없으면 로그인이 불가능합니다");
        }

        if (memberRepository.findByEmail(userInfo.getEmail()) == null) {
            Member member = Member.builder()
                    .joinType(joinType)
                    .email(userInfo.getEmail())
                    .nickname(userInfo.getNickname())
                    .role(MemberRole.USER)
                    .regDate(LocalDate.now())
                    .build();
            memberRepository.save(member);
        }
        Member member = Optional.ofNullable(memberRepository.findByEmail(userInfo.getEmail())).orElseThrow(() ->
                new IllegalArgumentException("없는 회원입니다"));

        return LoginApiSessionDto.builder()
                .email(member.getEmail())
                .code(accessToken.getAccess_token())
                .memberJoinType(joinType)
                .build();
    }

    public void logout(LoginApiSessionDto loginApiSessionDto) {
        LoginAllApiService loginApiService = this.getLoginApiService(loginApiSessionDto.getMemberJoinType());
        loginApiService.logOut(loginApiSessionDto.getCode());
    }

    private LoginAllApiService getLoginApiService(MemberJoinType joinType) {
        for (LoginAllApiService allApiService : loginAllApiService) {
            if (joinType.equals(allApiService.getLoginType())) {
                return allApiService;
            }
        }
        throw new IllegalArgumentException("알맞은 타입이 존재하지 않습니다");
    }

    public MemberApiLoginInfoDto findMember(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member.getAddress() == null) {
            return MemberApiLoginInfoDto.builder()
                    .email(member.getEmail())
                    .build();
        }
        return MemberApiLoginInfoDto.builder()
                .email(member.getEmail())
                .phone(member.getPhone())
                .password(member.getPw())
                .address(member.getAddress())
                .build();
    }
}
