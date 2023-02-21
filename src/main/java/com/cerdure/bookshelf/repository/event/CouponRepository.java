package com.cerdure.bookshelf.repository.event;

import com.cerdure.bookshelf.domain.event.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
}
