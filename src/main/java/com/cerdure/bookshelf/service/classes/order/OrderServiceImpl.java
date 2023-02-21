package com.cerdure.bookshelf.service.classes.order;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.enums.OrderState;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.Cart;
import com.cerdure.bookshelf.domain.order.OrderItem;
import com.cerdure.bookshelf.domain.order.Orders;
import com.cerdure.bookshelf.dto.order.CartDto;
import com.cerdure.bookshelf.dto.order.OrderDto;
import com.cerdure.bookshelf.dto.order.OrderItemDto;
import com.cerdure.bookshelf.dto.order.OrderSearchDto;
import com.cerdure.bookshelf.repository.member.MemberCouponRepository;
import com.cerdure.bookshelf.repository.order.CartRepository;
import com.cerdure.bookshelf.repository.order.OrderItemRepository;
import com.cerdure.bookshelf.repository.order.OrdersRepository;
import com.cerdure.bookshelf.service.classes.book.interfaces.BookService;
import com.cerdure.bookshelf.service.classes.member.interfaces.MemberService;
import com.cerdure.bookshelf.service.classes.order.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberService memberService;
    private final BookService bookService;
    private final CartRepository cartRepository;
    private final OrdersRepository ordersRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Override
    public List<Cart> findAllCart(Authentication authentication) {
        return cartRepository.findByMember(memberService.findMember(authentication));
    }

    @Override
    public List<Cart> findAllSelectedCart(Authentication authentication) {
        return cartRepository.findByMemberAndSelected(memberService.findMember(authentication), true);
    }

    @Override
    public Cart findCart(Long bookId, Authentication authentication) {
        return cartRepository.findByBookIdAndMember(bookId, memberService.findMember(authentication));
    }

    @Override
    public Map<String, Object> syncItemSelect(CartDto cartDto, Authentication authentication) {
        Map<String, Object> map = new HashMap<>();
        try {
            findAllCart(authentication)
                    .forEach(cart -> {
                        cart.changeSelected(cartDto.getBookIds().contains(cart.getBook().getId()));
                        cartRepository.save(cart);
                    });
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("error", e.getMessage());
            return map;
        }
        map.put("success", true);
        return map;
    }

    @Override
    public Object modifyCart(Long bookId, Integer amount, Authentication authentication) {
        Cart cart = findCart(bookId, authentication);
        Book book = bookService.findById(bookId);
        if (amount > book.getStock() || book.getStock() < 1) {
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
        carts.forEach(cart -> {
            if (cart.getSelected()) cartRepository.delete(cart);
        });
    }

    @Override
    public Orders findOrder(String orderId, Authentication authentication) {
        return ordersRepository.findByIdAndOrderer(orderId, memberService.findMember(authentication));
    }

    @Override
    public Orders findLastOrder(Authentication authentication) {
        return ordersRepository.findTopByOrdererOrderByRegDateDesc(memberService.findMember(authentication));
    }

    @Override
    public Slice<Orders> searchOrders(OrderSearchDto orderSearchDto, Member member) {
        int page = orderSearchDto.getPage() == null ? 0 : orderSearchDto.getPage() - 1;
        Pageable pageable = PageRequest.of(page, 4, Sort.by("id").descending());
        LocalDateTime startDate = LocalDateTime.of(orderSearchDto.getStartDate(), LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(orderSearchDto.getEndDate(), LocalTime.MAX);
        String keyword = orderSearchDto.getName() == null ? "" : orderSearchDto.getName();
        List<OrderState> orderStates = new ArrayList<>();
        if (orderSearchDto.getOrderState() == OrderState.ALL) {
            orderStates.add(OrderState.ORDER);
            orderStates.add(OrderState.CANCEL);
            orderStates.add(OrderState.SOLD);
        } else {
            orderStates.add(orderSearchDto.getOrderState());
        }
        Slice<Orders> orders = ordersRepository
                .findDistinctByOrdererAndOrderItems_Book_NameContainsIgnoreCaseAndOrderItems_OrderStateInAndRegDateBetween
                        (member, keyword, orderStates, startDate, endDate, pageable);
        orders.forEach(order -> {
            List<OrderItem> orderItems = order.getOrderItems().stream().filter(orderItem -> {
                Book book = orderItem.getBook();

                if (!keyword.equals("")) {
                    book.coincidenceHighlight(keyword);
                    orderItem.changeBook(book);
                }
                return book.getName().contains(keyword) && orderStates.contains(orderItem.getOrderState());
            }).collect(Collectors.toList());
            order.changeOrderItems(orderItems);
        });
        return orders;
    }

    @Override
    public Map<String, Object> searchReset(Authentication authentication) {
        Member member = memberService.findMember(authentication);
        OrderSearchDto orderSearchDto = OrderSearchDto.builder()
                .startDate(LocalDate.now().minusMonths(1))
                .endDate(LocalDate.now())
                .orderState(OrderState.ALL)
                .name("")
                .page(1)
                .build();
        Slice<Orders> orders = searchOrders(orderSearchDto, member);
        Map<String, Object> map = new HashMap<>();
        map.put("member", member);
        map.put("orders", orders);
        return map;
    }

    @Override
    public List<Cart> newOrder(OrderDto orderDto, Authentication authentication) {
        List<Cart> orders = new ArrayList<>();
        Long bookId = orderDto.getBookId();
        if (bookId == null) {
            orders = findAllSelectedCart(authentication);
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
    public void createOrder(OrderDto orderDto, Authentication authentication) throws Exception {
        try {
            Orders orders = orderDto.toEntity();
            orders.changeOrderer(memberService.findMember(authentication));
            memberCouponRepository.findById(orderDto.getCouponId())
                    .ifPresent(coupon -> orders.changeCoupon(coupon.getCoupon()));
            ordersRepository.save(orders);
            memberService.changePoint(authentication, orders.getPoint(), false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Override
    public void cancelOrder(String orderId, Authentication authentication) {
        Orders orders = ordersRepository.findByIdAndOrderer(orderId, memberService.findMember(authentication));
        orders.getOrderItems().forEach(orderItem -> {
            orderItem.changeState(OrderState.CANCEL);
            orderItemRepository.save(orderItem);
        });
        orders.changeState(OrderState.CANCEL);
        ordersRepository.save(orders);
    }

    @Override
    public Integer sumOfOrderAmount(Member member, LocalDateTime startDate) {
        return ordersRepository.findByOrdererAndRegDateAfter(member, startDate)
                .stream().mapToInt(Orders::getOrderPrice).sum();
    }

    @Override
    public Optional<OrderItem> findOrderItem(Long bookId, Authentication authentication) {
        return orderItemRepository.findByOrders_OrdererAndBook_Id(memberService.findMember(authentication), bookId);
    }

    @Override
    public List<OrderItem> findDistinctOrderItems(Authentication authentication) {
        List<Orders> orders = ordersRepository.findByOrderer(memberService.findMember(authentication));
        List<Book> books = new ArrayList<>();
        return orders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .filter(orderItem -> {
                    Book book = orderItem.getBook();
                    if (books.contains(book)) return false;
                    books.add(book);
                    return true;
                }).collect(Collectors.toList());
    }

    @Override
    public Boolean saveOrderItems(OrderItemDto orderItemDto, Authentication authentication) {
        try {
            Orders orders = ordersRepository.findById(orderItemDto.getOrderId()).get();
            Book book = bookService.findById(orderItemDto.getBookId());
            int amount = orderItemDto.getAmount();
            book.changeStock(book.getStock() - amount);
            book.changeSales(book.getSales() + amount);
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
    public void cancelOrderItem(String orderId, Long orderItemId, Authentication authentication) {
        Orders orders = ordersRepository.findByIdAndOrderer(orderId, memberService.findMember(authentication));
        OrderItem orderItem = orderItemRepository.findByIdAndOrders(orderItemId, orders);
        orderItem.changeState(OrderState.CANCEL);
        orderItemRepository.save(orderItem);
        List<OrderItem> orderItems = orderItemRepository.findByOrdersAndOrderState(orders, OrderState.ORDER);
        if (orderItems.size() == 0) {
            orders.changeState(OrderState.CANCEL);
            ordersRepository.save(orders);
        }
    }

    @Override
    public void changeReviewed(OrderItem orderItem, boolean reviewed) {
        orderItem.changeReviewed(reviewed);
        orderItemRepository.save(orderItem);
    }

    @Override
    public String createCode(Member member) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + member.getId();
    }

    @Override
    public OrderDto memberAndCode(Authentication authentication) {
        Member member = memberService.findMember(authentication);
        return OrderDto.builder()
                .member(member)
                .id(createCode(member))
                .build();
    }

    @Override
    public Integer restPoint(Integer point, Authentication authentication) {
        return memberService.findMember(authentication).getPoint() - point;
    }

}
