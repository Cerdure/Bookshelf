package com.cerdure.bookshelf.service.classes.member.interfaces;

import com.cerdure.bookshelf.domain.enums.MemberGrade;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.member.VerifyCode;
import com.cerdure.bookshelf.dto.loginApi.ApiJoinDto;
import com.cerdure.bookshelf.dto.loginApi.MemberApiLoginInfoDto;
import com.cerdure.bookshelf.dto.member.MemberDto;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberService {
    Map<String, Object> join(MemberDto memberDto);
    MemberApiLoginInfoDto apiJoin(ApiJoinDto apiJoinDto);

    void validateNull(MemberDto memberDto);
    VerifyCode validateVerify(MemberDto memberDto);
    void validateDuplicateMember(MemberDto memberDto);
    void validateDuplicateMember(String phone);

    Map<String, Object> verifyCodeSend(String phoneNumber);
    Boolean verify(VerifyCode verifyCode, String code);
    Map<String, Object> verifyResolver(String phone, String code);
    Boolean verifyExpire(String phone);

    List<Member> findMembers();
    Member findMember(Authentication authentication);
    Optional<Member> findOptionalMember(Authentication authentication);
    Member findById(Long memberId);
    Member findByPhone(String phone);
    Member findByEmail(String email);

    Map<String, Object> updateInfo(MemberDto memberDto, Authentication authentication);
    Map<String, Object> updatePw(MemberDto memberDto);
    void updateGrade(Member member, MemberGrade memberGrade);
    Map<String, Object> resetProfile(Authentication authentication);
    void useCoupon(Long couponId);
    void changePoint(Authentication authentication, int point, boolean isPlus);

    Map<String, Object> leave(Authentication authentication, HttpServletRequest request);
}
