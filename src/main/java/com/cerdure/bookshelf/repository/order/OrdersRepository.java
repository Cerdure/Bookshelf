package com.cerdure.bookshelf.repository.order;

import com.cerdure.bookshelf.domain.enums.OrderState;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, String>, JpaSpecificationExecutor<Orders> {
    List<Orders> findByOrderer(Member orderer);
    Orders findByIdAndOrderer(String id, Member orderer);
    Orders findTopByOrdererOrderByRegDateDesc(Member orderer);
    List<Orders> findByOrdererAndRegDateAfter(Member orderer, LocalDateTime startDate);
    Orders findByOrdererAndOrderItems_Book_Id(Member orderer, Long bookId);
    Slice<Orders> findDistinctByOrdererAndOrderItems_Book_NameContainsIgnoreCaseAndOrderItems_OrderStateInAndRegDateBetween
            (Member orderer, String bookName, List<OrderState> orderState, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
