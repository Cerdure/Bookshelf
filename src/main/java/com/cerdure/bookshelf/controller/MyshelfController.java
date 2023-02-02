package com.cerdure.bookshelf.controller;

import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyshelfController {

    private final MemberServiceImpl memberService;

    @GetMapping("/myshelf")
    public String myshelf(Authentication authentication, Model model){
        Member member = memberService.findByPhone(authentication.getName());
        model.addAttribute("member",member);
        return "myshelf";
    }
}
