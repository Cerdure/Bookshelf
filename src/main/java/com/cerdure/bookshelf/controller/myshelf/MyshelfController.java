package com.cerdure.bookshelf.controller.myshelf;

import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.member.MemberDto;
import com.cerdure.bookshelf.dto.order.OrderSearchDto;
import com.cerdure.bookshelf.service.classes.member.interfaces.MemberService;
import com.cerdure.bookshelf.service.classes.order.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MyshelfController {

    private final MemberService memberService;
    private final OrderService orderService;

    @GetMapping("/myshelf")
    public String myShelf(Authentication authentication, Model model) {
        Map<String, Object> map = orderService.searchReset(authentication);
        model.addAttribute("member", map.get("member"));
        model.addAttribute("orders", map.get("orders"));
        model.addAttribute("orderItems", orderService.findDistinctOrderItems(authentication));
        return "myshelf";
    }

    @PostMapping("/myshelf/order")
    public String myShelfOrder(@ModelAttribute OrderSearchDto orderSearchDto, Authentication authentication, Model model) {
        Member member = memberService.findMember(authentication);
        model.addAttribute("orders", orderService.searchOrders(orderSearchDto, member));
        model.addAttribute("orderSearchDto", orderSearchDto);
        model.addAttribute("member", member);
        return "myshelf :: #order-box";
    }

    @GetMapping("/myshelf/order/detail")
    public String myShelfOrderDetail(@RequestParam("orderId") String orderId, Authentication authentication, Model model) {
        model.addAttribute("order", orderService.findOrder(orderId, authentication));
        return "myshelf :: .order-detail";
    }

    @PostMapping("/myshelf/order/add")
    public String myShelfOrderAdd(@ModelAttribute OrderSearchDto orderSearchDto, Authentication authentication, Model model) {
        Member member = memberService.findMember(authentication);
        model.addAttribute("orders", orderService.searchOrders(orderSearchDto, member));
        model.addAttribute("orderSearchDto", orderSearchDto);
        model.addAttribute("member", member);
        return "myshelf :: #page-box";
    }

    @GetMapping("/myshelf/order/reset")
    public String myShelfOrderReset(Authentication authentication, Model model) {
        Map<String, Object> map = orderService.searchReset(authentication);
        model.addAttribute("member", map.get("member"));
        model.addAttribute("orders", map.get("orders"));
        return "myshelf :: #order-box";
    }

    @GetMapping("/myshelf/order/cancel")
    @ResponseBody
    public Boolean orderCancel(@RequestParam("orderId") String orderId, Authentication authentication) {
        orderService.cancelOrder(orderId, authentication);
        return true;
    }

    @GetMapping("/myshelf/order/item/cancel")
    @ResponseBody
    public Boolean orderItemCancel(@RequestParam("orderId") String orderId,
                                   @RequestParam("orderItemId") Long orderItemId, Authentication authentication) {
        orderService.cancelOrderItem(orderId, orderItemId, authentication);
        return true;
    }

    @PostMapping("/myshelf/info/update")
    @ResponseBody
    public Map<String, Object> updateInfo(@ModelAttribute MemberDto memberDto, Authentication authentication) {
        return memberService.updateInfo(memberDto, authentication);
    }

    @GetMapping("/myshelf/info/profile/reset")
    @ResponseBody
    public Map<String, Object> profileReset(Authentication authentication) {
        return memberService.resetProfile(authentication);
    }

    @PostMapping("/myshelf/leave")
    @ResponseBody
    public Map<String, Object> leave(Authentication authentication, HttpServletRequest request) {
        return memberService.leave(authentication, request);
    }
}
