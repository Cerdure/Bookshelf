package com.cerdure.bookshelf.dto.order;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.member.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartRemoveDto {

    private Long id;
    private Member member;
    private Book book;
    private Integer originPrice;
    private Integer discountPrice;
    private Integer amount;
    private Boolean checked;
    private List<Long> bookIds;

    @Builder
    public CartRemoveDto(Long id, Member member, Book book, Integer originPrice, Integer discountPrice, Integer amount, Boolean checked, List<Long> bookIds) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.originPrice = originPrice;
        this.discountPrice = discountPrice;
        this.amount = amount;
        this.checked = checked;
        this.bookIds = bookIds;
    }
}
