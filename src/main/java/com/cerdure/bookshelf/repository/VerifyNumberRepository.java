package com.cerdure.bookshelf.repository;

import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.member.VerifyNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifyNumberRepository extends JpaRepository<VerifyNumber,Long> {
    public VerifyNumber findByMember(Member member);
}
