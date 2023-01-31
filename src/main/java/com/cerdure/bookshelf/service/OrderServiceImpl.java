package com.cerdure.bookshelf.service;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.order.Cart;
import com.cerdure.bookshelf.dto.order.CartDto;
import com.cerdure.bookshelf.repository.CartRepository;
import com.cerdure.bookshelf.service.interfaces.BookService;
import com.cerdure.bookshelf.service.interfaces.MemberService;
import com.cerdure.bookshelf.service.interfaces.OrderService;
import com.cerdure.bookshelf.web.exception.NotEnoughStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
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
    public void addCart(Long bookId, Authentication authentication) throws Exception{
        Cart cart = findCart(bookId, authentication);
        Book book = bookService.findById(bookId);
        if (cart != null) {
            if(cart.getAmount() >= book.getStock()){
                throw new NotEnoughStockException("재고가 부족합니다");
            }
            cart.amountPlus();
            cartRepository.save(cart);
        } else if (book.getStock() < 1) {
            throw new NotEnoughStockException("재고가 부족합니다");
        } else {
            cartRepository.save(Cart.builder()
                    .member(memberService.findMember(authentication))
                    .book(book)
                    .originPrice(book.getOriginPrice())
                    .discountPrice(book.getDiscountPrice())
                    .amount(1)
                    .build());
        }
    }

    @Override
    public void minusCart(Long bookId, Authentication authentication) throws Exception {
        
    }

    @Override
    public void removeCart(CartDto cartDto, Authentication authentication) {
        cartDto.getBookIds().forEach(bookId -> cartRepository.delete(findCart(bookId, authentication)));
    }
}
