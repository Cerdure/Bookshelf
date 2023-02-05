package com.cerdure.bookshelf.controller;

import com.cerdure.bookshelf.dto.member.MemberDto;
import com.cerdure.bookshelf.service.MemberServiceImpl;
import com.cerdure.bookshelf.service.interfaces.MemberInfoUpdateService;
import com.cerdure.bookshelf.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final MemberServiceImpl memberService;
    private final MemberInfoUpdateService memberInfoUpdateService;
    @GetMapping("/join")
    public String joinForm(@ModelAttribute("memberDto") MemberDto memberDto, BindingResult bindingResult){
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute("memberDto") MemberDto memberDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "join";
        }
        memberService.join(memberDto);
        return "redirect:/";
    }
    @GetMapping("/join/makeCheckNum")
    @ResponseBody
    public String join(@RequestParam("phone") String phone) {
        String num = memberInfoUpdateService.makeNum(phone);
        return num;
    }
}
