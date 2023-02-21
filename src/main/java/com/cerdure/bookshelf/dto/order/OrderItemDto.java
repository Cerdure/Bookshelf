package com.cerdure.bookshelf.dto.order;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemDto {
    private String orderId;
    private Long bookId;
    private Integer amount;
}
