package com.cerdure.bookshelf.repository;

import com.cerdure.bookshelf.domain.enums.OrderState;
import com.cerdure.bookshelf.domain.order.OrderItem;
import com.cerdure.bookshelf.domain.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    public OrderItem findByIdAndOrders(Long id, Orders orders);
    public List<OrderItem> findByOrdersAndOrderState(Orders orders, OrderState orderState);
}
