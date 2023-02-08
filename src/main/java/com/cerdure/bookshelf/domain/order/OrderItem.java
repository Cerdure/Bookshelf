package com.cerdure.bookshelf.domain.order;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.enums.OrderState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @PrePersist
    public void prePersist() {
        this.orderState = this.orderState == null ? OrderState.ORDER : this.orderState;
    }

    @Builder
    public OrderItem(Long id, Orders orders, Book book, Integer amount, OrderState orderState) {
        this.id = id;
        this.orders = orders;
        this.book = book;
        this.amount = amount;
        this.orderState = orderState;
    }

    public void changeBook(Book book){
        this.book = book;
    }
    public void changeState(OrderState orderState){
        this.orderState = orderState;
    }
}
