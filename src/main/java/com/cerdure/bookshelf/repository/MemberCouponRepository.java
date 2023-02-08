package com.cerdure.bookshelf.repository;

import com.cerdure.bookshelf.domain.member.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
}
