package com.cerdure.bookshelf.service.classes.event.interfaces;

import com.cerdure.bookshelf.domain.event.Coupon;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.board.ReviewDto;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface EventService {
    void syncEventState(Member member);
    void syncMemberGrade(Member member);
    void giveMonthlyPoint(Authentication authentication);
    void giveMonthlyCoupon(Authentication authentication);
    Map<String, Object> reviewEventResolver(ReviewDto reviewDto, Authentication authentication, Map<String, Object> map);
    Coupon coupon(int price);
}
