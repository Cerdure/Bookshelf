package com.cerdure.bookshelf.dto.order;

import com.cerdure.bookshelf.domain.member.Address;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.Orders;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto {

    private String id;
    private String receiver;
    private String phone;
    private String tel;
    private String zipcode;
    private String city;
    private String street;
    private String deliveryPlace;
    private Integer originSum;
    private Integer deliveryCharge;
    private Long couponId;
    private Integer point;
    private Integer orderPrice;
    private String payType;
    private List<OrderItemDto> orderItems;
    private Long bookId;
    private Member member;

    public Orders toEntity() {
        return Orders.builder()
                .id(this.id)
                .receiver(this.receiver)
                .phone(this.phone)
                .tel(this.tel)
                .address(getAddress())
                .deliveryPlace(this.deliveryPlace)
                .originSum(this.originSum)
                .deliveryCharge(this.deliveryCharge)
                .point(this.point)
                .orderPrice(this.orderPrice)
                .payType(this.payType)
                .build();
    }

    public Address getAddress() {
        return new Address(this.city, this.street, this.zipcode);
    }
}
