package com.cerdure.bookshelf.service.interfaces;

import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.Cart;
import com.cerdure.bookshelf.domain.order.Orders;
import com.cerdure.bookshelf.dto.order.CartDto;
import com.cerdure.bookshelf.dto.order.OrderDto;
import com.cerdure.bookshelf.dto.order.OrderItemDto;
import com.cerdure.bookshelf.dto.order.OrderSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    public List<Cart> findAllCart(Authentication authentication);
    public Cart findCart(Long bookId, Authentication authentication);
    public Object modifyCart(Long bookId, Integer amount, Authentication authentication);
    public void removeCart(CartDto cartDto, Authentication authentication);
    public void clearCart(Authentication authentication);

    public Orders findLastOrder(Authentication authentication);
    public Page<Orders> searchOrders(OrderSearchDto orderSearchDto, Member member);
    public List<Cart> newOrder(OrderDto orderDto, Authentication authentication);
    public void createOrder(OrderDto orderDto, Authentication authentication) throws Exception;
    public void cancelOrder(String orderId, Authentication authentication);

    public Boolean saveOrderItems(OrderItemDto orderItemDto, Authentication authentication);
    public void cancelOrderItem(String orderId, Long orderItemId, Authentication authentication);

    public String createCode(Authentication authentication);
    public OrderDto memberAndCode(Authentication authentication);
    public Integer restPoint(Integer point, Authentication authentication);
}
