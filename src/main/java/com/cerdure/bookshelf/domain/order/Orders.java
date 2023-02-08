package com.cerdure.bookshelf.domain.order;

import com.cerdure.bookshelf.domain.enums.OrderState;
import com.cerdure.bookshelf.domain.member.Address;
import com.cerdure.bookshelf.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @Column(name = "orders_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member orderer;

    private String receiver;

    private String phone;

    private String tel;

    @Embedded
    private Address address;

    private String deliveryPlace;

    private Integer originSum;

    private Integer deliveryCharge;

    private Integer point;

    private Integer orderPrice;

    private String payType;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    private LocalDateTime regDate;

    @OneToMany(mappedBy = "orders")
    private List<OrderItem> orderItems = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.orderState = this.orderState == null ? OrderState.ORDER : this.orderState;
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
    }

    @Builder
    public Orders(String id, Member orderer, String receiver, String phone, String tel, Address address, String deliveryPlace, Integer originSum, Integer deliveryCharge, Integer point, Integer orderPrice, String payType, OrderState orderState, LocalDateTime regDate, List<OrderItem> orderItems) {
        this.id = id;
        this.orderer = orderer;
        this.receiver = receiver;
        this.phone = phone;
        this.tel = tel;
        this.address = address;
        this.deliveryPlace = deliveryPlace;
        this.originSum = originSum;
        this.deliveryCharge = deliveryCharge;
        this.point = point;
        this.orderPrice = orderPrice;
        this.payType = payType;
        this.orderState = orderState;
        this.regDate = regDate;
        this.orderItems = orderItems;
    }

    public void changeId(String id){
        this.id = id;
    }

    public void changeOrderer(Member orderer){
        this.orderer = orderer;
    }

    public void changeState(OrderState orderState){
        this.orderState = orderState;
    }

    public void changeOrderItems(List<OrderItem> orderItems){
        this.orderItems = orderItems;
    }
}
