package com.cerdure.bookshelf.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @Column(name = "coupon_id")
    private Integer id;
    private Integer price;
    private Integer min;
    private LocalDateTime regDate;

    @PrePersist
    public void prePersist(){
        this.id = this.id == null ? this.price : this.id;
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
    }

    @Builder
    public Coupon(Integer id, Integer price, Integer min, LocalDateTime regDate) {
        this.id = id;
        this.price = price;
        this.min = min;
        this.regDate = regDate;
    }
}
