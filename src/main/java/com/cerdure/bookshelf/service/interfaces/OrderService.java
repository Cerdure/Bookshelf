package com.cerdure.bookshelf.service.interfaces;

import com.cerdure.bookshelf.domain.order.Cart;
import com.cerdure.bookshelf.dto.order.CartDto;
import com.cerdure.bookshelf.dto.order.OrderDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OrderService {
    public List<Cart> findAllCart(Authentication authentication);
    public Cart findCart(Long bookId, Authentication authentication);
    public Object modifyCart(Long bookId, Integer amount, Authentication authentication);
    public void removeCart(CartDto cartDto, Authentication authentication);
    public List<Cart> getOrder(OrderDto orderDto, Authentication authentication);
}
