package com.cerdure.bookshelf.dto.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemDto {

    private String orderId;
    private Long bookId;
    private Integer amount;

    @Builder
    public OrderItemDto(Long bookId, Integer amount) {
        this.bookId = bookId;
        this.amount = amount;
    }
}
