package com.cerdure.bookshelf.repository;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.book.Bookmark;
import com.cerdure.bookshelf.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    Bookmark findByMemberAndBook(Member member, Book book);
    void deleteByMemberAndBook(Member member, Book book);
}
