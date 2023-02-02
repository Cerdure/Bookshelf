package com.cerdure.bookshelf.repository;

import com.cerdure.bookshelf.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
