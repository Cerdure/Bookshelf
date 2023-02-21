package com.cerdure.bookshelf.controller.home;

import com.cerdure.bookshelf.service.classes.board.interfaces.NoticeService;
import com.cerdure.bookshelf.service.classes.book.interfaces.BookService;
import com.cerdure.bookshelf.service.classes.member.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final BookService bookService;
    private final NoticeService noticeService;

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        if (authentication != null) model.addAttribute("member", memberService.findMember(authentication));
        Map<String, Object> map = bookService.randomHomeBooks();
        model.addAttribute("notices", noticeService.findLatest4());
        model.addAttribute("bannerBooks", map.get("bannerBooks"));
        model.addAttribute("todayBooks", map.get("todayBooks"));
        model.addAttribute("bestBooks", map.get("bestBooks"));
        return "home";
    }

    @GetMapping("/todayBook-reset")
    public String todayBookReset(Model model) {
        model.addAttribute("todayBooks", bookService.resetTodayBook());
        return "home :: .today-book-wrapper";
    }

    @GetMapping("/bestBook/{criteria}")
    public String todayBookReset(@PathVariable("criteria") Integer criteria, Model model) {
        model.addAttribute("bestBooks", bookService.findTop10(criteria));
        return "home :: .best-books-wrapper";
    }
}