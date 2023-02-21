package com.cerdure.bookshelf.service.classes.login;

import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.member.VerifyCode;
import com.cerdure.bookshelf.repository.member.MemberRepository;
import com.cerdure.bookshelf.repository.member.VerifyCodeRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final VerifyCodeRepository verifyCodeRepository;

    @Value("${message.api-key}")
    private String smsApiKey;
    @Value("${message.secret-key}")
    private String smsApiSecret;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Member member = memberRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException(phone));
        return User.builder()
                .username(member.getPhone())
                .password(member.getPw())
                .roles(member.getRole().toString())
                .build();
    }

    public Map<String, Object> verifyCodeSend(String phone) {
        Map<String, Object> map = new HashMap<>();
        try {
            memberRepository.findByPhone(phone)
                    .orElseThrow(() -> new UsernameNotFoundException("미가입된 번호입니다."));
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
}
