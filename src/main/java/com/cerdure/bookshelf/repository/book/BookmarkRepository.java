package com.cerdure.bookshelf.repository.book;

import com.cerdure.bookshelf.domain.book.Bookmark;
import com.cerdure.bookshelf.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByMemberAndBookId(Member member, Long bookId);
}
