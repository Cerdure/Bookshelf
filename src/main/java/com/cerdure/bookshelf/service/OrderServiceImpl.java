package com.cerdure.bookshelf.service;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.order.Cart;
import com.cerdure.bookshelf.dto.order.CartDto;
import com.cerdure.bookshelf.dto.order.OrderDto;
import com.cerdure.bookshelf.repository.CartRepository;
import com.cerdure.bookshelf.service.interfaces.BookService;
import com.cerdure.bookshelf.service.interfaces.MemberService;
import com.cerdure.bookshelf.service.interfaces.OrderService;
import com.cerdure.bookshelf.web.exception.NotEnoughStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberService memberService;
    private final BookService bookService;
    private final CartRepository cartRepository;

    @Override
    public List<Cart> findAllCart(Authentication authentication) {
        return cartRepository.findByMember(memberService.findMember(authentication));
    }

    @Override
    public Cart findCart(Long bookId, Authentication authentication) {
        return cartRepository.findByBookIdAndMember(bookId, memberService.findMember(authentication));
    }

    @Override
    public Object modifyCart(Long bookId, Integer amount, Authentication authentication) {
        Cart cart = findCart(bookId, authentication);
        Book book = bookService.findById(bookId);
        if(amount > book.getStock() || book.getStock() < 1) {
            return book.getStock();
        } else if (amount < 1) {
            throw new IllegalStateException("최솟값입니다.");
        } else if (cart != null) {
            cart.changeAmount(amount);
            cartRepository.save(cart);
            return true;
        } else {
            cartRepository.save(Cart.builder()
                    .member(memberService.findMember(authentication))
                    .book(book)
                    .originPrice(book.getOriginPrice())
                    .discountPrice(book.getDiscountPrice())
                    .amount(1)
                    .build());
            return true;
        }
    }

    @Override
    public void removeCart(CartDto cartDto, Authentication authentication) {
        cartDto.getBookIds().forEach(bookId -> cartRepository.delete(findCart(bookId, authentication)));
    }

    @Override
    public List<Cart> getOrder(OrderDto orderDto, Authentication authentication) {
        List<Cart> orders = new ArrayList<>();
        Long bookId = orderDto.getBookId();
        if(bookId == null) {
            orders = findAllCart(authentication);
        } else {
            Book book = bookService.findById(bookId);
            orders.add(Cart.builder()
                    .member(memberService.findMember(authentication))
                    .book(book)
                    .originPrice(book.getOriginPrice())
                    .discountPrice(book.getDiscountPrice())
                    .amount(1)
                    .build());
        }
        return orders;
    }
}
