package com.cerdure.bookshelf.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    @Id @GeneratedValue
    @Column(name = "member_coupon_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private LocalDateTime regDate;

    private LocalDateTime useDate;

    @PrePersist
    public void prePersist(){
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
    }

    @Builder
    public MemberCoupon(Long id, Member member, Coupon coupon, LocalDateTime regDate) {
        this.id = id;
        this.member = member;
        this.coupon = coupon;
        this.regDate = regDate;
    }

    public void changeUseDate(LocalDateTime useDate){
        this.useDate = useDate;
    }
}
