package com.cerdure.bookshelf.service.classes.board.interfaces;

import com.cerdure.bookshelf.domain.board.Reply;
import com.cerdure.bookshelf.domain.board.Review;
import com.cerdure.bookshelf.dto.board.ReplyDto;
import com.cerdure.bookshelf.dto.board.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ReplyService {
    List<Reply> findAll();
    void replySave(ReplyDto replyDto, Long inquireId, Authentication authentication);
    void replyModify(ReplyDto replyDto);
    void replyDelete(ReplyDto replyDto);
}
