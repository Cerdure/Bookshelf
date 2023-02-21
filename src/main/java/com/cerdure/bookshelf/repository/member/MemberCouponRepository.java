package com.cerdure.bookshelf.repository.member;

import com.cerdure.bookshelf.domain.member.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
    void deleteById(Long id);
}
