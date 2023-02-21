package com.cerdure.bookshelf.domain.board;

import com.cerdure.bookshelf.domain.file.UploadFile;
import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id")
    private Long id;
    @Size(max = 10000)
    private String content;
    private String tag;
    private Integer rating;
    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "review", orphanRemoval = true)
    private List<UploadFile> files = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        this.regDate = ofNullable(this.regDate).orElse(LocalDateTime.now());
    }

    public void changeRating(Integer rating) {
        this.rating = rating;
    }

    public void changeTag(String tag) {
        this.tag = tag;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeFiles(List<UploadFile> files) {
        this.files = files;
    }

}
