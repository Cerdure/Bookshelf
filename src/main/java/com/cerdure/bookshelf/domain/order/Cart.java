package com.cerdure.bookshelf.domain.order;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.order.CartDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static java.util.Optional.ofNullable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    private Long id;
    private Integer originPrice;
    private Integer discountPrice;
    private Integer amount;
    private Boolean selected;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @PrePersist
    private void prePersist() {
        this.selected = ofNullable(this.selected).orElse(true);
    }

    public void changeAmount(Integer amount) {
        this.amount = amount;
    }

    public void changeSelected(boolean selected) {
        this.selected = selected;
    }
}
