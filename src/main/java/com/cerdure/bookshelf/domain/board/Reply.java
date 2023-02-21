package com.cerdure.bookshelf.domain.board;

import com.cerdure.bookshelf.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reply_id")
    private Long id;
    @Size(max = 3000)
    private String content;
    private Integer seq;
    private Integer level;
    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquire_id")
    private Inquire inquire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_reply_id")
    private Reply parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Reply> children;

    @PrePersist
    private void prePersist() {
        this.level = ofNullable(this.level).orElse(0);
        this.regDate = ofNullable(this.regDate).orElse(LocalDateTime.now());
    }

    public void changeLevel(Integer level) {
        this.level = level;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void delete() {
        this.member = null;
        this.regDate = null;
        this.content = "삭제된 댓글입니다.";
    }
}
