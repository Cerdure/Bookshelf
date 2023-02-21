package com.cerdure.bookshelf.service.classes.member;

import com.cerdure.bookshelf.domain.enums.MemberGrade;
import com.cerdure.bookshelf.domain.member.Address;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.member.MemberCoupon;
import com.cerdure.bookshelf.domain.member.VerifyCode;
import com.cerdure.bookshelf.dto.loginApi.ApiJoinDto;
import com.cerdure.bookshelf.dto.loginApi.MemberApiLoginInfoDto;
import com.cerdure.bookshelf.dto.member.MemberDto;
import com.cerdure.bookshelf.repository.member.MemberCouponRepository;
import com.cerdure.bookshelf.repository.member.MemberRepository;
import com.cerdure.bookshelf.repository.member.VerifyCodeRepository;
import com.cerdure.bookshelf.service.classes.file.interfaces.UploadFileService;
import com.cerdure.bookshelf.service.classes.member.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final VerifyCodeRepository verifyCodeRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final UploadFileService uploadFileService;

    @Value("${message.api-key}")
    private String smsApiKey;
    @Value("${message.secret-key}")
    private String smsApiSecret;

    @Override
    @Transactional
    public Map<String, Object> join(MemberDto memberDto) {
        Map<String, Object> map = new HashMap<>();
        try {
            validateNull(memberDto);
            validateDuplicateMember(memberDto);
            VerifyCode verifyCode = validateVerify(memberDto);
            memberDto.setPhone(verifyCode.getPhone());
            Member member = memberDto.createMember(passwordEncoder);
            memberRepository.save(member);
        } catch (Exception e) {
            map.put("error", e.getMessage());
            map.put("success", false);
            return map;
        }
        map.put("success", true);
        return map;
    }

    @Override
    @Transactional
    public MemberApiLoginInfoDto apiJoin(ApiJoinDto apiJoinDto) {
        Member member = memberRepository.findByEmail(apiJoinDto.getEmail());
        if (memberRepository.findByPhone(apiJoinDto.getPhoneNumber()).isPresent()) {
            log.info("이미 가입한 회원입니다");
            memberRepository.deleteById(member.getId());
            return null;
        }
        Address address = Address.builder()
                .street(apiJoinDto.getStreet())
                .city(apiJoinDto.getCity())
                .zipcode(apiJoinDto.getZipcode())
                .build();
        String passWordEmail = member.getEmail();
        String encode = passwordEncoder.encode(passWordEmail);
        Member joinedMember = member.apiJoin(apiJoinDto.getPhoneNumber(), address, encode, apiJoinDto.getMemberName());
        Member savedMember = memberRepository.save(joinedMember);

        return MemberApiLoginInfoDto.builder()
                .address(savedMember.getAddress())
                .password(passWordEmail)
                .phone(savedMember.getPhone())
                .build();
    }

    @Override
    public void validateNull(MemberDto memberDto) {
        if (memberDto.getNickname() == null || memberDto.getNickname().isEmpty())
            throw new NullPointerException("닉네임을 입력해주세요.");
        if (memberDto.getName() == null || memberDto.getName().isEmpty())
            throw new NullPointerException("이름을 입력해주세요.");
        if (memberDto.getEmail() == null || memberDto.getEmail().isEmpty())
            throw new NullPointerException("이메일을 입력해주세요.");
        if (memberDto.getPw() == null || memberDto.getPw().isEmpty())
            throw new NullPointerException("비밀번호를 입력해주세요.");
        if (memberDto.getPwCheck() == null || !memberDto.getPwCheck().equals(memberDto.getPw()))
            throw new NullPointerException("비밀번호 확인이 일치하지 않습니다.");
        if (memberDto.getZipcode() == null || memberDto.getZipcode().isEmpty()
                || memberDto.getCity() == null || memberDto.getCity().isEmpty())
            throw new NullPointerException("주소를 입력해주세요.");
        if (memberDto.getStreet() == null || memberDto.getStreet().isEmpty())
            throw new NullPointerException("상세주소를 입력해주세요.");
    }

    @Override
    public VerifyCode validateVerify(MemberDto memberDto) {
        Long codeId = ofNullable(memberDto.getCodeId())
                .orElseThrow(() -> new NullPointerException("휴대폰 인증을 진행해주세요."));
        VerifyCode verifyCode = verifyCodeRepository.findById(codeId)
                .orElseThrow(() -> new NullPointerException("휴대폰 인증을 진행해주세요."));
        if (!verifyCode.getVerified()) throw new NullPointerException("휴대폰 인증을 진행해주세요.");
        return verifyCode;
    }

    @Override
    public void validateDuplicateMember(MemberDto memberDto) {
        if (memberRepository.findByPhone(memberDto.getPhone()).isPresent())
            throw new IllegalStateException("이미 가입된 번호입니다.");
    }

    @Override
    public void validateDuplicateMember(String phone) {
        if (memberRepository.findByPhone(phone).isPresent())
            throw new IllegalStateException("이미 가입된 번호입니다.");
    }

    @Override
    @Transactional
    public Map<String, Object> verifyCodeSend(String phone) throws IllegalStateException {
        Map<String, Object> map = new HashMap<>();
        try {
            validateDuplicateMember(phone);
            StringBuilder code = new StringBuilder();

            for (int i = 0; i < 4; i++) {
                String ran = Integer.toString(new Random().nextInt(10));
                code.append(ran);
            }
            HashMap<String, String> params = new HashMap<>();
            params.put("to", phone);
            params.put("from", "01032100575");
            params.put("type", "SMS");
            params.put("text", "BOOKSHELF 휴대폰인증 메시지 : 인증번호는 " + "[" + code + "]" + "입니다.");
            params.put("app_version", "test app 1.2");

            Message message = new Message(smsApiKey, smsApiSecret);

            message.send(params);
            VerifyCode verifyCode = verifyCodeRepository.findByPhone(phone)
                    .orElseGet(() -> VerifyCode.builder()
                            .phone(phone)
                            .build());
            verifyCode.changeCode(code.toString());
            verifyCode.changeVerified(false);
            verifyCodeRepository.save(verifyCode);

        } catch (Exception e) {
            map.put("error", e.getMessage());
            map.put("success", false);
            return map;
        }
        map.put("success", true);
        return map;
    }

    @Override
    @Transactional
    public Boolean verify(VerifyCode verifyCode, String code) {
        boolean verified = code.equals(verifyCode.getCode());
        verifyCode.changeVerified(verified);
        verifyCodeRepository.save(verifyCode);
        return verified;
    }

    @Override
    @Transactional
    public Map<String, Object> verifyResolver(String phone, String code) {
        Map<String, Object> map = new HashMap<>();
        VerifyCode verifyCode = verifyCodeRepository.findByPhone(phone).orElseThrow();
        map.put("codeId", verifyCode.getId());
        map.put("success", verify(verifyCode, code));
        return map;
    }

    @Override
    @Transactional
    public Boolean verifyExpire(String phone) {
        try {
            verifyCodeRepository.deleteByPhone(phone);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return null;
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member findMember(Authentication authentication) {
        return memberRepository.findByPhone(authentication.getName()).get();
    }

    @Override
    public Optional<Member> findOptionalMember(Authentication authentication) {
        return memberRepository.findByPhone(authentication.getName());
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Override
    public Member findByPhone(String phone) {
        return memberRepository.findByPhone(phone).get();
    }

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public Map<String, Object> updateInfo(MemberDto memberDto, Authentication authentication) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (memberDto.getProfileImg() != null) {
                String profilePath = uploadFileService.saveFile(memberDto.getProfileImg());
                memberDto.setProfilePath(profilePath);
                map.put("profile", profilePath);
            }
            Member member = findMember(authentication);
            member.update(memberDto, passwordEncoder);
            memberRepository.save(member);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("error", e.getMessage());
            return map;
        }
        map.put("success", true);
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> updatePw(MemberDto memberDto) {
        Map<String, Object> map = new HashMap<>();
        try {
            Member member = findByPhone(memberDto.getPhone());
            member.update(memberDto, passwordEncoder);
            memberRepository.save(member);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("error", e.getMessage());
            return map;
        }
        map.put("success", true);
        return map;
    }

    @Override
    @Transactional
    public void updateGrade(Member member, MemberGrade memberGrade) {
        member.changeGrade(memberGrade);
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public Map<String, Object> resetProfile(Authentication authentication) {
        Map<String, Object> map = new HashMap<>();
        try {
            Member member = findMember(authentication);
            member.changeProfilePath(null);
            memberRepository.save(member);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("error", e.getMessage());
            return map;
        }
        map.put("success", true);
        return map;
    }

    @Override
    @Transactional
    public void useCoupon(Long couponId) {
        memberCouponRepository.deleteById(couponId);
    }

    @Override
    @Transactional
    public void changePoint(Authentication authentication, int point, boolean isPlus) {
        Member member = findMember(authentication);
        member.changePoint(member.getPoint() + (isPlus ? point : -point));
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public Map<String, Object> leave(Authentication authentication, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            Member member = findMember(authentication);
            member.leave();
            memberRepository.save(member);
            HttpSession session = request.getSession(false);
            session.invalidate();
            SecurityContextHolder.getContext().setAuthentication(null);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("error", e.getMessage());
            return map;
        }
        map.put("success", true);
        return map;
    }
}
