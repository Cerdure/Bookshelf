package com.cerdure.bookshelf.repository;

import com.cerdure.bookshelf.domain.enums.OrderState;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, String>, JpaSpecificationExecutor<Orders> {
    public Orders findTopByOrdererOrderByRegDateDesc(Member orderer);
    public Orders findByIdAndOrderer(String id, Member orderer);
    public Slice<Orders> findDistinctByOrderItems_Book_NameContainsIgnoreCaseAndOrderItems_OrderStateInAndRegDateBetween
            (String bookName, List<OrderState> orderState, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
