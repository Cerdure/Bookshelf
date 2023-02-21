package com.cerdure.bookshelf.dto.board;

import com.cerdure.bookshelf.domain.board.Inquire;
import com.cerdure.bookshelf.domain.board.Reply;
import com.cerdure.bookshelf.domain.member.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquireDto {

    private Long id;
    private Member member;
    private String memberNickname;
    private String title;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private Integer closed;
    private String pw;
    private Integer hits;
    private Integer reFlag;
    private List<Reply> replies;
    private Integer searchBy;
    private String input;

    public Inquire toEntity() {
        return Inquire.builder()
                .member(this.member)
                .memberNickname(this.memberNickname)
                .title(this.title)
                .content(this.content)
                .regDate(this.regDate)
                .modDate(this.modDate)
                .closed(this.closed)
                .pw(this.pw)
                .hits(this.hits)
                .reFlag(this.reFlag)
                .replies(this.replies)
                .build();
    }
}
