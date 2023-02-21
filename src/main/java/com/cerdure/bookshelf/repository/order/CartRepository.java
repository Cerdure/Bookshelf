package com.cerdure.bookshelf.repository.order;

import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByMember(Member member);
    List<Cart> findByMemberAndSelected(Member member, boolean selected);
    Cart findByBookIdAndMember(Long bookId, Member member);
}
