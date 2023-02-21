package com.cerdure.bookshelf.service.classes.order.interfaces;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.Cart;
import com.cerdure.bookshelf.domain.order.OrderItem;
import com.cerdure.bookshelf.domain.order.Orders;
import com.cerdure.bookshelf.dto.order.CartDto;
import com.cerdure.bookshelf.dto.order.OrderDto;
import com.cerdure.bookshelf.dto.order.OrderItemDto;
import com.cerdure.bookshelf.dto.order.OrderSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public interface OrderService {
    List<Cart> findAllCart(Authentication authentication);
    List<Cart> findAllSelectedCart(Authentication authentication);
    Cart findCart(Long bookId, Authentication authentication);
    Map<String, Object> syncItemSelect(CartDto cartDto, Authentication authentication);
    Object modifyCart(Long bookId, Integer amount, Authentication authentication);
    void removeCart(CartDto cartDto, Authentication authentication);
    void clearCart(Authentication authentication);

    Orders findOrder(String orderId, Authentication authentication);
    Orders findLastOrder(Authentication authentication);
    Slice<Orders> searchOrders(OrderSearchDto orderSearchDto, Member member);
    Map<String, Object> searchReset(Authentication authentication);
    List<Cart> newOrder(OrderDto orderDto, Authentication authentication);
    void createOrder(OrderDto orderDto, Authentication authentication) throws Exception;
    void cancelOrder(String orderId, Authentication authentication);
    Integer sumOfOrderAmount(Member member, LocalDateTime startDate);

    Optional<OrderItem> findOrderItem(Long bookId, Authentication authentication);
    List<OrderItem> findDistinctOrderItems(Authentication authentication);
    Boolean saveOrderItems(OrderItemDto orderItemDto, Authentication authentication);
    void cancelOrderItem(String orderId, Long orderItemId, Authentication authentication);
    void changeReviewed(OrderItem orderItem, boolean reviewed);

    String createCode(Member member);
    OrderDto memberAndCode(Authentication authentication);
    Integer restPoint(Integer point, Authentication authentication);
}
