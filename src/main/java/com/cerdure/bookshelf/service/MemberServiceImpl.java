package com.cerdure.bookshelf.service;

import com.cerdure.bookshelf.domain.member.Address;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.loginApi.ApiJoinDto;
import com.cerdure.bookshelf.dto.loginApi.MemberApiLoginInfoDto;
import com.cerdure.bookshelf.dto.member.InfoUpdateDto;
import com.cerdure.bookshelf.dto.member.MemberDto;
import com.cerdure.bookshelf.repository.MemberRepository;
import com.cerdure.bookshelf.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long join(MemberDto memberDto) {
        validateDuplicateMember(memberDto);
        Member member = memberDto.createMember(passwordEncoder);
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(MemberDto memberDto) {
        Optional<Member> findMembers = memberRepository.findByPhone(memberDto.getPhone());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Override
    @Transactional
    public MemberApiLoginInfoDto apiJoin(ApiJoinDto apiJoinDto, String email) {
        Member member = memberRepository.findByEmail(email);
        Address address = Address.builder()
                .street(apiJoinDto.getStreet())
                .city(apiJoinDto.getCity())
                .zipcode(apiJoinDto.getZipcode())
                .build();
        String passWordEmail = member.getEmail();
        String encode = passwordEncoder.encode(passWordEmail);
        Member joinedMember = member.apiJoin(apiJoinDto.getPhone(), address, encode,apiJoinDto.getName());
        Member savedMember = memberRepository.save(joinedMember);

        return MemberApiLoginInfoDto.builder()
                .address(savedMember.getAddress())
                .password(passWordEmail)
                .phone(savedMember.getPhone())
                .build();
    }

    @Override
    public Member findMember(Authentication authentication) {
        return memberRepository.findByPhone(authentication.getName()).get();
    }

    @Override
    public InfoUpdateDto showInfo(Authentication authentication) {
        Member member = this.findMember(authentication);
        return InfoUpdateDto.builder()
                .city(member.getAddress().getCity())
                .postNum(member.getAddress().getZipcode())
                .street(member.getAddress().getStreet())
                .nickName(member.getNickname())
                .phone(member.getPhone())
                .memberJoinType(member.getMemberJoinType())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Override
    public Member findByPhone(String phone) {
        return memberRepository.findByPhone(phone).get();
    }

    @Transactional
    public void update(Long id, MemberDto memberDto) {
//        Member member = memberRepository.findOne(id);
//        member.setName(name);
    }

    @Transactional
    public void delete(Long id){
    }

    @Override
    public void changePoint(Authentication authentication, int point) {
        Member member = findMember(authentication);
        member.changePoint(member.getPoint() - point);
        memberRepository.save(member);
    }

    @Override
    public void changePoint(Member member, int point) {
        member.changePoint(member.getPoint() - point);
        memberRepository.save(member);
    }

}
