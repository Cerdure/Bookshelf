package com.cerdure.bookshelf.domain.book;

import com.cerdure.bookshelf.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDateTime regDate;

    @PrePersist
    private void prePersist() {
        this.regDate = ofNullable(this.regDate).orElse(LocalDateTime.now());
    }
}
