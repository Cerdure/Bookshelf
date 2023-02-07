package com.cerdure.bookshelf.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventState {

    @Id @GeneratedValue
    @Column(name = "event_state_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean takePoint;
    private Boolean takeCoupon;
    private LocalDateTime regDate;
    private LocalDateTime resetDate;

    @PrePersist
    public void prePersist(){
        this.takePoint = this.takePoint == null ? false : this.takePoint;
        this.takeCoupon = this.takeCoupon == null ? false : this.takeCoupon;
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
        this.resetDate = this.resetDate == null ? LocalDateTime.now() : this.resetDate;
    }

    @Builder
    public EventState(Long id, Member member, Boolean takePoint, Boolean takeCoupon, LocalDateTime regDate, LocalDateTime resetDate) {
        this.id = id;
        this.member = member;
        this.takePoint = takePoint;
        this.takeCoupon = takeCoupon;
        this.regDate = regDate;
        this.resetDate = resetDate;
    }

    public void reset(){
        changeTakePoint(false);
        changeTakeCoupon(false);
        changeResetDate(LocalDateTime.now());
    }

    public void changeTakePoint(boolean takePoint){
        this.takePoint = takePoint;
    }

    public void changeTakeCoupon(boolean takeCoupon){
        this.takeCoupon = takeCoupon;
    }

    public void changeResetDate(LocalDateTime resetDate){
        this.resetDate = resetDate;
    }
}
