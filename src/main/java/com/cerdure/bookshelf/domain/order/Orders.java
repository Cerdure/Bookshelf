package com.cerdure.bookshelf.domain.order;

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

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;

    @Embedded
    private Address address;

    private String phone;
    private String requirement;
    private Integer totalPrice;

    private LocalDateTime regDate;

    @OneToMany(mappedBy = "order")
    private List<OrdersDetail> orderdetails = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
    }

    @Builder
    public Orders(Long id, Member member, String name, Address address, String phone, String requirement, int totalPrice, LocalDateTime regDate, List<OrdersDetail> orderdetails) {
        this.id = id;
        this.member = member;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.requirement = requirement;
        this.totalPrice = totalPrice;
        this.regDate = regDate;
        this.orderdetails = orderdetails;
    }
}
