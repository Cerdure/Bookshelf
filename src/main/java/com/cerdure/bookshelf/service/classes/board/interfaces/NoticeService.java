package com.cerdure.bookshelf.service.classes.board.interfaces;

import com.cerdure.bookshelf.domain.board.Notice;
import com.cerdure.bookshelf.dto.board.NoticeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface NoticeService {
    Page <Notice> findAll(Pageable pageable);
    Notice findById(Long noticeId);
    Page <Notice> findByTitle(String title, Long memberId, Pageable pageable);
    Page<Notice> findByContent(String memberNickname, Long memberId, Pageable pageable);
    Notice findPrevNotice(Notice notice);
    Notice findNextNotice(Notice notice);
    List<Notice> findLatest4();
    Long create(NoticeDto noticeDto, Authentication authentication);
    Notice modify(Long noticeId, NoticeDto noticeDto, Authentication authentication) throws Exception;
    void delete(Long noticeId, Authentication authentication) throws Exception;
    void hitsPlus(Notice notice);
}
