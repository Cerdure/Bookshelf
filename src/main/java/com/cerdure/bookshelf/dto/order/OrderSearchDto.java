package com.cerdure.bookshelf.dto.order;

import com.cerdure.bookshelf.domain.enums.OrderState;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSearchDto {
    private String name;
    private OrderState orderState;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private Integer page;
}
