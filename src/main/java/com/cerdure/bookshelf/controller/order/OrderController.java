package com.cerdure.bookshelf.controller.order;

import com.cerdure.bookshelf.dto.order.CartDto;
import com.cerdure.bookshelf.dto.order.OrderDto;
import com.cerdure.bookshelf.dto.order.OrderItemDto;
import com.cerdure.bookshelf.service.classes.event.interfaces.EventService;
import com.cerdure.bookshelf.service.classes.member.interfaces.MemberService;
import com.cerdure.bookshelf.service.classes.order.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final EventService eventService;
    private final MemberService memberService;

    @GetMapping("/cart")
    public String cart(Authentication authentication, Model model) {
        model.addAttribute("carts", orderService.findAllCart(authentication));
        return "cart";
    }

    @PostMapping("/cart/select")
    @ResponseBody
    public Map<String, Object> cartItemSelect(@ModelAttribute CartDto cartDto, Authentication authentication) {
        return orderService.syncItemSelect(cartDto, authentication);
    }

    @GetMapping("/cart/modify")
    @ResponseBody
    public String modifyCart(@RequestParam("bookId") Long bookId,
                             @RequestParam("amount") Integer amount, Authentication authentication) {
        try {
            Object result = orderService.modifyCart(bookId, amount, authentication);
            if (result instanceof Integer) {
                return result.toString();
            }
        } catch (IllegalStateException e) {
            return "min";
        } catch (NoSuchElementException e) {
            return "error";
        }
        return "ok";
    }

    @PostMapping("/cart/remove")
    @ResponseBody
    public Boolean removeCart(@ModelAttribute CartDto cartDto, Authentication authentication) {
        orderService.removeCart(cartDto, authentication);
        return true;
    }

    @GetMapping("/cart/sum")
    public String cartSum(Authentication authentication, Model model) {
        model.addAttribute("carts", orderService.findAllSelectedCart(authentication));
        return "cart :: #sum";
    }

    @GetMapping("/order")
    public String order(@ModelAttribute OrderDto orderDto, Authentication authentication, Model model) {
        model.addAttribute("member", memberService.findMember(authentication));
        model.addAttribute("orders", orderService.newOrder(orderDto, authentication));
        return "order";
    }

    @GetMapping("/order/info")
    public String orderInfo(@RequestParam("type") String type, Authentication authentication, Model model) {
        if (type.equals("last")) model.addAttribute("last", orderService.findLastOrder(authentication));
        model.addAttribute("member", memberService.findMember(authentication));
        model.addAttribute("infoType", type);
        return "order :: #info";
    }

    @GetMapping("/order/point/rest")
    @ResponseBody
    public Integer pointCheck(@RequestParam("point") Integer point, Authentication authentication) {
        Integer restPoint = orderService.restPoint(point, authentication);
        return restPoint;
    }

    @GetMapping("/order/create/prev")
    @ResponseBody
    public OrderDto orderCreatePrev(Authentication authentication) {
        return orderService.memberAndCode(authentication);
    }

    @PostMapping("/order/create")
    @ResponseBody
    public Boolean orderCreate(@ModelAttribute OrderDto orderDto, Authentication authentication) {
        try {
            orderService.createOrder(orderDto, authentication);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/order/item")
    @ResponseBody
    public Boolean orderItem(@ModelAttribute OrderItemDto orderItemDto, Authentication authentication) {
        return orderService.saveOrderItems(orderItemDto, authentication);
    }

    @GetMapping("/order/success")
    public String orderSuccess(@RequestParam("orderId") String orderId,
                               @RequestParam("couponId") Long couponId,
                               @RequestParam("point") Integer point, Authentication authentication, Model model) {
        orderService.clearCart(authentication);
        memberService.changePoint(authentication, point, true);
        if (couponId > -1) memberService.useCoupon(couponId);
        eventService.syncEventState(memberService.findMember(authentication));
        eventService.syncMemberGrade(memberService.findMember(authentication));
        model.addAttribute("orderId", orderId);
        model.addAttribute("point", point);
        return "order-success";
    }
}
