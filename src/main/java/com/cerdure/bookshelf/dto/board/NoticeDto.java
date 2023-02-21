package com.cerdure.bookshelf.dto.board;

import com.cerdure.bookshelf.domain.board.Notice;
import com.cerdure.bookshelf.domain.member.Member;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeDto {

    private Long id;
    private Member member;
    private String memberNickname;
    private Long memberId;
    private String title;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private Integer hits;
    private Integer searchBy;
    private String input;

    public Notice toEntity() {
        return Notice.builder()
                .member(this.member)
                .memberNickname((this.memberNickname))
                .title(this.title)
                .content(this.content)
                .modDate(this.modDate)
                .hits(this.hits)
                .build();
    }
}
