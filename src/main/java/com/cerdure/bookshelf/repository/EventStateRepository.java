package com.cerdure.bookshelf.repository;

import com.cerdure.bookshelf.domain.member.EventState;
import com.cerdure.bookshelf.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStateRepository extends JpaRepository<EventState, Long> {
    public EventState findByMember(Member member);
}
