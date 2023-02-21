package com.cerdure.bookshelf.service.classes.board;

import com.cerdure.bookshelf.domain.board.Review;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.board.ReviewDto;
import com.cerdure.bookshelf.repository.board.ReviewRepository;
import com.cerdure.bookshelf.service.classes.board.interfaces.ReviewService;
import com.cerdure.bookshelf.service.classes.book.interfaces.BookService;
import com.cerdure.bookshelf.service.classes.event.interfaces.EventService;
import com.cerdure.bookshelf.service.classes.file.interfaces.UploadFileService;
import com.cerdure.bookshelf.service.classes.member.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final BookService bookService;
    private final EventService eventService;
    private final MemberService memberService;
    private final UploadFileService uploadFileService;
    private final ReviewRepository reviewRepository;

    @Override
    public Map<String, Object> create(ReviewDto reviewDto, Authentication authentication) {
        Map<String, Object> map = new HashMap<>();
        try {
            Member member = memberService.findByPhone(authentication.getName());
            reviewDto.setMember(member);
            reviewDto.setBook(bookService.findById(reviewDto.getBookId()));
            Review review = reviewRepository.save(reviewDto.toEntity());
            uploadFileService.fileSaveResolver(reviewDto, review.getId());
            map = eventService.reviewEventResolver(reviewDto, authentication, map);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("error", e.getMessage());
        }
        map.put("success", true);
        return map;
    }

    @Override
    public Page<Review> findAll(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 3, Sort.by("regDate").descending());
        return reviewRepository.findAll(pageable);
    }

    @Override
    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId).get();
    }

    @Override
    public Page<Review> findByBookId(Long bookId, Pageable pageable) {
        return reviewRepository.findByBookId(bookId, pageable);
    }

    @Override
    public Page<Review> findByMemberId(Long memberId, Pageable pageable) {
        return reviewRepository.findByMemberId(memberId, pageable);
    }

    @Override
    public void modify(Long reviewId, ReviewDto reviewDto, Authentication authentication) throws Exception {
        uploadFileService.deleteFilesByReviewId(reviewId, reviewDto);
        uploadFileService.fileSaveResolver(reviewDto, reviewId);
        Review review = reviewRepository.findById(reviewId).get();
        review.changeRating(reviewDto.getRating());
        review.changeTag(reviewDto.getTag());
        review.changeContent(reviewDto.getContent());
        reviewRepository.save(review);
    }

    @Override
    public void delete(Long reviewId, Authentication authentication) throws Exception {
        Review review = reviewRepository.findById(reviewId).get();
        reviewRepository.delete(review);
    }
}
