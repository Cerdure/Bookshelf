package com.cerdure.bookshelf.controller.book;

import com.cerdure.bookshelf.domain.board.Review;
import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.OrderItem;
import com.cerdure.bookshelf.dto.board.ReviewDto;
import com.cerdure.bookshelf.service.classes.board.interfaces.ReviewService;
import com.cerdure.bookshelf.service.classes.book.interfaces.BookService;
import com.cerdure.bookshelf.service.classes.event.interfaces.EventService;
import com.cerdure.bookshelf.service.classes.file.interfaces.UploadFileService;
import com.cerdure.bookshelf.service.classes.member.interfaces.MemberService;
import com.cerdure.bookshelf.service.classes.order.interfaces.OrderService;
import com.cerdure.bookshelf.utils.DataUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final MemberService memberService;
    private final ReviewService reviewService;
    private final OrderService orderService;
    private final UploadFileService uploadFileService;

    @GetMapping("/book{id}")
    public String bookDetail(@RequestParam("id") Long id, Authentication authentication, Model model) {
        if (authentication != null) {
            model.addAttribute("orderItem", orderService.findOrderItem(id, authentication).orElse(null));
            model.addAttribute("isBookmark", bookService.checkBookmark(id, authentication));
        }
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("dataUtils", new DataUtils());
        return "book-detail";
    }

    @GetMapping("/book/bookmark")
    @ResponseBody
    public Map<String, Object> bookmark(@RequestParam("bookId") Long bookId, Authentication authentication) {
        return bookService.bookmark(bookId, authentication);
    }

    @GetMapping("/book/bookmark/update")
    public String bookmarkUpdate(@RequestParam("bookId") Long bookId, Model model) {
        model.addAttribute("book", bookService.findById(bookId));
        return "book-detail :: .shelves";
    }

    @GetMapping("/bookReview/{reviewId}")
    public String findReview(@PathVariable("reviewId") Long reviewId, Model model) {
        model.addAttribute("modReview", reviewService.findById(reviewId));
        return "book-detail :: .review-modify-wrapper";
    }

    @PostMapping("/bookReview-modify/{reviewId}")
    public String modifyReview(@PathVariable("reviewId") Long reviewId,
                               @ModelAttribute ReviewDto reviewDto, Authentication authentication) throws Exception {
        reviewService.modify(reviewId, reviewDto, authentication);
        return "redirect:/book?id=" + reviewDto.getBookId();
    }

    @PostMapping("/bookReview-delete/{reviewId}/{bookId}")
    public String deleteReview(@PathVariable("reviewId") Long reviewId,
                               @PathVariable("bookId") Long bookId, Authentication authentication) throws Exception {
        reviewService.delete(reviewId, authentication);
        return "redirect:/book?id=" + bookId;
    }
}