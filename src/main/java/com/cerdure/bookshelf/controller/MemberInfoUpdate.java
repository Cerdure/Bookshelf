package com.cerdure.bookshelf.controller;
import com.cerdure.bookshelf.dto.member.InfoUpdateDto;
import com.cerdure.bookshelf.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberInfoUpdate {

    private final MemberService memberService;

    @GetMapping("/memberInfo/update")
    public String memberInfoUpdateForm(Authentication authentication, Model model){
        InfoUpdateDto info = memberService.showInfo(authentication);
        model.addAttribute("memberInfo",info);
        return "memberInfoUpdate";
    }


}
