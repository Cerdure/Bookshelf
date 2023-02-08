package com.cerdure.bookshelf.repository;

import com.cerdure.bookshelf.domain.member.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileRepository extends JpaRepository<MemberProfile ,Long> {
}
