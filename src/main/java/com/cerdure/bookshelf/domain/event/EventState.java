package com.cerdure.bookshelf.domain.event;

import com.cerdure.bookshelf.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventState {

    @Id
    @GeneratedValue
    @Column(name = "event_state_id")
    private Long id;
    private Boolean takePoint;
    private Boolean takeCoupon;
    private LocalDateTime regDate;
    private LocalDateTime resetDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @PrePersist
    private void prePersist() {
        this.takePoint = ofNullable(this.takePoint).orElse(false);
        this.takeCoupon = ofNullable(this.takeCoupon).orElse(false);
        this.regDate = ofNullable(this.regDate).orElse(LocalDateTime.now());
        this.resetDate = ofNullable(this.resetDate).orElse(LocalDateTime.now());
    }

    public void reset() {
        changeTakePoint(false);
        changeTakeCoupon(false);
        changeResetDate(LocalDateTime.now());
    }

    public void changeTakePoint(boolean takePoint) {
        this.takePoint = takePoint;
    }

    public void changeTakeCoupon(boolean takeCoupon) {
        this.takeCoupon = takeCoupon;
    }

    public void changeResetDate(LocalDateTime resetDate) {
        this.resetDate = resetDate;
    }
}
