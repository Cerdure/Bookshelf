package com.cerdure.bookshelf.dto.board;


import com.cerdure.bookshelf.domain.board.Review;
import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.member.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDto {

    private Long id;
    private Long bookId;
    private Book book;
    private Member member;
    private String content;
    private String tag;
    private Integer rating;
    private LocalDateTime regDate;
    private List<MultipartFile> imageFiles;

    public Review toEntity() {
        return Review.builder()
                .book(this.book)
                .member(this.member)
                .content(this.content)
                .tag(this.tag)
                .rating(this.rating)
                .regDate(this.regDate)
                .build();
    }
}
