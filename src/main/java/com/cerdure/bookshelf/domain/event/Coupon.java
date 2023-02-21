package com.cerdure.bookshelf.domain.event;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @Column(name = "coupon_id")
    private Integer id;
    private Integer price;
    private Integer min;
    private LocalDateTime regDate;

    @PrePersist
    private void prePersist() {
        this.id = ofNullable(this.id).orElse(this.price);
        this.regDate = ofNullable(this.regDate).orElse(LocalDateTime.now());
    }
}
