package com.cerdure.bookshelf.dto.board;

import com.cerdure.bookshelf.domain.board.Inquire;
import com.cerdure.bookshelf.domain.board.Reply;
import com.cerdure.bookshelf.domain.member.Member;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyDto {

    private Long id;
    private Inquire inquire;
    private Member member;
    private LocalDateTime regDate;
    private String content;
    private Long replyId;
    private Reply parent;
    private Integer level;
    private Long parentId;

    public Reply toEntity() {
        return Reply.builder()
                .inquire(this.inquire)
                .member(this.member)
                .regDate(this.regDate)
                .content(this.content)
                .parent(this.parent)
                .level(this.level)
                .build();
    }
}
