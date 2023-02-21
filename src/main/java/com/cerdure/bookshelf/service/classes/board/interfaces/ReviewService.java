package com.cerdure.bookshelf.service.classes.board.interfaces;

import com.cerdure.bookshelf.domain.board.Review;
import com.cerdure.bookshelf.dto.board.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.Map;

public interface ReviewService {
    Map<String, Object> create(ReviewDto reviewDto, Authentication authentication) throws IOException;
    Page <Review> findAll(Pageable pageable);
    Review findById(Long reviewId);
    Page <Review> findByBookId(Long bookId, Pageable pageable);
    Page <Review> findByMemberId(Long memberId, Pageable pageable);
    void modify(Long reviewId, ReviewDto reviewDto, Authentication authentication) throws Exception;
    void delete(Long reviewId, Authentication authentication) throws Exception;
}
