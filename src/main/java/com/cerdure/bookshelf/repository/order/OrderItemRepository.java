package com.cerdure.bookshelf.repository.order;

import com.cerdure.bookshelf.domain.enums.OrderState;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.OrderItem;
import com.cerdure.bookshelf.domain.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    OrderItem findByIdAndOrders(Long id, Orders orders);
    Optional<OrderItem> findByOrders_OrdererAndBook_Id(Member orderer, Long bookId);
    List<OrderItem> findByOrdersAndOrderState(Orders orders, OrderState orderState);
}
