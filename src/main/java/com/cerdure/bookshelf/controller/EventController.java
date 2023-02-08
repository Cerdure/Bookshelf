package com.cerdure.bookshelf.controller;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.book.BookDto;
import com.cerdure.bookshelf.service.interfaces.BookService;
import com.cerdure.bookshelf.service.interfaces.EventService;
import com.cerdure.bookshelf.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final MemberService memberService;
    private final BookService bookService;
    private final EventService eventService;

    @GetMapping("/event")
    public String event(Authentication authentication, Model model){
        List<Book> bannerBooks = bookService.findDiscountTop16();
        List<Book> saleBooks = bookService.findDiscountTop18();
        model.addAttribute("bannerBooks", bannerBooks);
        model.addAttribute("saleBooks", saleBooks);
        if(authentication != null){
            Member member = memberService.findMember(authentication);
            eventService.syncEventState(member);
            eventService.syncMemberGrade(member);
            model.addAttribute("member", member);
            System.out.println("member.getGrade() = " + member.getGrade());
        }
        return "event";
    }

    @GetMapping("/event-sale")
    public String homeSearchInput(@ModelAttribute BookDto bookDto, Model model) {
        String inputVal = bookDto.getName();
        if(inputVal!=""){
            List<Book> books = bookService.findByNameWithDiscount(inputVal, 30);
            if(books != null){
                books.forEach(e -> e.coincidenceHighlight(inputVal));
            }
            model.addAttribute("ipBooks", books);
        }
        return "event :: #search-input-results";
    }

    @GetMapping("/event/point")
    @ResponseBody
    public String eventPoint(Authentication authentication){
        try {
            eventService.giveMonthlyPoint(authentication);
        } catch (IllegalStateException e) {
            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "ok";
    }

    @GetMapping("/event/coupon")
    @ResponseBody
    public String eventCoupon(Authentication authentication){
        try {
            eventService.giveMonthlyCoupon(authentication);
        } catch (IllegalStateException e) {
            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "ok";
    }
}
