package com.cerdure.bookshelf.controller.borad;

import com.cerdure.bookshelf.domain.board.Review;
import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.board.ReviewDto;
import com.cerdure.bookshelf.service.classes.board.interfaces.ReviewService;
import com.cerdure.bookshelf.service.classes.book.interfaces.BookService;
import com.cerdure.bookshelf.service.classes.file.interfaces.UploadFileService;
import com.cerdure.bookshelf.service.classes.member.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final BookService bookService;
    private final MemberService memberService;
    private final UploadFileService uploadFileService;

    @GetMapping("/review")
    public String allReviews(@ModelAttribute("reviewDto") ReviewDto reviewDto, Model model, Pageable pageable) {
        model.addAttribute("reviews", reviewService.findAll(pageable));
        return "board-review";
    }

    @GetMapping("/review/{reviewId}")
    public String findReview(@PathVariable("reviewId") Long reviewId, Model model) {
        model.addAttribute("modReview", reviewService.findById(reviewId));
        return "board-review :: .review-modify-wrapper";
    }

    @GetMapping("/review/book-search")
    public String findBook(@RequestParam Map<String, Object> paramMap, Model model, Pageable pageable) {
        Page<Book> books = bookService.findByName(paramMap.get("name").toString(), paramMap.get("sortOrder").toString(), pageable);
        model.addAttribute("books", books);
        return "board-review :: #search-results";
    }

    @PostMapping("/review")
    @ResponseBody
    public Map<String, Object> createReview(@ModelAttribute ReviewDto reviewDto, Authentication authentication) throws Exception {
        return reviewService.create(reviewDto, authentication);
    }

    @PostMapping("/review-modify/{reviewId}")
    public String modify(@PathVariable("reviewId") Long reviewId,
                         @ModelAttribute ReviewDto reviewDto,
                         Authentication authentication, Model model, Pageable pageable) throws Exception {
        reviewService.modify(reviewId, reviewDto, authentication);
        model.addAttribute("reviews", reviewService.findAll(pageable));
        return "board-review";
    }

    @PostMapping("/review-delete/{reviewId}")
    public String delete(@PathVariable("reviewId") Long reviewId,
                         Authentication authentication, Model model, Pageable pageable) throws Exception {
        reviewService.delete(reviewId, authentication);
        model.addAttribute("reviews", reviewService.findAll(pageable));
        return "board-review";
    }

    @GetMapping("/review-my")
    public String myReview(Authentication authentication, Model model, Pageable pageable) {
        Member member = memberService.findByPhone(authentication.getName());
        Page<Review> reviews = reviewService.findByMemberId(member.getId(), pageable);
        model.addAttribute("reviews", reviews);
        return "board-review";
    }
}
