package com.cerdure.bookshelf.repository;

import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    public List<Cart> findByMember(Member member);
    public Cart findByBookIdAndMember(Long bookId, Member member);
}
