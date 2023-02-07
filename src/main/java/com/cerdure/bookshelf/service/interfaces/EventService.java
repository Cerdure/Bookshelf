package com.cerdure.bookshelf.service.interfaces;

import com.cerdure.bookshelf.domain.member.Coupon;
import com.cerdure.bookshelf.domain.member.Member;
import org.springframework.security.core.Authentication;

public interface EventService {
    public void syncEventState(Member member);
    public void syncMemberGrade(Member member);
    public void giveMonthlyPoint(Authentication authentication);
    public void giveMonthlyCoupon(Authentication authentication);
    Coupon coupon(int price);
}
