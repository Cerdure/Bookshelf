package com.cerdure.bookshelf.dto.order;

import com.cerdure.bookshelf.domain.member.Address;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.Orders;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
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

    @Builder
    public OrderDto(String id, String receiver, String phone, String tel, String zipcode, String city, String street, String deliveryPlace, Integer originSum, Integer deliveryCharge, Integer point, Integer orderPrice, String payType, List<OrderItemDto> orderItems, Long bookId, Member member) {
        this.id = id;
        this.receiver = receiver;
        this.phone = phone;
        this.tel = tel;
        this.zipcode = zipcode;
        this.city = city;
        this.street = street;
        this.deliveryPlace = deliveryPlace;
        this.originSum = originSum;
        this.deliveryCharge = deliveryCharge;
        this.point = point;
        this.orderPrice = orderPrice;
        this.payType = payType;
        this.orderItems = orderItems;
        this.bookId = bookId;
        this.member = member;
    }

    public Orders toEntity(){
        return Orders.builder()
                .id(this.id)
                .receiver(this.receiver)
                .phone(this.phone)
                .tel(this.tel)
                .address(new Address(this.city, this.street, this.zipcode))
                .deliveryPlace(this.deliveryPlace)
                .originSum(this.originSum)
                .deliveryCharge(this.deliveryCharge)
                .point(this.point)
                .orderPrice(this.orderPrice)
                .payType(this.payType)
                .build();
    }
}
