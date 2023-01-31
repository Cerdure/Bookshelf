package com.cerdure.bookshelf.controller;

import com.cerdure.bookshelf.dto.order.CartDto;
import com.cerdure.bookshelf.service.interfaces.OrderService;
import com.cerdure.bookshelf.web.exception.NotEnoughStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/cart")
    public String cart(Authentication authentication, Model model) {
        model.addAttribute("carts", orderService.findAllCart(authentication));
        return "cart";
    }

    @GetMapping("/cart/add")
    @ResponseBody
    public String addCart(@RequestParam("bookId") Long bookId, Authentication authentication) {
        try {
            orderService.addCart(bookId, authentication);
        } catch (NotEnoughStockException e) {
            e.printStackTrace();
            return "sold";
        } catch (Exception e) {
            return "error";
        }
        return "ok";
    }

    @GetMapping("/cart/minus")
    @ResponseBody
    public String minusCart(@RequestParam("bookId") Long bookId, Authentication authentication) {
        try {
            orderService.addCart(bookId, authentication);
        } catch (NotEnoughStockException e) {
            e.printStackTrace();
            return "sold";
        } catch (Exception e) {
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
    public String cartSum(Authentication authentication, Model model){
        model.addAttribute("carts", orderService.findAllCart(authentication));
        return "cart :: #sum";
    }
}
