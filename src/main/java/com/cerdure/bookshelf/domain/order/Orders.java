package com.cerdure.bookshelf.domain.order;

import com.cerdure.bookshelf.domain.enums.OrderState;
import com.cerdure.bookshelf.domain.event.Coupon;
import com.cerdure.bookshelf.domain.member.Address;
import com.cerdure.bookshelf.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.cerdure.bookshelf.domain.enums.OrderState.ORDER;
import static java.util.Optional.ofNullable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @Column(name = "orders_id")
    private String id;
    private String receiver;
    private String phone;
    private String tel;
    private String deliveryPlace;
    private Integer originSum;
    private Integer deliveryCharge;
    private Integer point;
    private Integer orderPrice;
    private String payType;
    private LocalDateTime regDate;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member orderer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @OneToMany(mappedBy = "orders")
    private List<OrderItem> orderItems = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        this.orderState = ofNullable(this.orderState).orElse(ORDER);
        this.regDate = ofNullable(this.regDate).orElse(LocalDateTime.now());
    }

    public void changeOrderer(Member orderer) {
        this.orderer = orderer;
    }

    public void changeCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public void changeState(OrderState orderState) {
        this.orderState = orderState;
    }

    public void changeOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
