package com.cerdure.bookshelf.service;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.Cart;
import com.cerdure.bookshelf.domain.order.OrderItem;
import com.cerdure.bookshelf.domain.order.Orders;
import com.cerdure.bookshelf.dto.order.CartDto;
import com.cerdure.bookshelf.dto.order.OrderDto;
import com.cerdure.bookshelf.dto.order.OrderItemDto;
import com.cerdure.bookshelf.repository.CartRepository;
import com.cerdure.bookshelf.repository.OrderItemRepository;
import com.cerdure.bookshelf.repository.OrdersRepository;
import com.cerdure.bookshelf.service.interfaces.BookService;
import com.cerdure.bookshelf.service.interfaces.MemberService;
import com.cerdure.bookshelf.service.interfaces.OrderService;
import com.cerdure.bookshelf.web.exception.NotEnoughStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberService memberService;
    private final BookService bookService;
    private final CartRepository cartRepository;
    private final OrdersRepository ordersRepository;
    private final OrderItemRepository orderItemRepository;

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
    public void clearCart(Authentication authentication) {
        List<Cart> carts = cartRepository.findByMember(memberService.findMember(authentication));
        carts.forEach(cart -> cartRepository.delete(cart));
    }

    @Override
    public Orders findLastOrder(Authentication authentication) {
        return ordersRepository.findTopByOrdererOrderByRegDateDesc(memberService.findMember(authentication));
    }

    @Override
    public List<Cart> newOrder(OrderDto orderDto, Authentication authentication) {
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

    @Override
    public String createCode(Authentication authentication) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
                        + memberService.findMember(authentication).getId();
    }

    @Override
    public OrderDto memberAndCode(Authentication authentication) {
        return OrderDto.builder()
                .member(memberService.findMember(authentication))
                .id(createCode(authentication))
                .build();
    }

    @Override
    public void createOrder(OrderDto orderDto, Authentication authentication) throws Exception{
        try {
            Orders orders = orderDto.toEntity();
            orders.changeOrderer(memberService.findMember(authentication));
            ordersRepository.save(orders);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Override
    public Boolean saveOrderItems(OrderItemDto orderItemDto, Authentication authentication){
        try {
            Orders orders = ordersRepository.findById(orderItemDto.getOrderId()).get();
            Book book = bookService.findById(orderItemDto.getBookId());
            int amount = orderItemDto.getAmount();

            book.changeStock(book.getStock() - amount);
            book.changeSales(book.getSales() + amount);
            memberService.changePoint(authentication, orders.getPoint());
            orderItemRepository.save(OrderItem.builder()
                    .orders(orders)
                    .book(book)
                    .amount(amount)
                    .build());

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Integer restPoint(Integer point, Authentication authentication) {
        return memberService.findMember(authentication).getPoint() - point;
    }
}
