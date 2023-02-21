package com.cerdure.bookshelf.domain.board;

import com.cerdure.bookshelf.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Optional.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notice_id")
    private Long id;
    private String memberNickname;
    private String title;
    @Size(max = 10000)
    private String content;
    private Integer hits;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @PrePersist
    private void prePersist() {
        this.hits = ofNullable(this.hits).orElse(0);
        this.regDate = ofNullable(this.regDate).orElse(LocalDateTime.now());
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void hitsPlus() {
        this.hits++;
    }
}
