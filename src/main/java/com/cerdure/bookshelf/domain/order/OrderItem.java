package com.cerdure.bookshelf.domain.order;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.enums.OrderState;
import lombok.*;

import javax.persistence.*;

import static com.cerdure.bookshelf.domain.enums.OrderState.ORDER;
import static java.util.Optional.ofNullable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_item_id")
    private Long id;
    private Integer amount;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private Boolean reviewed;

    @PrePersist
    private void prePersist() {
        this.orderState = ofNullable(this.orderState).orElse(ORDER);
        this.reviewed = ofNullable(this.reviewed).orElse(false);
    }

    public void changeBook(Book book) {
        this.book = book;
    }

    public void changeState(OrderState orderState) {
        this.orderState = orderState;
    }

    public void changeReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }
}
