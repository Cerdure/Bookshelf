package com.cerdure.bookshelf.service;

import com.cerdure.bookshelf.domain.enums.MemberGrade;
import com.cerdure.bookshelf.domain.member.Coupon;
import com.cerdure.bookshelf.domain.member.EventState;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.member.MemberCoupon;
import com.cerdure.bookshelf.repository.CouponRepository;
import com.cerdure.bookshelf.repository.EventStateRepository;
import com.cerdure.bookshelf.repository.MemberCouponRepository;
import com.cerdure.bookshelf.service.interfaces.EventService;
import com.cerdure.bookshelf.service.interfaces.MemberService;
import com.cerdure.bookshelf.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.cerdure.bookshelf.domain.enums.MemberGrade.*;
import static com.cerdure.bookshelf.domain.enums.MemberGrade.VIP;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final MemberService memberService;
    private final OrderService orderService;
    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;
    private final EventStateRepository eventStateRepository;

    @Override
    public void syncEventState(Member member) {
        EventState eventState = member.getEventState();
        if(eventState == null){
            eventState = EventState.builder().member(member).build();
            eventStateRepository.save(eventState);
            return;
        }
        if(eventState.getResetDate().getMonth() != LocalDateTime.now().getMonth()){
            eventState.reset();
            eventStateRepository.save(eventState);
        }
    }

    @Override
    public void syncMemberGrade(Member member) {
        Integer sumOfOrderAmount = orderService.sumOfOrderAmount(member, LocalDateTime.now().minusMonths(3));
        EventState eventState = eventStateRepository.findByMember(member);
        MemberGrade prevGrade = member.getGrade();
        if (sumOfOrderAmount < 100000) {
            member.changeGrade(NEW);
        } else if (sumOfOrderAmount < 200000) {
            member.changeGrade(SILVER);
            eventState.changeTakeCoupon(prevGrade.equals(NEW) ? false : true);
        } else if (sumOfOrderAmount < 300000) {
            member.changeGrade(GOLD);
            eventState.changeTakeCoupon(prevGrade.equals(NEW) || prevGrade.equals(SILVER) ? false : true);
        } else {
            member.changeGrade(VIP);
            eventState.changeTakeCoupon(!prevGrade.equals(VIP) ? false : true);
        }
        memberService.save(member);
    }

    @Override
    public void giveMonthlyPoint(Authentication authentication) {
        Member member = memberService.findMember(authentication);
        EventState eventState = eventStateRepository.findByMember(member);
        if(eventState.getTakePoint()){
            throw new IllegalStateException("이번 달 포인트를 이미 받으셨습니다.");
        }
        member.changePoint(member.getPoint() + 1000);
        eventState.changeTakePoint(true);
        eventStateRepository.save(eventState);
    }

    @Override
    public void giveMonthlyCoupon(Authentication authentication) {
        Member member = memberService.findMember(authentication);
        EventState eventState = eventStateRepository.findByMember(member);
        List<MemberCoupon> memberCoupons = new ArrayList<>();

        if(eventState.getTakeCoupon()){
            throw new IllegalStateException("이번 달 쿠폰을 이미 받으셨습니다.");
        }
        switch (member.getGrade()){
            case NEW:
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(2000)).build());
                break;
            case SILVER:
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(2000)).build());
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(3000)).build());
                break;
            case GOLD:
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(2000)).build());
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(3000)).build());
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(5000)).build());
                break;
            case VIP:
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(2000)).build());
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(2000)).build());
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(3000)).build());
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(5000)).build());
                break;
        }
        memberCoupons.forEach(memberCoupon -> memberCouponRepository.save(memberCoupon));
        eventState.changeTakeCoupon(true);
        eventStateRepository.save(eventState);
    }

    @Override
    public Coupon coupon(int price) {
        return couponRepository.findById(price).get();
    }
}
