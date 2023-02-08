package com.cerdure.bookshelf.repository;

import com.cerdure.bookshelf.domain.member.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
}
