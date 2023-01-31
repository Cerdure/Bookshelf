package com.cerdure.bookshelf.service.interfaces;

import com.cerdure.bookshelf.domain.order.Cart;
import com.cerdure.bookshelf.dto.order.CartDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OrderService {
    public List<Cart> findAllCart(Authentication authentication);
    public Cart findCart(Long bookId, Authentication authentication);
    public void addCart(Long bookId, Authentication authentication) throws Exception;
    public void minusCart(Long bookId, Authentication authentication) throws Exception;
    public void removeCart(CartDto cartDto, Authentication authentication);
}
