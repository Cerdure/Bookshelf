package com.cerdure.bookshelf.dto.order;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.member.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
public class CartDto {

    private Long id;
    private Member member;
    private Book book;
    private Integer originPrice;
    private Integer discountPrice;
    private Integer amount;
    private List<Long> bookIds;

    @Builder
    public CartDto(Long id, Member member, Book book, Integer originPrice, Integer discountPrice, Integer amount) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.originPrice = originPrice;
        this.discountPrice = discountPrice;
        this.amount = amount;
    }
}
