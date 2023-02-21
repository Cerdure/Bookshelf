package com.cerdure.bookshelf.service.classes.book.interfaces;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.book.Bookmark;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.book.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookService {
    List<Book> findAll();
    Page<Book> findAllWithPaging(Pageable pageable);
    Book findById(Long bookId);
    Book findById(String bookId);
    List<Book> findByName(String name);
    Page<Book> findByName(String name, String sortOrder, Pageable pageable);
    Page<Book> findByNamePaging(String name, String sortOrder, Integer maxNum, Pageable pageable);
    Page<Book> findByNameWithCategoryAndPublishDate(String name, List<Integer> categoryIds, LocalDate publishDate, String sortOrder, Pageable pageable);
    Page<Book> findByCategory(Integer categoryId, Pageable pageable);
    List<Book> findTop10(Integer criteria);
    List<Book> findDiscountTop16();
    List<Book> findDiscountTop18();
    List<Book> findByNameWithDiscount(String name, int discountRate);
    Map<String, Object> randomHomeBooks();
    Book[] resetTodayBook();

    Map<String, Object> bookmark(Long bookId, Authentication authentication);
    Boolean checkBookmark(Long bookId, Authentication authentication);

    void insert(BookDto bookDto);
    void validateDuplicateBook(BookDto bookDto);
    void modify(BookDto bookDto);
    void delete(BookDto bookDto);
}
