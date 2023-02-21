package com.cerdure.bookshelf.service.classes.board.interfaces;

import com.cerdure.bookshelf.domain.board.Inquire;
import com.cerdure.bookshelf.domain.board.Review;
import com.cerdure.bookshelf.dto.board.InquireDto;
import com.cerdure.bookshelf.dto.board.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface InquireService {
    Long create(InquireDto inquireDto, Authentication authentication);
    Page <Inquire> findAll(Pageable pageable);
    Inquire findById(Long inquireId);
    Page <Inquire> findByTitle(String title, Pageable pageable);
    Page <Inquire> findByMemberId(Long memberId, Pageable pageable);
    Page<Inquire> findByMemberNickname(String memberNickname, Pageable pageable);
    Inquire findPrevInquire(Inquire inquire);
    Inquire findNextInquire(Inquire inquire);
    Inquire modify(Long inquireId, InquireDto inquireDto, Authentication authentication) throws Exception;
    void delete(Long inquireId, Authentication authentication) throws Exception;
    void hitsPlus(Inquire inquire);
}
