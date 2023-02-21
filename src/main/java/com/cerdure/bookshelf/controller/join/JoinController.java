package com.cerdure.bookshelf.controller.join;

import com.cerdure.bookshelf.dto.member.MemberDto;
import com.cerdure.bookshelf.service.classes.member.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm(@ModelAttribute("memberDto") MemberDto memberDto) {
        return "join";
    }

    @PostMapping("/join")
    @ResponseBody
    public Map<String, Object> join(@ModelAttribute("memberDto") MemberDto memberDto) {
        return memberService.join(memberDto);
    }

    @GetMapping("/verify/send")
    @ResponseBody
    public Map<String, Object> verifySend(@RequestParam("phone") String phone) {
        return memberService.verifyCodeSend(phone);
    }

    @GetMapping("/verify/check")
    @ResponseBody
    public Map<String, Object> verifyCheck(@RequestParam("phone") String phone,
                                           @RequestParam("code") String code) {
        return memberService.verifyResolver(phone, code);
    }

    @GetMapping("/verify/expire")
    @ResponseBody
    public Boolean verifyExpire(@RequestParam("phone") String phone) {
        return memberService.verifyExpire(phone);
    }
}
