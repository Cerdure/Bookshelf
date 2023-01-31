package com.cerdure.bookshelf.dto.order;

import com.cerdure.bookshelf.domain.member.Address;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.OrdersDetail;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto {

    private Long id;
    private Member member;
    private String name;
    private Address address;
    private String phone;
    private String requirement;
    private Integer totalPrice;
    private LocalDateTime regDate;
    private List<OrdersDetail> orderDetails = new ArrayList<>();
    private Long bookId;

    @Builder
    public OrderDto(Long id, Member member, String name, Address address, String phone, String requirement, Integer totalPrice, LocalDateTime regDate, List<OrdersDetail> orderDetails) {
        this.id = id;
        this.member = member;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.requirement = requirement;
        this.totalPrice = totalPrice;
        this.regDate = regDate;
        this.orderDetails = orderDetails;
    }
}
