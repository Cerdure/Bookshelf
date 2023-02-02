package com.cerdure.bookshelf.domain.order;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.order.CartDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private Integer originPrice;

    private Integer discountPrice;

    private Integer amount;

    @Builder
    public Cart(Long id, Member member, Book book, Integer originPrice, Integer discountPrice, Integer amount) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.originPrice = originPrice;
        this.discountPrice = discountPrice;
        this.amount = amount;
    }

    public CartDto toDto(){
        return CartDto.builder()
                .id(this.id)
                .member(this.member)
                .book(this.book)
                .originPrice(this.originPrice)
                .discountPrice(this.discountPrice)
                .amount(this.amount)
                .build();
    }

    public void changeAmount(Integer amount){this.amount = amount;}
}
