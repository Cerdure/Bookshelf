package com.cerdure.bookshelf.service.classes.event;

import com.cerdure.bookshelf.domain.event.Coupon;
import com.cerdure.bookshelf.domain.event.EventState;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.member.MemberCoupon;
import com.cerdure.bookshelf.domain.order.OrderItem;
import com.cerdure.bookshelf.domain.order.Orders;
import com.cerdure.bookshelf.dto.board.ReviewDto;
import com.cerdure.bookshelf.repository.event.CouponRepository;
import com.cerdure.bookshelf.repository.event.EventStateRepository;
import com.cerdure.bookshelf.repository.member.MemberCouponRepository;
import com.cerdure.bookshelf.service.classes.book.interfaces.BookService;
import com.cerdure.bookshelf.service.classes.event.interfaces.EventService;
import com.cerdure.bookshelf.service.classes.member.interfaces.MemberService;
import com.cerdure.bookshelf.service.classes.order.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.cerdure.bookshelf.domain.enums.MemberGrade.*;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final OrderService orderService;
    private final MemberService memberService;
    private final CouponRepository couponRepository;
    private final EventStateRepository eventStateRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Override
    public void syncEventState(Member member) {
        EventState eventState = member.getEventState();
        if (eventState == null) {
            eventState = EventState.builder().member(member).build();
            eventStateRepository.save(eventState);
            return;
        }
        if (eventState.getResetDate().getMonth() != LocalDateTime.now().getMonth()) {
            eventState.reset();
            eventStateRepository.save(eventState);
        }
    }

    @Override
    public void syncMemberGrade(Member member) {
        Integer sumOfOrderAmount = orderService.sumOfOrderAmount(member, LocalDateTime.now().minusMonths(3));
        if (sumOfOrderAmount < 100000) {
            memberService.updateGrade(member, NEW);
        } else if (sumOfOrderAmount < 200000) {
            memberService.updateGrade(member, SILVER);
        } else if (sumOfOrderAmount < 300000) {
            memberService.updateGrade(member, GOLD);
        } else {
            memberService.updateGrade(member, VIP);
        }
    }

    @Override
    public void giveMonthlyPoint(Authentication authentication) {
        Member member = memberService.findMember(authentication);
        EventState eventState = eventStateRepository.findByMember(member);
        if (eventState.getTakePoint()) {
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

        if (eventState.getTakeCoupon()) {
            throw new IllegalStateException("이번 달 쿠폰을 이미 받으셨습니다.");
        }
        switch (member.getGrade()) {
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
        memberCouponRepository.saveAll(memberCoupons);
        eventState.changeTakeCoupon(true);
        eventStateRepository.save(eventState);
    }

    @Override
    public Map<String, Object> reviewEventResolver(ReviewDto reviewDto, Authentication authentication, Map<String, Object> map) {
        Optional<OrderItem> orderItem = orderService.findOrderItem(reviewDto.getBookId(), authentication);
        if (orderItem.isPresent() && !orderItem.get().getReviewed()) {
            memberService.changePoint(authentication, 200, true);
            orderService.changeReviewed(orderItem.get(), true);
            map.put("getPoint", true);
        } else {
            map.put("getPoint", false);
        }
        return map;
    }

    @Override
    public Coupon coupon(int price) {
        return couponRepository.findById(price).get();
    }
}
