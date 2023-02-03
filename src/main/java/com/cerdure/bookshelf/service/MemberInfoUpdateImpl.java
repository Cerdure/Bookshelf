package com.cerdure.bookshelf.service;

import com.cerdure.bookshelf.domain.member.Address;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.member.NewAddressDto;
import com.cerdure.bookshelf.dto.member.NewNamesDto;
import com.cerdure.bookshelf.repository.MemberRepository;
import com.cerdure.bookshelf.service.interfaces.MemberInfoUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.Coolsms;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberInfoUpdateImpl implements MemberInfoUpdateService {
    @Value("${social.client.message.api-key}")
    private String apiKey;
    @Value("${social.client.message.secret-key}")
    private String secretKey;
    private final MemberRepository memberRepository;
    @Override
    public Boolean memberPhoneCheck(String phoneNumber) {
       if(memberRepository.findByPhone(phoneNumber)==null){
           return true;
       }else{
           return false;
       }
    }

    @Override
    public Boolean memberPasswordChange(String newPassword, String phone) {
        Member member = findByPhone(phone);
        String changePassword = member.changePassword(newPassword);
        if(changePassword==newPassword){
            return true;
        }
            return false;
    }

    @Override
    public String makeNum(String phoneNumber) {

        Random random = new Random();
        String numStr = "";

        for(int i=0; i<4; i++){
            String ran = Integer.toString(random.nextInt(10));
            numStr+=ran;
        }
        Message message = new Message(apiKey, secretKey);
        HashMap<String, String> params = new HashMap<>();
        params.put("to",phoneNumber);
        params.put("from","01064090592");
        params.put("type","SMS");
        params.put("text","BOOKSHELF 휴대폰인증 메시지 : 인증버호는 "+"["+numStr+"]"+"입니다.");
        params.put("app_version","test app 1.2");

        try{
            JSONObject obj = (JSONObject) message.send(params);
        }catch (CoolsmsException e){
            log.info("message={}",e.getMessage());
            log.info("code={}",e.getCode());
        }
        return numStr;
    }

    @Override
    public String changeNumber(String phoneNumber,String newPhoneNumber) {
        Member member = findByPhone(phoneNumber);
        Member newPhoneNum = member.changePhoneNumber(newPhoneNumber);
        Member savedMember = memberRepository.save(newPhoneNum);
        return savedMember.getPhone();
    }

    /**
     * 이메일 변경
     * @param email
     * @return
     */
    @Override
    public Boolean memberEmailChange(String email,String phone) {
        if(memberRepository.findByEmail(email)!=null){
            return false;
        }else{
            Member member = findByPhone(phone);
            Member newEmail = member.changeEmail(email);
            memberRepository.save(newEmail);
            return true;
        }
    }

    @Override
    public NewNamesDto memberChangeNames(String newName, String newNickName, String phone) {
        Member member = findByPhone(phone);
        Member newNames = member.changeNames(newName, newNickName);

        return NewNamesDto.builder()
                .newNickName(newNames.getNickname())
                .newName(newNames.getName())
                .build();
    }

    private Member findByPhone(String phone) {
        return memberRepository.findByPhone(phone).orElseThrow(() ->
                  new IllegalArgumentException("없는 회원입니다"));
    }

    @Override
    public Boolean memberChangeAddress(NewAddressDto newAddressDto,String phone) {
        Member member = findByPhone(phone);
        Address newAddress = Address.builder()
                .city(newAddressDto.getCity())
                .zipcode(newAddressDto.getZipcode())
                .street(newAddressDto.getStreet())
                .build();

        Address newMemberAddress= member.changeAddress(newAddress);
        log.info("getStreet={}",newMemberAddress.getStreet());
        log.info("getCity={}",newMemberAddress.getCity());
        log.info("getZipcode={}",newMemberAddress.getZipcode());

        if(newMemberAddress.getCity()==newAddressDto.getCity()&&
         newMemberAddress.getStreet()==newAddressDto.getStreet()&&
         newMemberAddress.getZipcode()==newAddressDto.getZipcode()){
            return true;
        }
        return false;
    }

    @Override
    public Boolean memberDelete(String phone) {
        memberRepository.deleteByPhone(phone);
        if(memberRepository.findByPhone(phone)==null){
            return true;
        }
        return false;
    }
}
