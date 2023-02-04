package com.cerdure.bookshelf.controller;

import com.cerdure.bookshelf.domain.enums.OrderState;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.Orders;
import com.cerdure.bookshelf.dto.order.OrderSearchDto;
import com.cerdure.bookshelf.dto.utils.DataUtils;
import com.cerdure.bookshelf.service.interfaces.MemberService;
import com.cerdure.bookshelf.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class MyshelfController {

    private final MemberService memberService;
    private final OrderService orderService;

    @GetMapping("/myshelf")
    public String myShelf(Authentication authentication, Model model) {
        Member member = memberService.findByPhone(authentication.getName());
        OrderSearchDto orderSearchDto = OrderSearchDto.builder()
                .startDate(LocalDate.now().minusMonths(1))
                .endDate(LocalDate.now())
                .orderState(OrderState.ALL)
                .name("")
                .page(1)
                .build();
        Page<Orders> orders = orderService.searchOrders(orderSearchDto, member);
        model.addAttribute("member", member);
        model.addAttribute("orders", orders);
        return "myshelf";
    }

    @PostMapping("/myshelf/order")
    public String myShelfOrder(@ModelAttribute OrderSearchDto orderSearchDto, Authentication authentication, Model model) {
        System.out.println("orderSearchDto = " + orderSearchDto);
        Member member = memberService.findByPhone(authentication.getName());
        Page<Orders> orders = orderService.searchOrders(orderSearchDto, member);
        model.addAttribute("member", member);
        model.addAttribute("orders", orders);
        model.addAttribute("orderSearchDto", orderSearchDto);
        return "myshelf :: #order-box";
    }

    @GetMapping("/myshelf/order/cancel")
    public String orderCancel(@ModelAttribute DataUtils dataUtils, Authentication authentication, Pageable pageable, Model model) {
//        Member member = memberService.findByPhone(authentication.getName());
//        Page<Orders> orders = orderService.findLastOrders(member, LocalDateTime.now().minusMonths(1), pageable);
//        model.addAttribute("member", member);
//        model.addAttribute("orders", orders);
        return "myshelf :: #order-box";
    }

    @GetMapping("/myshelf/order/item/cancel")
    public String orderItemCancel(@RequestParam("orderId") String orderId,
                                  @RequestParam("orderItemId") Long orderItemId,
                                  Authentication authentication, Pageable pageable, Model model) {

//        Member member = memberService.findByPhone(authentication.getName());
//        Page<Orders> orders = orderService.findLastOrders(member, LocalDateTime.now().minusMonths(1), pageable);
//        model.addAttribute("member", member);
//        model.addAttribute("orders", orders);
        return "myshelf :: #order-box";
    }
}
