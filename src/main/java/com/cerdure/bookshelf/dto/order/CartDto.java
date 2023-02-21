package com.cerdure.bookshelf.dto.order;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id;
    private Member member;
    private Book book;
    private Integer originPrice;
    private Integer discountPrice;
    private Integer amount;
    private List<Long> bookIds;
}
