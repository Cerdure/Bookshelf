package com.cerdure.bookshelf.repository;

import com.cerdure.bookshelf.domain.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
