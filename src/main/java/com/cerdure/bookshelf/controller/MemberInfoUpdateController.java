package com.cerdure.bookshelf.controller;
import com.cerdure.bookshelf.dto.member.InfoUpdateDto;
import com.cerdure.bookshelf.dto.member.MemberProfileDto;
import com.cerdure.bookshelf.dto.member.NewAddressDto;
import com.cerdure.bookshelf.dto.member.NewNamesDto;
import com.cerdure.bookshelf.service.interfaces.MemberInfoUpdateService;
import com.cerdure.bookshelf.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberInfoUpdateController {
    private final MemberService memberService;
    private final MemberInfoUpdateService memberInfoUpdateService;

    @GetMapping("/memberInfo/update")
    public String memberInfoUpdateForm(Authentication authentication, Model model){
        InfoUpdateDto info = memberService.showInfo(authentication);
        model.addAttribute("memberInfo",info);
        return "memberInfoUpdate";
    }

    @GetMapping("/memberinfo/phone/check/")
    @ResponseBody
    public boolean memberPhoneNumberCheck(@RequestParam("phone") String number){
        Boolean check = memberInfoUpdateService.memberPhoneCheck(number);
        return check;
    }
    @GetMapping("/memberinfo/phone/makeNum")
    @ResponseBody
    public String memberMessageNumberChange(@RequestParam("phone") String number)
    {
        String messageNumber = memberInfoUpdateService.makeNum(number);
        return messageNumber;
    }

    @GetMapping("/memberinfo/phone")
    @ResponseBody
    public String memberChangePhoneNumber(@RequestParam("phone")String originNum, @RequestParam("newPhoneNum")String newNumber){
       return memberInfoUpdateService.changeNumber(originNum,newNumber);
    }

    @GetMapping("/memberinfo/password")
    @ResponseBody
    public boolean memberChangePassword(@RequestParam("password")String password,Authentication authentication){
        String phone = authentication.getName();
        return  memberInfoUpdateService.memberPasswordChange(password, phone);
    }
    @GetMapping("/memberinfo/email")
    @ResponseBody
    public boolean memberChangeEmail(@RequestParam("email")String newEmail,Authentication authentication){
        String phone = authentication.getName();
        return  memberInfoUpdateService.memberEmailChange(newEmail, phone);
    }

    @GetMapping("/memberinfo/names")
    @ResponseBody
    public NewNamesDto memberChangeNames(@RequestParam("name")String name,@RequestParam("nickName")String nickName, Authentication authentication){
        String phone = authentication.getName();
        return memberInfoUpdateService.memberChangeNames(name, nickName, phone);
    }

    @GetMapping("/memberinfo/address")
    @ResponseBody
    public Boolean memberChangeAddress(@ModelAttribute NewAddressDto newAddressDto, Authentication authentication){
        String phone = authentication.getName();
        return memberInfoUpdateService.memberChangeAddress(newAddressDto, phone);
    }

    @GetMapping("/memberinfo/delete")
    @ResponseBody
    public Boolean memberdelete(Authentication authentication){
        String phone = authentication.getName();
        return memberInfoUpdateService.memberDelete(phone);
    }
    @PostMapping("/memberInfo/profile")
    @ResponseBody
    public String memberProfileChange(@ModelAttribute MemberProfileDto memberProfileDto, Authentication authentication) throws IOException {
        log.info("file={}",memberProfileDto.getFile());
        String phone = authentication.getName();
        String profile = memberInfoUpdateService.memberProfileChange(memberProfileDto.getFile(), phone);
        return profile;
    }


    @GetMapping("/memberInfo/profile/basic")
    @ResponseBody
    public String memberProfileBsic(Authentication authentication) {
        String phone = authentication.getName();
        String basic = memberInfoUpdateService.memberChangebasic(phone);
        return basic;
    }
}
