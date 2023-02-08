package com.cerdure.bookshelf.dto.order;

import com.cerdure.bookshelf.domain.enums.OrderState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSearchDto {

    private String name;
    private OrderState orderState;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private Integer page;

    @Builder
    public OrderSearchDto(String name, OrderState orderState, LocalDate startDate, LocalDate endDate, Integer page) {
        this.name = name;
        this.orderState = orderState;
        this.startDate = startDate;
        this.endDate = endDate;
        this.page = page;
    }
}
