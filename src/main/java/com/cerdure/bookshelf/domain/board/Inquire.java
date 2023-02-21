package com.cerdure.bookshelf.domain.board;

import com.cerdure.bookshelf.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inquire_id")
    private Long id;
    private String memberNickname;
    private String title;
    @Size(max = 10000)
    private String content;
    private Integer hits;
    private Integer reFlag;
    private Integer closed;
    private String pw;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "inquire", orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        this.closed = ofNullable(this.closed).orElse(0);
        this.hits = ofNullable(this.hits).orElse(0);
        this.reFlag = ofNullable(this.reFlag).orElse(0);
        this.regDate = ofNullable(this.regDate).orElse(LocalDateTime.now());
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeClosed(Integer closed) {
        this.closed = closed;
    }

    public void changePw(String pw) {
        this.pw = pw;
    }

    public void hitsPlus() {
        this.hits++;
    }

    public void changeReFlag(Integer reFlag) {
        this.reFlag = reFlag;
    }
}
