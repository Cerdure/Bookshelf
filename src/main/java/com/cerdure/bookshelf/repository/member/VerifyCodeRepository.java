package com.cerdure.bookshelf.repository.member;

import com.cerdure.bookshelf.domain.member.VerifyCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerifyCodeRepository extends JpaRepository<VerifyCode,Long> {
    Optional<VerifyCode> findByPhone(String phone);
    void deleteByPhone(String phone);
}
