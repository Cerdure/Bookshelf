package com.cerdure.bookshelf.service.interfaces;

import com.cerdure.bookshelf.dto.loginApi.ApiJoinDto;
import com.cerdure.bookshelf.dto.loginApi.MemberApiLoginInfoDto;
import com.cerdure.bookshelf.dto.member.InfoUpdateDto;
import com.cerdure.bookshelf.dto.member.MemberDto;
import com.cerdure.bookshelf.domain.member.Member;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface MemberService {
    public Long join(MemberDto memberDto);
    void validateDuplicateMember(MemberDto memberDto);
    MemberApiLoginInfoDto apiJoin(ApiJoinDto apiJoinDto, String email);
    public List<Member> findMembers();
    public Member findMember(Authentication authentication);
    InfoUpdateDto showInfo(Authentication authentication);
    public Member findById(Long memberId);
    public Member findByPhone(String phone);
    public void update(Long id, MemberDto memberDto);
    public void delete(Long id);
    public void changePoint(Authentication authentication, int point);
    public void changePoint(Member member, int point);
}
