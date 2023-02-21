package com.cerdure.bookshelf.domain.member;

import com.cerdure.bookshelf.domain.event.Coupon;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    @Id
    @GeneratedValue
    @Column(name = "member_coupon_id")
    private Long id;
    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @PrePersist
    private void prePersist() {
        this.regDate = ofNullable(this.regDate).orElse(LocalDateTime.now());
    }
}
