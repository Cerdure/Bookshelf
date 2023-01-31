package com.cerdure.bookshelf.controller;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.order.Cart;
import com.cerdure.bookshelf.dto.order.CartDto;
import com.cerdure.bookshelf.dto.order.OrderDto;
import com.cerdure.bookshelf.service.interfaces.BookService;
import com.cerdure.bookshelf.service.interfaces.OrderService;
import com.cerdure.bookshelf.web.exception.NotEnoughStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final BookService bookService;

    @GetMapping("/cart")
    public String cart(Authentication authentication, Model model) {
        model.addAttribute("carts", orderService.findAllCart(authentication));
        return "cart";
    }

    @GetMapping("/cart/modify")
    @ResponseBody
    public String modifyCart(@RequestParam("bookId") Long bookId,
                             @RequestParam("amount") Integer amount, Authentication authentication) {
        try {
            Object result = orderService.modifyCart(bookId, amount, authentication);
            if(result instanceof Integer){
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
    public String cartSum(Authentication authentication, Model model){
        model.addAttribute("carts", orderService.findAllCart(authentication));
        return "cart :: #sum";
    }

    @GetMapping("/order")
    public String order(@ModelAttribute OrderDto orderDto, Authentication authentication, Model model){
        model.addAttribute("orders", orderService.getOrder(orderDto, authentication));
        return "order";
    }
}
